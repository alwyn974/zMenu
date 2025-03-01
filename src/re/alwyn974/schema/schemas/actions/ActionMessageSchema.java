package re.alwyn974.schema.schemas.actions;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * JSON Schema for the action message
 * @see fr.maxlego08.menu.loader.actions.MessageLoader
 */
public class ActionMessageSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"message", "messages"})
    public String type;

    @Schema(description = "Whether to use mini message format", defaultValue = "true", name = "mini-message")
    public boolean miniMessage;
    @Schema(description = "Whether to use mini message format", defaultValue = "true", name = "minimessage")
    public boolean minimessage;

    @Schema(description = "The messages to send", requiredMode = Schema.RequiredMode.REQUIRED)
    public List<String> messages;
}