package re.alwyn974.schema.schemas.inventory;

import fr.maxlego08.menu.api.enums.PlaceholderAction;
import re.alwyn974.schema.annotations.Schema;

public class PlaceholderSchema {
    @Schema(description = "Placeholder to use", required = true)
    public String placeHolder;
    @Schema(description = "Placeholder to use", required = true)
    public String placeholder;

    @Schema(description = "Value to check against", required = true)
    public String value;

    @Schema(description = "Action to do on the placeholder", required = true, allowedValues = {
            "b=", "s=", "s==", "sc", "==", ">", ">=", "<", "<="
    })
    public PlaceholderAction action;

    @Schema(description = "Player on which will be used the placeholder")
    public String target;
}