package re.alwyn974.schema.schemas.inventory;

import fr.maxlego08.menu.api.enums.PlaceholderAction;
import io.swagger.v3.oas.annotations.media.Schema;

public class PlaceholderSchema {
    @Schema(description = "Placeholder to use", requiredMode = Schema.RequiredMode.REQUIRED)
    public String placeHolder;
    @Schema(description = "Placeholder to use", requiredMode = Schema.RequiredMode.REQUIRED)
    public String placeholder;

    @Schema(description = "Value to check against", requiredMode = Schema.RequiredMode.REQUIRED)
    public String value;

    @Schema(description = "Action to do on the placeholder", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {
            "b=", "s=", "s==", "sc", "==", ">", ">=", "<", "<="
    })
    public PlaceholderAction action;

    @Schema(description = "Player on which will be used the placeholder")
    public String target;
}