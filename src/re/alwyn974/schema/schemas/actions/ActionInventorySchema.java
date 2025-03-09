package re.alwyn974.schema.schemas.actions;

import re.alwyn974.schema.annotations.Schema;

import java.util.List;

/**
 * JSON Schema for the action inventory
 * @see fr.maxlego08.menu.loader.actions.InventoryLoader
 */
public class ActionInventorySchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", required = true, allowedValues = {"inventory"})
    public String type;

    @Schema(description = "The inventory to open", required = true)
    public String inventory;

    @Schema(description = "The plugin that owns the inventory", required = true)
    public String plugin;

    @Schema(description = "The page of the inventory", required = true)
    public String page;

    @Schema(description = "The list of arguments")
    public List<String> arguments;
}









