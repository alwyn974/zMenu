package re.alwyn974.schema.schemas.inventory;

import re.alwyn974.schema.annotations.Schema;
import org.bukkit.event.inventory.InventoryType;
import re.alwyn974.schema.schemas.inventory.button.ButtonSchema;

import java.util.List;
import java.util.Map;

public class InventorySchema {
    @Schema(description = "The name of the inventory", names = {"title"})
    public String name;
    @Schema(description = "The size of the inventory. Can only be set if the type is CHEST. Size will be overridden if you use matrix !", required = true, allowedValues = {"9", "18", "27", "36", "45", "54"})
    public int size;
    @Schema(description = "The type of the inventory.", excludeValues = {"PLAYER", "CRAFTING"})
    public InventoryType type = InventoryType.CHEST;
    public Object fillItem;
    @Schema(description = "Specifies how often the buttons in the inventory should be refreshed, in milliseconds", names = {"update-interval"})
    public int updateInterval = 1000;
    @Schema(description = "Whether the player inventory should be cleared on opening and then restored on closing", names = {"clear-inventory"})
    public boolean clearInventory = false;
    @Schema(description = "Visually organize items in the inventory. Check https://docs.zmenu.dev/configurations/inventories#matrix for more information")
    public List<String> matrix;
    @Schema(description = "The buttons in the inventory", required = true)
    public Map<String, ButtonSchema> items;
    public Object openWithItem;
    public Object openRequirement;
    public List<Object> translatedName;
    @Schema(description = "The patterns to use in the inventory")
    public List<String> patterns;
}
