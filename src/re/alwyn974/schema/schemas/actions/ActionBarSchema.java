package re.alwyn974.schema.schemas.actions;

import io.swagger.v3.oas.annotations.media.Schema;

public class ActionBarSchema extends ActionBaseSchema {
    @Schema(description = "Action type", allowableValues = { "action", "actionbar" })
    private String type;

    @Schema(description = "Whether to use mini-message", defaultValue = "true")
    private boolean miniMessage;

    @Schema(description = "The message to display", requiredMode = Schema.RequiredMode.REQUIRED)
    private String message;
}
