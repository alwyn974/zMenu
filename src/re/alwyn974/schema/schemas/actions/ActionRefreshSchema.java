package re.alwyn974.schema.schemas.actions;

import re.alwyn974.schema.annotations.Schema;

/**
 * JSON Schema for the action refresh
 * @see fr.maxlego08.menu.loader.actions.RefreshLoader
 */
public class ActionRefreshSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", required = true, allowedValues = {"refresh"})
    public String type;
}
