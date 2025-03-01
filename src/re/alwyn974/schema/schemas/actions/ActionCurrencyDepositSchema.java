package re.alwyn974.schema.schemas.actions;

import fr.traqueur.currencies.Currencies;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * JSON Schema for the action of depositing currency.
 * @see fr.maxlego08.menu.loader.actions.CurrencyDepositLoader
 */
public class ActionCurrencyDepositSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"deposit", "money add"})
    public String type;

    @Schema(description = "The amount of currency to deposit", requiredMode = Schema.RequiredMode.REQUIRED)
    public double amount;

    @Schema(description = "The currency to use. The currency name will transformed to upper case", requiredMode = Schema.RequiredMode.REQUIRED, defaultValue = "VAULT")
    public Currencies currency;

    @Schema(description = "The economy name to use. Only for zEssentials, CoinsEngine and EcoBits")
    public String economy;
}
