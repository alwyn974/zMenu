package re.alwyn974.schema.schemas.actions;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * JSON Schema for the action broadcast
 * @see fr.maxlego08.menu.loader.actions.BroadcastLoader
 */
public class ActionBroadcastSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"broadcast"})
    public String type;

    @Schema(description = "Whether to use mini message format", defaultValue = "true", name = "mini-message")
    public boolean miniMessage;
    @Schema(description = "Whether to use mini message format", defaultValue = "true", name = "minimessage")
    public boolean minimessage;

    @Schema(description = "The messages to broadcast", requiredMode = Schema.RequiredMode.REQUIRED)
    public List<String> messages;
}
