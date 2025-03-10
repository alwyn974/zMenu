package re.alwyn974.schema.generator;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.victools.jsonschema.generator.*;
import com.github.victools.jsonschema.generator.Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import re.alwyn974.schema.annotations.ArraySchema;
import re.alwyn974.schema.annotations.Schema;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SchemaModule implements Module {
    private static final Logger logger = LoggerFactory.getLogger(SchemaModule.class);

    @Override
    public void applyToConfigBuilder(SchemaGeneratorConfigBuilder builder) {
        this.applyToConfigBuilder(builder.forTypesInGeneral());
        this.applyToConfigBuilder(builder.forFields());
        this.applyToConfigBuilder(builder.forMethods());

        builder.forFields().withInstanceAttributeOverride((node, scope, context) -> {
            Schema schema = scope.getAnnotation(Schema.class);
            if (schema == null) return;

            if (!node.has("default")) {
                try {
                    Field field = scope.getRawMember();
                    Object fieldTypeInstance = field.getDeclaringClass().getDeclaredConstructor().newInstance();
                    field.setAccessible(true);
                    Object value = field.get(fieldTypeInstance);
                    if (value == null) return;
                    if (value.getClass().isPrimitive() || value.getClass() == String.class || value.getClass().isEnum())
                        node.putPOJO("default", value);
                } catch (Exception e) {
                    logger.error("Error while setting default value for {}#{}", scope.getRawMember().getDeclaringClass(), scope.getName());
                }
            }
        });

        builder.forTypesInGeneral().withTypeAttributeOverride((node, scope, context) -> {
            if (!node.has("type") || !node.get("type").asText().equals("object"))
                return;
            if (scope.getType().isArray() || scope.getType().isInstanceOf(Map.class))
                return;
            ResolvedType resolvedType = scope.getType();
            resolvedType.getMemberFields().stream().filter(field -> field.getRawMember().isAnnotationPresent(Schema.class)).forEach(field -> {
                Schema schema = field.getRawMember().getAnnotation(Schema.class);
                String fieldName = schema.name().isEmpty() ? field.getRawMember().getName() : schema.name();
                JsonNode properties = node.get("properties");
                JsonNode propertyValue = properties.findValue(fieldName);

                if (!schema.type().isEmpty() || schema.types().length > 0) {
                    ObjectNode parent = (ObjectNode) propertyValue.findParent("type");
                    if (schema.types().length > 0) {
                        parent.remove("type");
                        ArrayNode types = parent.putArray("type");
                        for (String type : schema.types())
                            types.add(type);
                    } else if (!schema.type().isEmpty()) {
                        node.put("type", schema.type());
                    }
                }

                if (schema.names().length == 0) return;
                for (String name : schema.names()) {
                    ObjectNode newObjectNode = properties.withObject(name);
                    newObjectNode.setAll((ObjectNode) propertyValue);
                }
                if (!schema.required()) return;

                ObjectNode anyOf = node.withObject("not").withArray("anyOf").addObject();
                ArrayNode anyOfRequiredArray = anyOf.putArray("required");
                for (String name : schema.names())
                    anyOfRequiredArray.add(name);
                anyOfRequiredArray.add(fieldName);

                ObjectNode allOfNode = node.withArray("allOf").addObject();
                ArrayNode anyOfArray = allOfNode.withArray("anyOf");
                ObjectNode anyOfNode1 = anyOfArray.addObject();
                anyOfNode1.putArray("required").add(fieldName);
                for (String name : schema.names()) {
                    ObjectNode anyOfNode2 = anyOfArray.addObject();
                    anyOfNode2.putArray("required").add(name);
                }

                ArrayNode requiredArray = (ArrayNode) node.get("required");
                if (requiredArray != null) {
                    for (int i = 0; i < requiredArray.size(); i++) {
                        if (requiredArray.get(i).asText().equals(fieldName)) {
                            requiredArray.remove(i);
                            break;
                        }
                    }
                }
            });
        });
    }

    private void applyToConfigBuilder(SchemaGeneratorGeneralConfigPart configPart) {
        configPart.withDescriptionResolver(this.createTypePropertyResolver(Schema::description, description -> !description.isEmpty()));
        configPart.withTitleResolver(this.createTypePropertyResolver(Schema::title, title -> !title.isEmpty()));
        configPart.withAdditionalPropertiesResolver(this.createTypePropertyResolver(this::mapAdditionalPropertiesEnumValue, Objects::nonNull));

        configPart.withCustomDefinitionProvider(new ExternalRefCustomDefinitionProvider());
        configPart.withSubtypeResolver(new SubtypeResolver());
        configPart.withDefinitionNamingStrategy(new SchemaDefinitionNamingStrategy(configPart.getDefinitionNamingStrategy()));
    }

    private void applyToConfigBuilder(SchemaGeneratorConfigPart<?> configPart) {
        configPart.withTargetTypeOverridesResolver(this::resolveTargetTypeOverrides);
        configPart.withIgnoreCheck(this::shouldBeIgnored);
        configPart.withPropertyNameOverrideResolver(this::resolvePropertyNameOverride);
        configPart.withCustomDefinitionProvider(this::provideCustomSchemaDefinition);

        configPart.withDescriptionResolver(this::resolveDescription);
        configPart.withTitleResolver(this::resolveTitle);
        configPart.withRequiredCheck(this::checkRequired);
        configPart.withNullableCheck(this::checkNullable);
        configPart.withEnumResolver(this::resolveEnum);
        configPart.withDefaultResolver(this::resolveDefault);

        configPart.withStringMinLengthResolver(this::resolveMinLength);
        configPart.withStringMaxLengthResolver(this::resolveMaxLength);
        configPart.withStringFormatResolver(this::resolveFormat);
        configPart.withStringPatternResolver(this::resolvePattern);

        configPart.withNumberMultipleOfResolver(this::resolveMultipleOf);
        configPart.withNumberExclusiveMaximumResolver(this::resolveExclusiveMaximum);
        configPart.withNumberInclusiveMaximumResolver(this::resolveInclusiveMaximum);
        configPart.withNumberExclusiveMinimumResolver(this::resolveExclusiveMinimum);
        configPart.withNumberInclusiveMinimumResolver(this::resolveInclusiveMinimum);

        configPart.withArrayMinItemsResolver(this::resolveArrayMinItems);
        configPart.withArrayMaxItemsResolver(this::resolveArrayMaxItems);
        configPart.withArrayUniqueItemsResolver(this::resolveArrayUniqueItems);

        // take care of various keywords that are not so straightforward to apply
        configPart.withInstanceAttributeOverride(this::overrideInstanceAttributes);
        configPart.withDependentRequiresResolver(this::resolveDependentRequires);
        configPart.withAdditionalPropertiesResolver(this::resolveAdditionalProperties);
    }

    protected ResolvedType resolveAdditionalProperties(MemberScope<?, ?> member) {
        return this.getSchemaAnnotationValue(member, Schema::additionalProperties, annotatedImplementation -> annotatedImplementation != Void.class)
                .map(annotatedType -> member.getContext().resolve(annotatedType))
                .orElse(null);
    }

    protected List<String> resolveDependentRequires(MemberScope<?, ?> member) {
        return this.getSchemaAnnotationValue(member, Schema::dependentRequired, requires -> requires.length > 0)
                .map(Arrays::asList)
                .orElse(null);
    }

    /**
     * Derive target type override from {@code @Schema(implementation = ...)}.
     *
     * @param member field/method to determine target type override for
     * @return single target type override or null
     */
    protected List<ResolvedType> resolveTargetTypeOverrides(MemberScope<?, ?> member) {
        return this.getSchemaAnnotationValue(member, Schema::implementation, annotatedImplementation -> annotatedImplementation != Void.class)
                .map(annotatedType -> member.getContext().resolve(annotatedType))
                .map(Collections::singletonList)
                .orElse(null);
    }

    /**
     * Determine whether the given field/method should be skipped, based on {@code @Schema(hidden = true)}.
     *
     * @param member field/method to check
     * @return whether to skip the field/method
     */
    protected boolean shouldBeIgnored(MemberScope<?, ?> member) {
        return this.getSchemaAnnotationValue(member, Schema::hidden, Boolean.TRUE::equals).isPresent();
    }

    /**
     * Determine an alternative name for the given field/method, based on {@code @Schema(name = ...)}.
     *
     * @param member field/method to look-up alternative property name for
     * @return alternative property name
     */
    protected String resolvePropertyNameOverride(MemberScope<?, ?> member) {
        return this.getSchemaAnnotationValue(member, Schema::name, name -> !name.isEmpty()).orElse(null);
    }

    /**
     * Look-up description from {@code @Schema(description = ...)} for given field/method.
     *
     * @param member field/method to look-up description for
     * @return schema description
     */
    protected String resolveDescription(MemberScope<?, ?> member) {
        return this.getSchemaAnnotationValue(member, Schema::description, description -> !description.isEmpty()).orElse(null);
    }

    /**
     * Look-up title from {@code @Schema(title = ...)} for given field/method.
     *
     * @param member field/method to look-up title for
     * @return schema title
     */
    protected String resolveTitle(MemberScope<?, ?> member) {
        return this.getSchemaAnnotationValue(member, Schema::title, title -> !title.isEmpty()).orElse(null);
    }

    /**
     * Determine whether the given field/method is deemed required in its containing type based on {@code @Schema(required = true)}.
     *
     * @param member field/method to check
     * @return whether the field/method is required
     */
    protected boolean checkRequired(MemberScope<?, ?> member) {
        return this.getSchemaAnnotationValue(member, Schema::required, Boolean.TRUE::equals).isPresent();
    }

    /**
     * Determine whether the given field/method may be null based on {@code @Schema(nullable = true)}.
     *
     * @param member field/method to check
     * @return whether the field/method is nullable
     */
    protected boolean checkNullable(MemberScope<?, ?> member) {
        return this.getSchemaAnnotationValue(member, Schema::nullable, Boolean.TRUE::equals).isPresent();
    }

    /**
     * Derive the allowed type of a schema's additional properties from the given annotation.
     *
     * @param annotation annotation to check
     * @return {@code Object.class} (if true or an external "$ref" is specified), {@code Void.class} (if forbidden) or {@code null} (if undefined)
     */
    protected Type mapAdditionalPropertiesEnumValue(Schema annotation) {
        if (!annotation.$ref().isEmpty()) {
            // prevent an invalid combination of "$ref" with "additionalProperties": false
            return Object.class;
        }
        return annotation.additionalProperties();
    }

    /**
     * Look-up the finite list of possible values from {@code @Schema(allowedValues = ...)}.
     *
     * @param member field/method to determine allowed values for
     * @return applicable "const"/"enum" values or null
     */
    protected List<Object> resolveEnum(MemberScope<?, ?> member) {
        Optional<List<String>> allowedValues = this.getSchemaAnnotationValue(member, Schema::allowedValues, values -> values.length > 0)
                .map(Arrays::asList);

        Optional<List<String>> excludeValues = this.getSchemaAnnotationValue(member, Schema::excludeValues, values -> values.length > 0)
                .map(Arrays::asList);

        if (allowedValues.isPresent() && excludeValues.isPresent()) {
            List<String> filteredValues = new ArrayList<>(allowedValues.get());
            filteredValues.removeAll(excludeValues.get());
            return convertValues(filteredValues);
        }

        return allowedValues.map(this::convertValues).orElse(null);
    }

    private List<Object> convertValues(List<String> values) {
        if (values.stream().allMatch(this::isInteger)) {
            return values.stream().map(Integer::valueOf).collect(Collectors.toList());
        } else if (values.stream().allMatch(this::isDouble)) {
            return values.stream().map(Double::valueOf).collect(Collectors.toList());
        } else if (values.stream().allMatch(this::isFloat)) {
            return values.stream().map(Float::valueOf).collect(Collectors.toList());
        } else {
            return new ArrayList<>(values);
        }
    }

    private boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isFloat(String value) {
        try {
            Float.parseFloat(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Look-up the default value for the given field/method from {@code @Schema(defaultValue = ...)}.
     *
     * @param member field/method to determine default value for
     * @return default property value or null
     */
    protected String resolveDefault(MemberScope<?, ?> member) {
        return this.getSchemaAnnotationValue(member, Schema::defaultValue, defaultValue -> !defaultValue.isEmpty()).orElse(null);
    }

    /**
     * Look-up the value from {@code @Schema(minLength = ...)} for the given field/method.
     *
     * @param member field/method to look-up minimum string length for
     * @return minimum string length or null
     */
    protected Integer resolveMinLength(MemberScope<?, ?> member) {
        return this.getSchemaAnnotationValue(member, Schema::minLength, minLength -> minLength > 0).orElse(null);
    }

    /**
     * Look-up the value from {@code @Schema(maxLength = ...)} for the given field/method.
     *
     * @param member field/method to look-up maximum string length for
     * @return maximum string length or null
     */
    protected Integer resolveMaxLength(MemberScope<?, ?> member) {
        return this.getSchemaAnnotationValue(member, Schema::maxLength, maxLength -> maxLength < Integer.MAX_VALUE && maxLength > -1).orElse(null);
    }

    /**
     * Look-up the value from {@code @Schema(format = ...)} for the given field/method.
     *
     * @param member field/method to look-up format for
     * @return format value or null
     */
    protected String resolveFormat(MemberScope<?, ?> member) {
        return this.getSchemaAnnotationValue(member, Schema::format, format -> !format.isEmpty()).orElse(null);
    }

    /**
     * Look-up the value from {@code @Schema(pattern = ...)} for the given field/method.
     *
     * @param member field/method to look-up pattern for
     * @return pattern value or null
     */
    protected String resolvePattern(MemberScope<?, ?> member) {
        return this.getSchemaAnnotationValue(member, Schema::pattern, pattern -> !pattern.isEmpty()).orElse(null);
    }

    /**
     * Look-up the value from {@code @Schema(multipleOf = ...)} for the given field/method.
     *
     * @param member field/method to look-up multipleOf for
     * @return multipleOf value or null
     */
    protected BigDecimal resolveMultipleOf(MemberScope<?, ?> member) {
        return this.getSchemaAnnotationValue(member, Schema::multipleOf, multipleOf -> multipleOf != 0)
                .map(BigDecimal::valueOf)
                .orElse(null);
    }

    /**
     * Look-up the exclusive maximum value from {@code @Schema(maximum = ..., exclusiveMaxium = true)} for the given field/method.
     *
     * @param member field/method to look-up exclusiveMaximum for
     * @return exclusiveMaximum value or null
     */
    protected BigDecimal resolveExclusiveMaximum(MemberScope<?, ?> member) {
        return this.getSchemaAnnotationValue(member, Schema::exclusiveMaximum, maximum -> maximum != Long.MIN_VALUE)
                .map(BigDecimal::new)
                .orElse(null);
    }

    /**
     * Look-up the inclusive maximum value from {@code @Schema(maximum = ..., exclusiveMaxium = false)} for the given field/method.
     *
     * @param member field/method to look-up maximum for
     * @return maximum value or null
     */
    protected BigDecimal resolveInclusiveMaximum(MemberScope<?, ?> member) {
        return this.getSchemaAnnotationValue(member, Schema::maximum, maximum -> maximum != Long.MIN_VALUE)
                .map(BigDecimal::new)
                .orElse(null);
    }

    /**
     * Look-up the exclusive minimum value from {@code @Schema(minimum = ..., exclusiveMinium = true)} for the given field/method.
     *
     * @param member field/method to look-up exclusiveMinimum for
     * @return exclusiveMinimum value or null
     */
    protected BigDecimal resolveExclusiveMinimum(MemberScope<?, ?> member) {
        return this.getSchemaAnnotationValue(member, Schema::exclusiveMinimum, minimum -> minimum != Long.MAX_VALUE)
                .map(BigDecimal::new)
                .orElse(null);
    }

    /**
     * Look-up the inclusive minimum value from {@code @Schema(minimum = ..., exclusiveMinium = false)} for the given field/method.
     *
     * @param member field/method to look-up minimum for
     * @return minimum value or null
     */
    protected BigDecimal resolveInclusiveMinimum(MemberScope<?, ?> member) {
        return this.getSchemaAnnotationValue(member, Schema::minimum, minimum -> minimum != Long.MAX_VALUE)
                .map(BigDecimal::new)
                .orElse(null);
    }

    /**
     * Determine the given field/method's {@link ArraySchema} annotation is present and contains a specific {@code minItems}.
     *
     * @param member potentially annotated field/method
     * @return the {@code @ArraySchema(minItems)} value, otherwise {@code null}
     */
    protected Integer resolveArrayMinItems(MemberScope<?, ?> member) {
        if (member.isFakeContainerItemScope()) {
            return null;
        }
        return this.getArraySchemaAnnotation(member)
                .map(ArraySchema::minItems)
                .filter(minItems -> minItems != Integer.MAX_VALUE)
                .orElse(null);
    }

    /**
     * Determine the given field/method's {@link ArraySchema} annotation is present and contains a specific {@code maxItems}.
     *
     * @param member potentially annotated field/method
     * @return the {@code @ArraySchema(maxItems)} value, otherwise {@code null}
     */
    protected Integer resolveArrayMaxItems(MemberScope<?, ?> member) {
        if (member.isFakeContainerItemScope()) {
            return null;
        }
        return this.getArraySchemaAnnotation(member)
                .map(ArraySchema::maxItems)
                .filter(maxItems -> maxItems != Integer.MIN_VALUE)
                .orElse(null);
    }

    /**
     * Determine the given field/method's {@link ArraySchema} annotation is present and is marked as {@code uniqueItems = true}.
     *
     * @param member potentially annotated field/method
     * @return whether {@code @ArraySchema(uniqueItems = true)} is present
     */
    protected Boolean resolveArrayUniqueItems(MemberScope<?, ?> member) {
        if (member.isFakeContainerItemScope()) {
            return null;
        }
        return this.getArraySchemaAnnotation(member)
                .map(ArraySchema::uniqueItems)
                .filter(uniqueItemsFlag -> uniqueItemsFlag)
                .orElse(null);
    }

    /**
     * Implementation of the {@code CustomPropertyDefinitionProvider} to consider external references given in {@code @Schema(ref = ...)}.
     *
     * @param scope   field/method to determine custom definition for
     * @param context generation context
     * @return custom definition containing the looked-up external reference or null
     */
    protected CustomPropertyDefinition provideCustomSchemaDefinition(MemberScope<?, ?> scope, SchemaGenerationContext context) {
        Optional<String> externalReference = this.getSchemaAnnotationValue(scope, Schema::$ref, ref -> !ref.isEmpty());
        if (!externalReference.isPresent()) {
            return null;
        }
        // in Draft 6 and Draft 7, no other keywords are allowed besides a "$ref"
        CustomDefinition.AttributeInclusion attributeInclusion;
        switch (context.getGeneratorConfig().getSchemaVersion()) {
            case DRAFT_6:
                // fall-through (same as Draft 7)
            case DRAFT_7:
                attributeInclusion = CustomDefinition.AttributeInclusion.NO;
                break;
            default:
                attributeInclusion = CustomDefinition.AttributeInclusion.YES;
        }
        ObjectNode reference = context.getGeneratorConfig().createObjectNode()
                .put(context.getKeyword(SchemaKeyword.TAG_REF), externalReference.get());
        return new CustomPropertyDefinition(reference, attributeInclusion);
    }

    /**
     * Consider various remaining aspects.
     * <ul>
     * <li>{@code @Schema(not = ...)}</li>
     * <li>{@code @Schema(allOf = ...)}</li>
     * <li>{@code @Schema(minProperties = ...)}</li>
     * <li>{@code @Schema(maxProperties = ...)}</li>
     * <li>{@code @Schema(requiredProperties = ...)}</li>
     * </ul>
     *
     * @param memberAttributes already collected schema for the field/method
     * @param member           targeted field/method
     * @param context          generation context
     */
    protected void overrideInstanceAttributes(ObjectNode memberAttributes, MemberScope<?, ?> member, SchemaGenerationContext context) {
        Schema annotation = this.getSchemaAnnotationValue(member, Function.identity(), x -> true).orElse(null);
        if (annotation == null) return;
        if (annotation.not() != Void.class)
            memberAttributes.set(context.getKeyword(SchemaKeyword.TAG_NOT), context.createDefinitionReference(context.getTypeContext().resolve(annotation.not())));
        if (annotation.allOf().length > 0) {
            ArrayNode allOfArray = memberAttributes.withArray(context.getKeyword(SchemaKeyword.TAG_ALLOF));
            Stream.of(annotation.allOf())
                    .map(context.getTypeContext()::resolve)
                    .map(context::createDefinitionReference)
                    .forEach(allOfArray::add);
        }
        if (annotation.anyOf().length > 0) {
            ArrayNode allOfArray = memberAttributes.withArray(context.getKeyword(SchemaKeyword.TAG_ALLOF));
            ArrayNode anyOfArray = allOfArray.addObject().withArray(context.getKeyword(SchemaKeyword.TAG_ANYOF));
            Stream.of(annotation.anyOf())
                    .map(context.getTypeContext()::resolve)
                    .map(context::createDefinitionReference)
                    .forEach(anyOfArray::add);
        }
        if (annotation.oneOf().length > 0) {
            ArrayNode allOfArray = memberAttributes.withArray(context.getKeyword(SchemaKeyword.TAG_ALLOF));
            ArrayNode oneOfArray = allOfArray.addObject().withArray(context.getKeyword(SchemaKeyword.TAG_ONEOF));
            Stream.of(annotation.oneOf())
                    .map(context.getTypeContext()::resolve)
                    .map(context::createDefinitionReference)
                    .forEach(oneOfArray::add);
        }
        if (annotation.minProperties() > 0)
            memberAttributes.put(context.getKeyword(SchemaKeyword.TAG_PROPERTIES_MIN), annotation.minProperties());
        if (annotation.maxProperties() > 0)
            memberAttributes.put(context.getKeyword(SchemaKeyword.TAG_PROPERTIES_MAX), annotation.maxProperties());
        if (annotation.requiredProperties().length > 0) {
            Set<String> alreadyMentionedRequiredFields = new HashSet<>();
            ArrayNode requiredFieldNames = memberAttributes.withArray(context.getKeyword(SchemaKeyword.TAG_REQUIRED));
            requiredFieldNames.forEach(arrayItem -> alreadyMentionedRequiredFields.add(arrayItem.asText()));
            Stream.of(annotation.requiredProperties())
                    .filter(field -> !alreadyMentionedRequiredFields.contains(field))
                    .forEach(requiredFieldNames::add);
        }
        Map<String, Function<Schema, String>> properties = new HashMap<>();
        properties.put("$anchor", Schema::$anchor);
        properties.put("$comment", Schema::$comment);
        properties.put("$defs", Schema::$defs);
        properties.put("$dynamicAnchor", Schema::$dynamicAnchor);
        properties.put("$dynamicRef", Schema::$dynamicRef);
        properties.put("$id", Schema::$id);
        properties.put("$ref", Schema::$ref);
        properties.put("$schema", Schema::$schema);
        properties.put("$vocabulary", Schema::$vocabulary);

        properties.forEach((name, value) -> addProperty(memberAttributes, name, () -> value.apply(annotation)));

        if (annotation.examples().length > 0) {
            ArrayNode examples = memberAttributes.putArray("examples");
            for (String example : annotation.examples())
                examples.add(example);
        }

        if (!annotation._const().isEmpty())
            memberAttributes.put("const", annotation._const());
    }

    public static void addProperty(ObjectNode node, String name, Supplier<String> value) {
        if (value.get().isEmpty()) return;
        node.put(name, value.get());
    }

    /**
     * Look up a value from a {@link Schema} annotation on the given property or its associated field/getter or an external class referenced by
     * {@link Schema#implementation()}.
     *
     * @param member         field/method for which to look-up any present {@link Schema} annotation
     * @param valueExtractor the getter for the value from the annotation
     * @param valueFilter    filter that determines whether the value from a given annotation matches our criteria
     * @param <T>            the type of the returned value
     * @return the value from one of the matching {@link Schema} annotations or {@code Optional.empty()}
     */
    private <T> Optional<T> getSchemaAnnotationValue(MemberScope<?, ?> member, Function<Schema, T> valueExtractor, Predicate<T> valueFilter) {
        if (member.isFakeContainerItemScope()) {
            return this.getArraySchemaAnnotation(member)
                    .map(ArraySchema::schema)
                    .map(valueExtractor)
                    .filter(valueFilter);
        }
        Schema annotation = member.getAnnotationConsideringFieldAndGetter(Schema.class);
        if (annotation != null) {
            return Optional.of(annotation)
                    .map(valueExtractor)
                    .filter(valueFilter);
        }
        return this.getArraySchemaAnnotation(member)
                .map(ArraySchema::arraySchema)
                .map(valueExtractor)
                .filter(valueFilter);
    }

    /**
     * Create a {@link ConfigFunction} that extracts a value from the {@link Schema} annotation of a {@link TypeScope}.
     *
     * @param valueExtractor the getter for the value from the annotation
     * @param valueFilter    filter that determines whether the value from a given annotation matches our criteria
     * @param <T>            the type of the returned value
     * @return the value from the matching type's {@link Schema} annotation or {@code Optional.empty()}
     */
    private <T> ConfigFunction<TypeScope, T> createTypePropertyResolver(Function<Schema, T> valueExtractor, Predicate<T> valueFilter) {
        return typeScope -> Optional
                .ofNullable(typeScope.getType().getErasedType().getAnnotation(Schema.class))
                .map(valueExtractor).filter(valueFilter).orElse(null);
    }

    /**
     * Look-up the {@link ArraySchema} annotation on the given property or its associated field/getter.
     *
     * @param member field/method for which to look-up any present {@link ArraySchema} annotation
     * @return present {@link ArraySchema} annotation or {@code Optional.empty()}
     */
    private Optional<ArraySchema> getArraySchemaAnnotation(MemberScope<?, ?> member) {
        return Optional.ofNullable(member.getAnnotationConsideringFieldAndGetter(ArraySchema.class));
    }
}
