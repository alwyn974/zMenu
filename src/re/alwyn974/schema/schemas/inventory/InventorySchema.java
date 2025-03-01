package re.alwyn974.schema.schemas.inventory;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import org.bukkit.event.inventory.InventoryType;
import re.alwyn974.schema.schemas.button.ButtonSchema;

import java.util.List;
import java.util.Map;

public class InventorySchema {
    @Schema(description = "The name of the inventory")
    public String name;
    @Schema(description = "The name of the inventory")
    public String title;
    @Schema(description = "The size of the inventory. Can only be set if the type is CHEST. Size will be overriden if you use matrix !", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"9", "18", "27", "36", "45", "54"})
    public int size;
    @Schema(description = "The type of the inventory. PLAYER & CRAFTING can't be used !", defaultValue = "CHEST")
    public InventoryType type;
    public Object fillItem;
    public Object fillitem;
    @Schema(description = "Specifies how often the buttons in the inventory should be refreshed, in milliseconds", defaultValue = "1000")
    public int updateInterval;
    @Schema(description = "Specifies how often the buttons in the inventory should be refreshed, in milliseconds", defaultValue = "1000", name = "update-interval")
    public int updateinterval;
    @Schema(description = "Whether the player inventory should be cleared on opening and then restored on closing", defaultValue = "false")
    public boolean clearInventory;
    @Schema(description = "Whether the player inventory should be cleared on opening and then restored on closing", defaultValue = "false", name = "clear-inventory")
    public boolean clearinventory;
    @Schema(description = "Visually organize items in the inventory. Check https://docs.zmenu.dev/configurations/inventories#matrix for more information")
    public List<String> matrix;
    @Schema(description = "The buttons in the inventory", requiredMode = Schema.RequiredMode.REQUIRED)
    public Map<String, ButtonSchema> items;
    public Object openWithItem;
    public Object openRequirement;
    public List<Object> translatedName;
    @Schema(description = "The patterns to use in the inventory")
    public List<String> patterns;
}
