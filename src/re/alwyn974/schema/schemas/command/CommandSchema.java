package re.alwyn974.schema.schemas.command;

import re.alwyn974.schema.annotations.Schema;

import java.util.List;

/**
 * JSON Schema for the command
 * @see fr.maxlego08.menu.loader.CommandLoader
 */
public class CommandSchema {
    @Schema(description = "The command name")
    public String command;

    @Schema(description = "The permission required to use this command")
    public String permission;

    @Schema(description = "The inventory associated with this command")
    public String inventory;

    @Schema(description = "The message displayed when permission is denied", required = true)
    public String denyMessage;

    @Schema(description = "Aliases for the command")
    public List<String> aliases;

    @Schema(description = "Command arguments")
    public List<CommandArgumentSchema> arguments;
}