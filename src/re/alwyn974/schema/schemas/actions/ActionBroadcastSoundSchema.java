package re.alwyn974.schema.schemas.actions;

import re.alwyn974.schema.annotations.Schema;

/**
 * JSON Schema for the action broadcast sound
 * @see fr.maxlego08.menu.loader.actions.BroadcastSoundLoader
 */
public class ActionBroadcastSoundSchema extends ActionSoundSchema {
    @Schema(description = "The type of the action", required = true, allowedValues = {"broadcast_sound"})
    public String type;

    @Schema(description = "Players will hear the sound in this range", examples = "15")
    public int range = 30;
}
