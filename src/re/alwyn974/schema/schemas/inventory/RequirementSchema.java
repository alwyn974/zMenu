package re.alwyn974.schema.schemas.inventory;

import re.alwyn974.schema.annotations.Schema;

public class RequirementSchema {
    @Schema(description = "The type of requirement")
    public String type;

    @Schema(description = "The permission required")
    public String permission;

    @Schema(description = "The placeholder to check")
    public String placeholder;

    @Schema(description = "The value to compare against")
    public String value;

    @Schema(description = "The comparison action")
    public String action;
}
