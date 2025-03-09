package re.alwyn974.schema.schemas.actions;

import re.alwyn974.schema.annotations.Schema;

/**
 * JSON Schema for the action of withdrawing currency.
 * @see fr.maxlego08.menu.loader.actions.CurrencyWithdrawLoader
 */
public class ActionCurrencyWithdrawSchema extends ActionCurrencyDepositSchema {
    @Schema(description = "The type of the action", required = true, allowedValues = {"withdraw", "money remove"})
    public String type;
}