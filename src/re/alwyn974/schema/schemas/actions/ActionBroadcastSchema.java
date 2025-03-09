package re.alwyn974.schema.schemas.actions;

import re.alwyn974.schema.annotations.Schema;

import java.util.List;

/**
 * JSON Schema for the action broadcast
 * @see fr.maxlego08.menu.loader.actions.BroadcastLoader
 */
public class ActionBroadcastSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", required = true, allowedValues = {"broadcast"})
    public String type;

    @Schema(description = "Whether to use mini message format", names = {"mini-message"})
    public boolean minimessage = true;

    @Schema(description = "The messages to broadcast", required = true)
    public List<String> messages;
}
