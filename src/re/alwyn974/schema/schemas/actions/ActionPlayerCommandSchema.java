package re.alwyn974.schema.schemas.actions;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * JSON Schema for the action player command
 * @see fr.maxlego08.menu.loader.actions.PlayerCommandLoader
 */
public class ActionPlayerCommandSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"player_command", "player_commands", "player command", "player commands"})
    public String type;

    @Schema(description = "If the command is sent to player chat", defaultValue = "false")
    public boolean commandInChat;
    @Schema(description = "If the command is sent to player chat", defaultValue = "false", name = "command-in-chat")
    public boolean commandinchat;

    @Schema(description = "The commands for the player to execute", requiredMode = Schema.RequiredMode.REQUIRED)
    public List<String> commands;
}