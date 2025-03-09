package re.alwyn974.schema.generator;

import com.github.victools.jsonschema.generator.SchemaGenerationContext;
import com.github.victools.jsonschema.generator.impl.DefinitionKey;
import com.github.victools.jsonschema.generator.naming.DefaultSchemaDefinitionNamingStrategy;
import re.alwyn974.schema.annotations.Schema;

import java.util.Map;

/**
 * Naming strategy for the keys in the {@code definitions}/{@code $defs} of the produced schema, based on {@code @Schema(name = ...)}.
 */
public class SchemaDefinitionNamingStrategy implements com.github.victools.jsonschema.generator.naming.SchemaDefinitionNamingStrategy {

    private final com.github.victools.jsonschema.generator.naming.SchemaDefinitionNamingStrategy baseStrategy;

    /**
     * Constructor expecting a base strategy to be applied if there is no {@link Schema} annotation with a specific {@code name} being specified.
     *
     * @param baseStrategy fall-back strategy to be applied
     */
    public SchemaDefinitionNamingStrategy(com.github.victools.jsonschema.generator.naming.SchemaDefinitionNamingStrategy baseStrategy) {
        if (baseStrategy == null) {
            this.baseStrategy = new DefaultSchemaDefinitionNamingStrategy();
        } else {
            this.baseStrategy = baseStrategy;
        }
    }

    @Override
    public String getDefinitionNameForKey(DefinitionKey key, SchemaGenerationContext generationContext) {
        Schema annotation = key.getType().getErasedType().getAnnotation(Schema.class);
        if (annotation == null || annotation.name().isEmpty()) {
            return this.baseStrategy.getDefinitionNameForKey(key, generationContext);
        }
        return annotation.name();
    }

    @Override
    public void adjustDuplicateNames(Map<DefinitionKey, String> subschemasWithDuplicateNames, SchemaGenerationContext generationContext) {
        this.baseStrategy.adjustDuplicateNames(subschemasWithDuplicateNames, generationContext);
    }

    @Override
    public String adjustNullableName(DefinitionKey key, String definitionName, SchemaGenerationContext generationContext) {
        return this.baseStrategy.adjustNullableName(key, definitionName, generationContext);
    }
}