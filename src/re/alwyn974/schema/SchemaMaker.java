package re.alwyn974.schema;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.victools.jsonschema.generator.*;
import com.github.victools.jsonschema.module.swagger2.Swagger2Module;
import re.alwyn974.schema.schemas.command.CommandsSchema;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SchemaMaker {
    public static void main(String[] args) throws IOException {
        // https://github.com/victools/jsonschema-generator/tree/main/jsonschema-maven-plugin
        ObjectMapper objectMapper = new ObjectMapper();
//        AddonModule module = new AddonModule();
        Swagger2Module module = new Swagger2Module();
        SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(objectMapper, SchemaVersion.DRAFT_7, OptionPreset.PLAIN_JSON)
                .with(module)
                .with(Option.MAP_VALUES_AS_ADDITIONAL_PROPERTIES)
                .with(Option.FORBIDDEN_ADDITIONAL_PROPERTIES_BY_DEFAULT);
        SchemaGeneratorConfig config = configBuilder.build();
        SchemaGenerator generator = new SchemaGenerator(config);
        JsonNode jsonSchema = generator.generateSchema(CommandsSchema.class);

        Files.write(Paths.get("commands-schema.json"), jsonSchema.toPrettyString().getBytes(StandardCharsets.UTF_8));
    }
}
