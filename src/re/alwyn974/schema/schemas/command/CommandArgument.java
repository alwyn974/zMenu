package re.alwyn974.schema.schemas.command;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import re.alwyn974.schema.schemas.actions.*;

import java.util.List;

public class CommandArgument {
    @Schema(description = "The argument name", requiredMode = Schema.RequiredMode.REQUIRED)
    public String name;

    @Schema(description = "The inventory associated with this argument")
    public String inventory;

    @Schema(description = "Whether this argument is required", defaultValue = "true")
    public boolean isRequired;

    @Schema(description = "Whether to perform the main action", defaultValue = "true")
    public boolean performMainAction;

    @ArraySchema(schema = @Schema(oneOf = {
            ActionBarSchema.class, ActionBackSchema.class, ActionBookSchema.class, ActionBroadcastSchema.class, ActionBroadcastSoundSchema.class, ActionChatSchema.class,
            ActionCloseSchema.class, ActionConnectSchema.class, ActionConsoleCommandSchema.class, ActionCurrencyDepositSchema.class, ActionCurrencyWithdrawSchema.class,
            ActionDataSchema.class, ActionInventorySchema.class, ActionLuckPermissionSetSchema.class, ActionMessageSchema.class, ActionPlayerCommandSchema.class,
            ActionRefreshSchema.class, ActionShopKeeperSchema.class, ActionSoundSchema.class, ActionTitleSchema.class,
    }))
    public List<Object> actions;

    @Schema(description = "The list of auto-completions", name = "auto-completion")
    public List<String> autoCompletion;
}