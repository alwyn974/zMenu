package re.alwyn974.schema.schemas.command;

import re.alwyn974.schema.annotations.Schema;

import java.util.Map;

public class CommandsSchema {
    @Schema(description = "A list of commands")
    public Map<String, CommandSchema> commands;
}
