package re.alwyn974.schema;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.victools.jsonschema.generator.*;
import re.alwyn974.schema.annotations.Schema;
import re.alwyn974.schema.generator.SchemaModule;
import re.alwyn974.schema.schemas.inventory.InventorySchema;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class SchemaMaker {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        SchemaModule module = new SchemaModule();
        SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(objectMapper, SchemaVersion.DRAFT_2020_12, OptionPreset.PLAIN_JSON)
                .with(module)
                .with(Option.MAP_VALUES_AS_ADDITIONAL_PROPERTIES, Option.FORBIDDEN_ADDITIONAL_PROPERTIES_BY_DEFAULT, Option.STRICT_TYPE_INFO);

        configBuilder.forFields().withInstanceAttributeOverride((node, scope, context) -> {
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
                    System.out.printf("Error while setting default value for %s#%s\n", scope.getRawMember().getDeclaringClass(), scope.getName());
                }
            }
        });

        configBuilder.forTypesInGeneral().withTypeAttributeOverride((node, scope, context) -> {
            if (!node.has("type") || !node.get("type").asText().equals("object"))
                return;
            if (scope.getType().isArray() || scope.getType().isInstanceOf(Map.class))
                return;
            ResolvedType type = scope.getType();
            type.getMemberFields().stream().filter(field -> field.getRawMember().isAnnotationPresent(Schema.class)).forEach(field -> {
                Schema schema = field.getRawMember().getAnnotation(Schema.class);
                if (schema.names().length == 0) return;
                String fieldName = schema.name().isEmpty() ? field.getRawMember().getName() : schema.name();
                JsonNode properties = node.get("properties");
                JsonNode propertyValue = properties.findValue(fieldName);
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

        SchemaGeneratorConfig config = configBuilder.build();
        SchemaGenerator generator = new SchemaGenerator(config);
        JsonNode jsonSchema = generator.generateSchema(InventorySchema.class);

        Files.write(Paths.get("inventory.json"), jsonSchema.toPrettyString().getBytes(StandardCharsets.UTF_8));
    }
}
