package re.alwyn974.schema.schemas.actions;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * JSON Schema for the action chat
 * @see fr.maxlego08.menu.loader.actions.ChatLoader
 */
public class ActionChatSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"chat"})
    public String type;

    @Schema(description = "The messages to send in chat", requiredMode = Schema.RequiredMode.REQUIRED)
    public List<String> messages;
}
