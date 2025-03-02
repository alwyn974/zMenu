package re.alwyn974.schema.annotations;

import io.swagger.v3.oas.annotations.OpenAPI31;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({FIELD, TYPE, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Schema {
    /**
     * A less common way to identify a subschema is to create a named anchor in the schema using the $anchor
     * @see <a href="https://json-schema.org/understanding-json-schema/structuring#anchor">JSON Schema Spec</a>
     */
    String $anchor() default "";

    /**
     * Comment should only be used for leaving notes.
     * It should not be used to communicate to users of the schema.
     * @see <a href="https://json-schema.org/understanding-json-schema/reference/comments#comments">JSON Schema Spec</a>
     */
    String $comment() default "";

    /**
     * The $defs keyword provides a standardized location for schema authors to inline re-usable JSON Schemas into a more general schema.
     * @see <a href="https://json-schema.org/understanding-json-schema/structuring#defs">JSON Schema Spec</a>
     */
    String $defs() default "";

    /**
     * The $dynamicAnchor keyword is used to define a dynamic anchor.
     * @see <a href="https://json-schema.org/draft/2020-12/json-schema-core#name-example-of-recursive-schema">JSON Schema Spec</a>
     */
    String $dynamicAnchor() default "";

    /**
     * The $dynamicRef keyword is used to define a dynamic reference.
     * @see <a href="https://json-schema.org/draft/2020-12/json-schema-core#name-example-of-recursive-schema">JSON Schema Spec</a>
     */
    String $dynamicRef() default "";

    /**
     * The $id keyword defines a URI for the schema, and the schema can be referenced using this URI.
     * @see <a href="https://json-schema.org/understanding-json-schema/structuring#id">JSON Schema Spec</a>
     */
    String $id() default "";

    /**
     * References a schema definition in an external json schema document.
     *  @see <a href="https://json-schema.org/understanding-json-schema/structuring#dollarref">JSON Schema Spec</a>
     */
    String $ref() default "";

    /**
     * The $schema keyword is used to declare which dialect of JSON Schema the schema was written for.
     * @see <a href="https://json-schema.org/understanding-json-schema/structuring#schema">JSON Schema Spec</a>
     */
    String $schema() default "";

    /**
     * @see <a href="https://json-schema.org/draft/2020-12/json-schema-core#name-the-vocabulary-keyword">JSON Schema Spec</a>
     */
    String $vocabulary() default "";

    /**
     * Name of the property. By default, it will use the variable name
     */
    String name() default "";

    /**
     * Use the variable name as the property name
     */
    boolean useVariableName() default true;

    /**
     * If the property has multiple names. This will be used as "oneOf"
     * It will be appended to the variable if useVariableName is true
     */
    String[] names() default {};

    /**
     * The default value of the property. If the value is an empty string, it will be ignored.
     * If the variable has a default value, it will be used.
     */
    String defaultValue() default "";

    /**
     * A long description of the property
     */
    String description() default "";

    /**
     * A short description of the property
     */
    String title() default "";

    /**
     * Specify a schema when using type Object
     **/
    Class<?> implementation() default Void.class;

    /**
     * Provides a java class to be used to disallow matching properties.
     **/
    Class<?> not() default Void.class;

    /**
     * Provides an array of java class implementations which can be used to describe multiple acceptable schemas.
     * If more than one match the derived schemas, a validation error will occur.
     **/
    Class<?>[] oneOf() default {};

    /**
     * Provides an array of java class implementations which can be used to describe multiple acceptable schemas.
     * If any match, the schema will be considered valid.
     **/
    Class<?>[] anyOf() default {};

    /**
     * Provides an array of java class implementations which can be used to describe multiple acceptable schemas.
     * If all matches, the schema will be considered valid
     **/
    Class<?>[] allOf() default {};

    /**
     * A pattern that the value must satisfy.
     * Ignored if the value is an empty string.
     **/
    String pattern() default "";

    /**
     * Mandates that the annotated item is required or not.
     */
    boolean required() default false;

    /**
     * Constrains a value such that when divided by the multipleOf, the remainder must be an integer.
     * Ignored if the value is 0.
     **/
    double multipleOf() default 0;

    /**
     * Sets the maximum numeric value for a property.
     * Ignored if the value is Long.MIN_VALUE.
     **/
    long maximum() default Long.MIN_VALUE;

    /**
     * Sets the maximum exclusive numeric value for a property.
     * Ignored if the value is Long.MIN_VALUE.
     **/
    long exclusiveMaximum() default Long.MIN_VALUE;

    /**
     * Sets the minimum numeric value for a property.
     * Ignored if the value is Long.MAX_VALUE.
     **/
    long minimum() default Long.MAX_VALUE;

    /**
     * Sets the minimum exclusive numeric value for a property.
     * Ignored if the value is Long.MAX_VALUE.
     **/
    long exclusiveMinimum() default Long.MAX_VALUE;

    /**
     * Sets the maximum length of a string value.  Ignored if the value is negative.
     *
     * @return the maximum length of this schema
     **/
    int maxLength() default Integer.MAX_VALUE;

    /**
     * Sets the minimum length of a string value.
     * Ignored if the value is negative.
     **/
    int minLength() default 0;

    /**
     * Constrains the number of arbitrary properties when additionalProperties is defined.
     * Ignored if value is 0.
     **/
    int maxProperties() default 0;

    /**
     * Constrains the number of arbitrary properties when additionalProperties is defined.
     * Ignored if value is 0.
     **/
    int minProperties() default 0;

    /**
     * Allows multiple properties in an object to be marked as required.
     **/
    String[] requiredProperties() default {};

    /**
     * Provides an optional override for the format.
     * If a consumer is unaware of the meaning of the format, they shall fall back to using the basic type without format.
     * For example, if "type: integer, format: int128" is used to designate a very large integer, most consumers will not understand how to handle it, and fall back to simply "type: integer"
     **/
    String format() default "";

    /**
     * If true, designates a value as possibly null.
     * It will include null in type
     **/
    boolean nullable() default false;

    /**
     * Provides multiple examples
     **/
    String[] examples() default {};

    /**
     * Provides an override for the basic type of the schema.
     * Valid types:
     * string, number, integer, object, array, boolean and null
     **/
    String type() default "";

    /**
     * Provides an override for the basic type of the schema.
     * You can specify multiple types.
     * It can't be used with type.
     * Valid types:
     * string, number, integer, object, array, boolean and null
     */
    String[] types() default {};

    /**
     * Only allow this value
     */
    String _const() default "";

    /**
     * Provides a list of allowed values. This field map to the enum property.
     * If the variable type is an enum, it will be added and not overridden.
     */
    String[] allowedValues() default {};

    /**
     * Don't add the property to the schema
     */
    boolean hidden() default false;
}
