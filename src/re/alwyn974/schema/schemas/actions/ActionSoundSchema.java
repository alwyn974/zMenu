package re.alwyn974.schema.schemas.actions;

import com.cryptomorin.xseries.XSound;
import re.alwyn974.schema.annotations.Schema;
import org.bukkit.Sound;

/**
 * JSON Schema for the action sound
 * @see fr.maxlego08.menu.loader.actions.SoundLoader
 */
public class ActionSoundSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", required = true, allowedValues = {"sound"})
    public String type;

    @Schema(description = "The sound to play", required = true)
    public Sound sound;

    @Schema(description = "The volume of the sound", examples = "0.5")
    public float volume = 1.0f;

    @Schema(description = "The pitch of the sound", examples= "0.5")
    public float pitch = 1.0f;

    @Schema(description = "The sound category", name = "sound-category", examples = "MUSIC")
    public XSound.Category category = XSound.Category.MASTER;
}