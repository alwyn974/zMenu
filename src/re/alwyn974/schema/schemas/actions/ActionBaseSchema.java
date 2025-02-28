package re.alwyn974.schema.schemas.actions;

import io.swagger.v3.oas.annotations.media.Schema;

public class ActionBaseSchema {
    @Schema(description = "The delay before the action is executed", defaultValue = "0")
    private int delay;
}
