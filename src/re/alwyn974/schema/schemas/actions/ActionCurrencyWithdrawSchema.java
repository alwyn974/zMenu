package re.alwyn974.schema.schemas.actions;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * JSON Schema for the action of withdrawing currency.
 * @see fr.maxlego08.menu.loader.actions.CurrencyWithdrawLoader
 */
public class ActionCurrencyWithdrawSchema extends ActionCurrencyDepositSchema {
    @Schema(description = "The type of the action", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"withdraw", "money remove"})
    public String type;
}