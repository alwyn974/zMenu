package re.alwyn974.schema.schemas.command;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

public class CommandsSchema {
    @Schema(description = "A list of commands")
    public Map<String, CommandSchema> commands;
}
