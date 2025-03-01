package re.alwyn974.schema.schemas.actions;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * JSON Schema for the action luck permission set
 * @see fr.maxlego08.menu.loader.actions.LuckPermissionSetLoader
 */
public class ActionLuckPermissionSetSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"permission-set", "permission set", "set permission", "set-permission"})
    public String type;

    @Schema(description = "The permission to set", requiredMode = Schema.RequiredMode.REQUIRED)
    public String permission;
}