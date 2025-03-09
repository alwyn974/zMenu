package re.alwyn974.schema.annotations;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.FIELD;

@Target({FIELD, TYPE, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ArraySchema {

    /**
     * The schemas of the items in the array
     *
     * @since 2.2.12
     *
     * @deprecated since 2.2.21, use {@link #schema()} instead. Marked for removal in future versions.
     * @return items
     */
    @Deprecated
    Schema items() default @Schema;

    /**
     * The schema of the items in the array
     *
     * @return schema
     */
    Schema schema() default @Schema;

    /**
     * Allows to define the properties to be resolved into properties of the schema of type `array` (not the ones of the
     * `items` of such schema which are defined in {@link #schema() schema}.
     *
     * @return arraySchema
     *
     * @since 2.0.2
     */
    Schema arraySchema() default @Schema;

    /**
     * sets the maximum number of items in an array.  Ignored if value is Integer.MIN_VALUE.
     *
     * @return integer representing maximum number of items in array
     **/
    int maxItems() default Integer.MIN_VALUE;

    /**
     * sets the minimum number of items in an array.  Ignored if value is Integer.MAX_VALUE.
     *
     * @return integer representing minimum number of items in array
     **/
    int minItems() default Integer.MAX_VALUE;

    /**
     * determines whether an array of items will be unique
     *
     * @return boolean - whether items in an array are unique or repeating
     **/
    boolean uniqueItems() default false;
}
