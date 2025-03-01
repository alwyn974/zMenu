package re.alwyn974.schema.schemas.actions;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * JSON Schema for the action connect
 * @see fr.maxlego08.menu.loader.actions.ConnectLoader
 */
public class ActionConnectSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"connect"})
    public String type;

    @Schema(description = "The server to connect to", requiredMode = Schema.RequiredMode.REQUIRED, defaultValue = "hub")
    public String server;
}