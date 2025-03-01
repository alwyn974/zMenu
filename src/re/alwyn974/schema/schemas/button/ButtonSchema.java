package re.alwyn974.schema.schemas.button;

import io.swagger.v3.oas.annotations.media.Schema;

public class ButtonSchema {
    @Schema(description = "The slot where your item is. It should be a number between 0 and inventory (limit - 1). You can specify the page using <page>-<slot> syntax.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^[0-9]*$|^[0-9]*-[0-9]*$", examples = {
            "0", "0-8", "2-5"
    })
    int slot;
}
