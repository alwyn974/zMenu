package re.alwyn974.schema.schemas.actions;

import re.alwyn974.schema.annotations.Schema;

/**
 * JSON Schema for the action luck permission set
 * @see fr.maxlego08.menu.loader.actions.LuckPermissionSetLoader
 */
public class ActionLuckPermissionSetSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", required = true, allowedValues = {"permission-set", "permission set", "set permission", "set-permission"})
    public String type;

    @Schema(description = "The permission to set", required = true)
    public String permission;
}