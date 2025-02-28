package re.alwyn974.schema;

import com.cryptomorin.xseries.XSound;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.victools.jsonschema.generator.*;
import com.github.victools.jsonschema.module.swagger2.Swagger2Module;
import re.alwyn974.schema.annotations.XSoundSchema;
import re.alwyn974.schema.schemas.command.CommandsSchema;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class SchemaMaker {
    public static void main(String[] args) throws IOException {
        // https://github.com/victools/jsonschema-generator/tree/main/jsonschema-maven-plugin
        ObjectMapper objectMapper = new ObjectMapper();
        Swagger2Module module = new Swagger2Module();
        SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(objectMapper, SchemaVersion.DRAFT_7, OptionPreset.PLAIN_JSON)
                .with(module)
                .with(Option.MAP_VALUES_AS_ADDITIONAL_PROPERTIES,
                        Option.FORBIDDEN_ADDITIONAL_PROPERTIES_BY_DEFAULT,
                        Option.STRICT_TYPE_INFO);
        CustomPropertyDefinitionProvider xSoundProvider = (member, context) -> Optional
                .ofNullable(member.getAnnotationConsideringFieldAndGetter(XSoundSchema.class))
                .map(annotation -> {
                    ObjectNode node = context.getGeneratorConfig().createObjectNode();
                    node.put("type", "string");
                    ArrayNode enumNode = node.putArray("enum");
                    for (XSound value : XSound.getValues())
                        enumNode.add(value.name());
                    return new CustomPropertyDefinition(node);
                }).orElse(null);
        configBuilder.forFields().withCustomDefinitionProvider(xSoundProvider);

//        configBuilder.forTypesInGeneral().withCustomDefinitionProvider((javaType, context) -> {
//            System.out.println(javaType.getErasedType());
//            XSoundSchema annotation = javaType.getErasedType().getAnnotation(XSoundSchema.class);
//            System.out.println(annotation);
//            if (annotation == null) return null;
//            ObjectNode node = context.getGeneratorConfig().createObjectNode();
//            node.put("type", "string");
//            ArrayNode enumNode = node.putArray("enum");
//            for (XSound value : XSound.getValues())
//                enumNode.add(value.name());
//            return new CustomDefinition(node, true);
//        });
        SchemaGeneratorConfig config = configBuilder.build();
        SchemaGenerator generator = new SchemaGenerator(config);
        JsonNode jsonSchema = generator.generateSchema(CommandsSchema.class);

        Files.write(Paths.get("commands-schema.json"), jsonSchema.toPrettyString().getBytes(StandardCharsets.UTF_8));
    }
}
