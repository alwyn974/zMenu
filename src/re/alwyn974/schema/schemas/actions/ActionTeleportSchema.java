package re.alwyn974.schema.schemas.actions;

import re.alwyn974.schema.annotations.Schema;

/**
 * JSON Schema for the action teleport
 * @see fr.maxlego08.menu.loader.actions.TeleportLoader
 */
public class ActionTeleportSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", required = true, allowedValues = {"teleport", "tp"})
    public String type;

    @Schema(description = "The world to teleport to")
    public String world = "world";

    @Schema(description = "The x coordinate to teleport to")
    public double x = 0;

    @Schema(description = "The y coordinate to teleport to")
    public double y = 0;

    @Schema(description = "The z coordinate to teleport to")
    public double z = 0;

    @Schema(description = "The yaw angle for the teleport")
    public float yaw = 0;

    @Schema(description = "The pitch angle for the teleport")
    public float pitch = 0;
}
