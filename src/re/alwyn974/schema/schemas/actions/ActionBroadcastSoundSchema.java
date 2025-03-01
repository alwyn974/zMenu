package re.alwyn974.schema.schemas.actions;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * JSON Schema for the action broadcast sound
 * @see fr.maxlego08.menu.loader.actions.BroadcastSoundLoader
 */
public class ActionBroadcastSoundSchema extends ActionSoundSchema {
    @Schema(description = "The type of the action", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"broadcast_sound"})
    public String type;

    @Schema(description = "Players will hear the sound in this range", defaultValue = "30", example = "15")
    public int range = 30;
}
