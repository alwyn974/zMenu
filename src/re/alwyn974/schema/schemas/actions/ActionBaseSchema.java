package re.alwyn974.schema.schemas.actions;

import re.alwyn974.schema.annotations.Schema;

public class ActionBaseSchema {
    @Schema(description = "The delay before executing the action (in ticks)", examples = "0")
    public int delay = 0;
}
