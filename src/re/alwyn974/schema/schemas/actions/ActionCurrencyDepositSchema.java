package re.alwyn974.schema.schemas.actions;

import fr.traqueur.currencies.Currencies;
import org.checkerframework.checker.units.qual.C;
import re.alwyn974.schema.annotations.Schema;

/**
 * JSON Schema for the action of depositing currency.
 * @see fr.maxlego08.menu.loader.actions.CurrencyDepositLoader
 */
public class ActionCurrencyDepositSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", required = true, allowedValues = {"deposit", "money add"})
    public String type;

    @Schema(description = "The amount of currency to deposit", required = true)
    public double amount;

    @Schema(description = "The currency to use. The currency name will transformed to upper case", required = true)
    public Currencies currency = Currencies.VAULT;

    @Schema(description = "The economy name to use. Only for zEssentials, CoinsEngine and EcoBits")
    public String economy;
}
