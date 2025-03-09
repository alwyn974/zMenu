package re.alwyn974.schema.schemas.inventory;

import com.cryptomorin.xseries.XSound;
import re.alwyn974.schema.annotations.Schema;
import org.bukkit.Sound;

/**
 * JSON Schema for the sound
 * @see fr.maxlego08.menu.loader.ZButtonLoader sound loader
 */
public class SoundSchema {
    @Schema(description = "The sound to play", required = true)
    public Sound sound;

    @Schema(description = "The volume of the sound", examples = "0.5")
    public float volume = 1.0f;

    @Schema(description = "The pitch of the sound", examples = "0.5")
    public float pitch = 1.0f;

    @Schema(description = "The sound category", name = "sound-category", examples = "MUSIC")
    public XSound.Category category = XSound.Category.MASTER;
}
