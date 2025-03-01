package re.alwyn974.schema.schemas.actions;

import com.cryptomorin.xseries.XSound;
import io.swagger.v3.oas.annotations.media.Schema;
import org.bukkit.Sound;

/**
 * JSON Schema for the action sound
 * @see fr.maxlego08.menu.loader.actions.SoundLoader
 */
public class ActionSoundSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"sound"})
    public String type;

    @Schema(description = "The sound to play", requiredMode = Schema.RequiredMode.REQUIRED)
    public Sound sound;

    @Schema(description = "The volume of the sound", defaultValue = "1.0", example = "0.5")
    public float volume = 1.0f;

    @Schema(description = "The pitch of the sound", defaultValue = "1.0", example = "0.5")
    public float pitch = 1.0f;

    @Schema(description = "The sound category", name = "sound-category", defaultValue = "MASTER", example = "MUSIC")
    public XSound.Category category;
}