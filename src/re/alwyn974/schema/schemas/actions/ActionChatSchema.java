package re.alwyn974.schema.schemas.actions;

import re.alwyn974.schema.annotations.Schema;

import java.util.List;

/**
 * JSON Schema for the action chat
 * @see fr.maxlego08.menu.loader.actions.ChatLoader
 */
public class ActionChatSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", required = true, allowedValues = {"chat"})
    public String type;

    @Schema(description = "The messages to send in chat", required = true)
    public List<String> messages;
}
