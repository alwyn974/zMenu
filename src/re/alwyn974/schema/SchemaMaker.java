package re.alwyn974.schema;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.util.RawValue;
import com.github.victools.jsonschema.generator.*;
import com.github.victools.jsonschema.module.swagger2.Swagger2Module;
import re.alwyn974.schema.annotations.Schema;
import re.alwyn974.schema.schemas.inventory.InventorySchema;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SchemaMaker {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Swagger2Module module = new Swagger2Module();
        SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(objectMapper, SchemaVersion.DRAFT_2020_12, OptionPreset.PLAIN_JSON)
                .with(module)
                .with(Option.MAP_VALUES_AS_ADDITIONAL_PROPERTIES, Option.FORBIDDEN_ADDITIONAL_PROPERTIES_BY_DEFAULT, Option.STRICT_TYPE_INFO);

        configBuilder.forFields().withInstanceAttributeOverride((node, scope, context) -> {
            Schema schema = scope.getAnnotation(Schema.class);
            if (schema == null) return;

            if (!schema.$anchor().isEmpty())
                node.put("$anchor", schema.$anchor());
            if (!schema.$comment().isEmpty())
                node.put("$comment", schema.$comment());
            if (!schema.$defs().isEmpty())
                node.put("$defs", schema.$defs());
            if (!schema.$dynamicAnchor().isEmpty())
                node.put("$dynamicAnchor", schema.$dynamicAnchor());
            if (!schema.$dynamicRef().isEmpty())
                node.put("$dynamicRef", schema.$dynamicRef());
            if (!schema.$id().isEmpty())
                node.put("$id", schema.$id());
            if (!schema.$ref().isEmpty())
                node.put("$ref", schema.$ref());
            if (!schema.$schema().isEmpty())
                node.put("$schema", schema.$schema());
            if (!schema.$vocabulary().isEmpty())
                node.put("$vocabulary", schema.$vocabulary());
            if (schema.examples().length > 0) {
                ArrayNode examples = node.putArray("examples");
                for (String example : schema.examples())
                    examples.add(example);
            }
            if (schema.types().length > 0) {
                node.remove("type");
                ArrayNode types = node.putArray("type");
                for (String type : schema.types())
                    types.add(type);
            } else if (!schema.type().isEmpty())
                node.put("type", schema.type());
            if (!schema._const().isEmpty())
                node.put("const", schema._const());

            if (!node.has("default")) {
                try {
                    Field field = scope.getRawMember();
                    Object fieldTypeInstance = field.getDeclaringClass().getDeclaredConstructor().newInstance();
                    field.setAccessible(true);
                    Object value = field.get(fieldTypeInstance);
                    if (value.getClass().isPrimitive() || value.getClass() == String.class)
                        node.putPOJO("default", value);
                } catch (Exception e) {
                    System.out.printf("Error while setting default value for %s#%s\n", scope.getRawMember().getDeclaringClass(), scope.getName());
                }
            }
        });
        SchemaGeneratorConfig config = configBuilder.build();
        SchemaGenerator generator = new SchemaGenerator(config);
        JsonNode jsonSchema = generator.generateSchema(InventorySchema.class);

        Files.write(Paths.get("inventory.json"), jsonSchema.toPrettyString().getBytes(StandardCharsets.UTF_8));
    }
}
