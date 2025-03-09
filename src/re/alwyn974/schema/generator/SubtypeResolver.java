package re.alwyn974.schema.generator;

import com.fasterxml.classmate.ResolvedType;
import com.github.victools.jsonschema.generator.SchemaGenerationContext;
import com.github.victools.jsonschema.generator.TypeContext;
import re.alwyn974.schema.annotations.Schema;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Subtype resolver considering {@code @Schema(anyOf = ...)}.
 */
public class SubtypeResolver implements com.github.victools.jsonschema.generator.SubtypeResolver {

    @Override
    public List<ResolvedType> findSubtypes(ResolvedType declaredType, SchemaGenerationContext context) {
        Schema annotation = declaredType.getErasedType().getAnnotation(Schema.class);
        if (annotation == null || (annotation.anyOf().length == 0)) {
            return null;
        }
        TypeContext typeContext = context.getTypeContext();
        return Stream.of(annotation.anyOf())
                .map(typeContext::resolve)
                .collect(Collectors.toList());
    }
}