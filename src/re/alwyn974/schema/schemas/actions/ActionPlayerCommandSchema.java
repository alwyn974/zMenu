package re.alwyn974.schema.schemas.actions;

import re.alwyn974.schema.annotations.Schema;

import java.util.List;

/**
 * JSON Schema for the action player command
 * @see fr.maxlego08.menu.loader.actions.PlayerCommandLoader
 */
public class ActionPlayerCommandSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", required = true, allowedValues = {"player_command", "player_commands", "player command", "player commands"})
    public String type;

    @Schema(description = "If the command is sent to player chat", names = {"command-in-chat"})
    public boolean commandInChat = false;

    @Schema(description = "The commands for the player to execute", required = true)
    public List<String> commands;
}