package re.alwyn974.schema.schemas.actions;

import re.alwyn974.schema.annotations.Schema;

/**
 * JSON Schema for the action close
 * @see fr.maxlego08.menu.loader.actions.CloseLoader
 */
public class ActionCloseSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", required = true, allowedValues = {"close"})
    public String type;
}