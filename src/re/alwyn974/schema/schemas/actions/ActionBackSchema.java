package re.alwyn974.schema.schemas.actions;

import re.alwyn974.schema.annotations.Schema;

/**
 * JSON Schema for the back action
 * @see fr.maxlego08.menu.button.loader.BackLoader
 */
public class ActionBackSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", required = true, allowedValues = {"back"})
    public String type;
}
