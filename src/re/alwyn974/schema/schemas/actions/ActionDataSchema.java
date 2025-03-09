package re.alwyn974.schema.schemas.actions;

import fr.maxlego08.menu.api.requirement.data.ActionPlayerDataType;
import re.alwyn974.schema.annotations.Schema;

/**
 * JSON Schema for the action data
 * @see fr.maxlego08.menu.loader.actions.DataLoader
 */
public class ActionDataSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", required = true, allowedValues = {"data"})
    public String type;

    @Schema(description = "The type of action to do on the data")
    public ActionPlayerDataType action = ActionPlayerDataType.SET;

    @Schema(description = "The key to store or retrieve data", required = true)
    public String key;

    @Schema(description = "The value to store")
    public Object value = true;

    @Schema(description = "Expiration time in seconds")
    public long seconds = 0;
}
