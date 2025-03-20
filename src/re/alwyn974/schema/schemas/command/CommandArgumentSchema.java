package re.alwyn974.schema.schemas.command;

import re.alwyn974.schema.annotations.ArraySchema;
import re.alwyn974.schema.annotations.Schema;
import re.alwyn974.schema.schemas.actions.*;

import java.util.List;

/**
 * JSON Schema for the command argument
 * @see fr.maxlego08.menu.loader.CommandLoader
 */
public class CommandArgumentSchema {
    @Schema(description = "The argument name", required = true)
    public String name;

    @Schema(description = "The inventory associated with this argument")
    public String inventory;

    @Schema(description = "Whether this argument is required")
    public boolean isRequired = true;

    @Schema(description = "Whether to perform the main action")
    public boolean performMainAction = true;

    @ArraySchema(schema = @Schema(oneOf = {
            ActionBarSchema.class, ActionBackSchema.class, ActionBookSchema.class, ActionBroadcastSchema.class, ActionBroadcastSoundSchema.class, ActionChatSchema.class,
            ActionCloseSchema.class, ActionConnectSchema.class, ActionConsoleCommandSchema.class, ActionCurrencyDepositSchema.class, ActionCurrencyWithdrawSchema.class,
            ActionDataSchema.class, ActionInventorySchema.class, ActionLuckPermissionSetSchema.class, ActionMessageSchema.class, ActionPlayerCommandSchema.class,
            ActionRefreshSchema.class, ActionShopKeeperSchema.class, ActionSoundSchema.class, ActionTitleSchema.class,
    }))
    public List<Object> actions;

    @Schema(description = "The list of auto-completions", name = "auto-completion")
    public List<String> autoCompletion;

    @Schema(description = "The default value of the argument")
    public String defaultValue;
}