package re.alwyn974.schema.generator;

import com.fasterxml.classmate.ResolvedType;
import com.github.victools.jsonschema.generator.CustomDefinition;
import com.github.victools.jsonschema.generator.CustomDefinitionProviderV2;
import com.github.victools.jsonschema.generator.SchemaGenerationContext;
import com.github.victools.jsonschema.generator.SchemaKeyword;
import re.alwyn974.schema.annotations.Schema;

import java.util.Optional;

/**
 * Replace any type annotated with {@code @Schema($ref = "...")} with the specified reference value, unless it is the main schema being targeted.
 */
public class ExternalRefCustomDefinitionProvider implements CustomDefinitionProviderV2 {

    /**
     * Reference to the targeted type, for which a schema is being generated, that should not be replaced by a "ref".
     */
    private Class<?> mainType;

    @Override
    public CustomDefinition provideCustomSchemaDefinition(ResolvedType javaType, SchemaGenerationContext context) {
        Class<?> erasedType = javaType.getErasedType();
        if (this.mainType == null) {
            this.mainType = erasedType;
        }
        if (this.mainType == erasedType) {
            return null;
        }
        return Optional.ofNullable(erasedType.getAnnotation(Schema.class))
                .map(Schema::$ref)
                .filter(ref -> !ref.isEmpty())
                .map(ref -> context.getGeneratorConfig().createObjectNode().put(context.getKeyword(SchemaKeyword.TAG_REF), ref))
                .map(schema -> new CustomDefinition(schema, CustomDefinition.INLINE_DEFINITION, CustomDefinition.INCLUDING_ATTRIBUTES))
                .orElse(null);
    }

    @Override
    public void resetAfterSchemaGenerationFinished() {
        this.mainType = null;
    }
}