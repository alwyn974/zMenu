package re.alwyn974.schema.schemas.actions;

import re.alwyn974.schema.annotations.Schema;

/**
 * JSON Schema for the action shopkeeper
 * @see fr.maxlego08.menu.loader.actions.ShopkeeperLoader
 */
public class ActionShopKeeperSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", required = true, allowedValues = {"shopkeeper"})
    public String type;

    @Schema(description = "The shopkeeper name", required = true)
    public String name;
}