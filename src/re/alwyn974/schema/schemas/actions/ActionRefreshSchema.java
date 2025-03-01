package re.alwyn974.schema.schemas.actions;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * JSON Schema for the action refresh
 * @see fr.maxlego08.menu.loader.actions.RefreshLoader
 */
public class ActionRefreshSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"refresh"})
    public String type;
}
