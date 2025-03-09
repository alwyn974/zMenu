package re.alwyn974.schema.schemas.actions;

import re.alwyn974.schema.annotations.Schema;

import java.util.List;

/**
 * JSON Schema for the action console command
 * @see fr.maxlego08.menu.loader.actions.ConsoleCommandLoader
 */
public class ActionConsoleCommandSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", required = true, allowedValues = {
            "console_command", "console_commands", "console commands", "console command", "command", "commands"
    })
    public String type;

    @Schema(description = "The commands to execute in the console", required = true)
    public List<String> commands;
}
