package re.alwyn974.schema.schemas.actions;

import re.alwyn974.schema.annotations.Schema;

import java.util.List;

/**
 * JSON Schema for the action message
 * @see fr.maxlego08.menu.loader.actions.MessageLoader
 */
public class ActionMessageSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", required = true, allowedValues = {"message", "messages"})
    public String type;

    @Schema(description = "Whether to use mini message format", name = "mini-message")
    public boolean minimessage = true;

    @Schema(description = "The messages to send", required = true)
    public List<String> messages;
}