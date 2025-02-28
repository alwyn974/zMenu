package re.alwyn974.schema.schemas.command;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class CommandSchema {
    @Schema(description = "The command name")
    private String command;

    @Schema(description = "The permission required to use this command")
    private String permission;

    @Schema(description = "The inventory associated with this command")
    private String inventory;

    @Schema(description = "The message displayed when permission is denied", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String denyMessage;

    @Schema(description = "Aliases for the command")
    private List<String> aliases;

    @Schema(description = "Command arguments")
    private List<CommandArgument> arguments;
}