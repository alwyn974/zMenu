package re.alwyn974.schema.schemas.actions;

import io.swagger.v3.oas.annotations.media.Schema;

public class ActionBaseSchema {
    @Schema(description = "The delay before executing the action (in ticks)", example = "0")
    public int delay = 0;
}
