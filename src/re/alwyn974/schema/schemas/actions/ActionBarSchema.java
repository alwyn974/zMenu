package re.alwyn974.schema.schemas.actions;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * JSON Schema for the action bar action
 * @see fr.maxlego08.menu.loader.actions.ActionBarLoader
 */
public class ActionBarSchema extends ActionBaseSchema {
    @Schema(description = "Action type", allowableValues = { "action", "actionbar" }, requiredMode = Schema.RequiredMode.REQUIRED)
    private String type;

    @Schema(description = "Whether to use mini message", defaultValue = "true")
    private boolean minimessage;

    @Schema(description = "The message to display", requiredMode = Schema.RequiredMode.REQUIRED)
    private String message;
}
