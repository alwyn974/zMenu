package re.alwyn974.schema.schemas.actions;

import fr.maxlego08.menu.api.requirement.data.ActionPlayerDataType;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * JSON Schema for the action data
 * @see fr.maxlego08.menu.loader.actions.DataLoader
 */
public class ActionDataSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"data"})
    public String type;

    @Schema(description = "The type of action to do on the data", defaultValue = "SET")
    public ActionPlayerDataType action;

    @Schema(description = "The key to store or retrieve data", requiredMode = Schema.RequiredMode.REQUIRED)
    public String key;

    @Schema(description = "The value to store", defaultValue = "true")
    public Object value;

    @Schema(description = "Expiration time in seconds", defaultValue = "0")
    public long seconds;
}
