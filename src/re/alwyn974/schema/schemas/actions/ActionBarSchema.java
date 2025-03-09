package re.alwyn974.schema.schemas.actions;

import re.alwyn974.schema.annotations.Schema;

/**
 * JSON Schema for the action bar action
 * @see fr.maxlego08.menu.loader.actions.ActionBarLoader
 */
public class ActionBarSchema extends ActionBaseSchema {
    @Schema(description = "Action type", allowedValues = { "action", "actionbar" }, required = true)
    public String type;

    @Schema(description = "Whether to use mini message")
    public boolean minimessage = true;

    @Schema(description = "The message to display", required = true)
    public String message;
}
