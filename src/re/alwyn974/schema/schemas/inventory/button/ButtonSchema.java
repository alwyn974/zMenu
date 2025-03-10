package re.alwyn974.schema.schemas.inventory.button;

import re.alwyn974.schema.annotations.ArraySchema;
import re.alwyn974.schema.annotations.Schema;
import re.alwyn974.schema.schemas.inventory.PlaceholderSchema;
import re.alwyn974.schema.schemas.inventory.SoundSchema;
import re.alwyn974.schema.schemas.inventory.item.ItemSchema;

import java.util.List;

/**
 * JSON Schema for the button
 *
 * @see fr.maxlego08.menu.loader.ZButtonLoader
 */
public class ButtonSchema {
    @Schema(description = "The type of the button", allowedValues = {"NONE", "INVENTORY", "BACK", "HOME", "NEXT", "PREVIOUS", "MAINMENU", "JUMP"})
    public String type = "NONE";

    @Schema(description = "The slot where your item is. It should be a number between 0 and inventory (limit - 1). You can specify the page using <page>-<slot> syntax.",
            pattern = "^[0-9]*$|^[0-9]*-[0-9]*$", examples = {"0", "0-8", "2-5"}, types = {"string", "integer"}
    )
    public String slot = "0";

    @ArraySchema(schema = @Schema(description = "The slots, you can either write the slot number or use <start>-<end> syntax", examples = {"1", "1-6"}), minItems = 1)
    public List<String> slots;

    @Schema(description = "The item to display in the button")
    public int page = 1;

    @Schema(description = "Allows you to specify if the button should be displayed on all pages of the inventory.", names = {"is-permanent"})
    public boolean isPermanent = false;

    @Schema(description = "Allows you to enable or disable caching on the item. By default, caching will always be used if the button item does not contain a placeholder.", names = {"use-cache"})
    public boolean useCache;

    @Schema(description = "The item to display in the button", required = true)
    public ItemSchema item;

    @Schema(description = "The sound to play when the button is clicked")
    public SoundSchema sound;

    @Schema(description = "Allows you to send a list of messages to the player upon clicking. You can use the MiniMessage format to send messages with click or hover actions.")
    public List<String> messages;

    public ButtonPatternSchema pattern;

    @Schema(description = "Send a link to the player. Only use this if you can't use MiniMessage! https://docs.zmenu.dev/configurations/buttons#openlink", dependentRequired = "messages")
    public String link;
    @Schema(description = "The message shown instead of the %link% placeholder", dependentRequired = {"replace", "hover", "message", "link"})
    public String message;
    @Schema(description = "The placeholder to replace inside the messages", dependentRequired = {"replace", "hover", "message", "link"})
    public String replace;
    @Schema(description = "The message to display when the player hovers over the link", dependentRequired = {"replace", "hover", "message", "link"})
    public List<String> hover;

    @Schema(description = "Close the inventory when clicked", names = {"close-inventory"})
    public boolean closeInventory = false;

    @Schema(description = "Update the button when clicked", names = {"refresh-on-click"})
    public boolean refreshOnClick = false;

    @Schema(description = "A player head, you can use %player% placeholder or use a name. It's not needed when you use player-head inside item.", names = {"player-head"})
    public String playerHead;

    @Schema(description = "The permission needed to click the button. You can use a ! before the permission to check if the player doesn't have the permission. It can also be a list of permission", oneOf = {String.class, List.class})
    public Object permission;

    @Schema(description = "One of the permission needed to click the button. You can use a ! before the permission to check if the player doesn't have the permission.",
            names = {"or-permission", "orPermissions", "or-permissions"})
    public List<String> orPermission;

    @Schema(description = "The button to display if the player doesn't meet the requirement", name = "else")
    public ButtonSchema elseButton;

    @Schema(description = "Allows you to define a permission using a placeholder.", implementation = PlaceholderSchema.class)
    public Object placeholder;

    @Schema(additionalProperties = Object.class, description = "The pattern to use. Every placeholder used in pattern need to be added below as key")
    public static class ButtonPatternSchema {
        @Schema(description = "The pattern file name", required = true, names = {"file-name"})
        public String fileName;
        @Schema(description = "The plugin name", names = {"plugin-name"})
        public String pluginName;
    }
}
