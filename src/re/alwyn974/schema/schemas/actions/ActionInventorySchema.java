package re.alwyn974.schema.schemas.actions;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * JSON Schema for the action inventory
 * @see fr.maxlego08.menu.loader.actions.InventoryLoader
 */
public class ActionInventorySchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"inventory"})
    public String type;

    @Schema(description = "The inventory to open", requiredMode = Schema.RequiredMode.REQUIRED)
    public String inventory;

    @Schema(description = "The plugin that owns the inventory", requiredMode = Schema.RequiredMode.REQUIRED)
    public String plugin;

    @Schema(description = "The page of the inventory", requiredMode = Schema.RequiredMode.REQUIRED)
    public String page;

    @Schema(description = "The list of arguments", name = "arguments")
    public List<String> arguments;
}









