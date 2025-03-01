package re.alwyn974.schema.schemas.actions;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * JSON Schema for the action shopkeeper
 * @see fr.maxlego08.menu.loader.actions.ShopkeeperLoader
 */
public class ActionShopKeeperSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"shopkeeper"})
    public String type;

    @Schema(description = "The shopkeeper name", requiredMode = Schema.RequiredMode.REQUIRED)
    public String name;
}