package re.alwyn974.schema.schemas.actions;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * JSON Schema for the back action
 * @see fr.maxlego08.menu.button.loader.BackLoader
 */
public class ActionBackSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"back"})
    public String type;
}
