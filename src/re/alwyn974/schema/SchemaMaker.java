package re.alwyn974.schema;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.victools.jsonschema.generator.*;
import re.alwyn974.schema.generator.SchemaModule;
import re.alwyn974.schema.schemas.inventory.InventorySchema;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SchemaMaker {

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        SchemaModule module = new SchemaModule();
        SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(objectMapper, SchemaVersion.DRAFT_2020_12, OptionPreset.PLAIN_JSON)
                .with(module)
                .with(Option.MAP_VALUES_AS_ADDITIONAL_PROPERTIES, Option.FORBIDDEN_ADDITIONAL_PROPERTIES_BY_DEFAULT, Option.STRICT_TYPE_INFO);

        SchemaGeneratorConfig config = configBuilder.build();
        SchemaGenerator generator = new SchemaGenerator(config);
        JsonNode jsonSchema = generator.generateSchema(InventorySchema.class);

        Files.write(Paths.get("inventory.json"), jsonSchema.toPrettyString().getBytes(StandardCharsets.UTF_8));
    }
}
