package re.alwyn974.schema.schemas.inventory.item;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import re.alwyn974.schema.schemas.actions.*;
import re.alwyn974.schema.schemas.inventory.RequirementSchema;

import java.util.List;
import java.util.Map;

public class ItemSchema {
    @Schema(description = "The material of the item", requiredMode = Schema.RequiredMode.REQUIRED)
    public String material;

    @Schema(description = "The slot of the item", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    public int slot;

    @Schema(description = "The display name of the item")
    public String name;

    @Schema(description = "The lore of the item")
    public List<String> lore;

    @Schema(description = "The amount of the item", example = "1")
    public int amount;

    @Schema(description = "The data value of the item", example = "0")
    public short data;

    @Schema(description = "The custom model data of the item")
    public Integer modelData;

    @Schema(description = "The enchantments of the item")
    public Map<String, Integer> enchants;

    @Schema(description = "The item flags")
    public List<String> flags;

    @Schema(description = "Whether the item should glow", example = "false")
    public boolean glow;

    @Schema(description = "The button type")
    public String buttonType;

    @ArraySchema(schema = @Schema(oneOf = {
            ActionBarSchema.class, ActionBackSchema.class, ActionBookSchema.class, ActionBroadcastSchema.class, ActionBroadcastSoundSchema.class, ActionChatSchema.class,
            ActionCloseSchema.class, ActionConnectSchema.class, ActionConsoleCommandSchema.class, ActionCurrencyDepositSchema.class, ActionCurrencyWithdrawSchema.class,
            ActionDataSchema.class, ActionInventorySchema.class, ActionLuckPermissionSetSchema.class, ActionMessageSchema.class, ActionPlayerCommandSchema.class,
            ActionRefreshSchema.class, ActionShopKeeperSchema.class, ActionSoundSchema.class, ActionTitleSchema.class,
    }))
    public List<Object> actions;

    @Schema(description = "The view requirements for the item")
    public RequirementSchema viewRequirement;

    @Schema(description = "The click requirements for the item")
    public RequirementSchema clickRequirement;
}
