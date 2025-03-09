package re.alwyn974.schema.schemas.actions;

import re.alwyn974.schema.annotations.Schema;

/**
 * JSON Schema for the action connect
 * @see fr.maxlego08.menu.loader.actions.ConnectLoader
 */
public class ActionConnectSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", required = true, allowedValues = {"connect"})
    public String type;

    @Schema(description = "The server to connect to", required = true)
    public String server = "hub";
}