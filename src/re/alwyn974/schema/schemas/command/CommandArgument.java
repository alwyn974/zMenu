package re.alwyn974.schema.schemas.command;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import re.alwyn974.schema.schemas.actions.ActionBarSchema;

import java.util.List;

public class CommandArgument {
    @Schema(description = "The argument name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "The inventory associated with this argument")
    private String inventory;

    @Schema(description = "Whether this argument is required", defaultValue = "true")
    private boolean isRequired;

    @Schema(description = "Whether to perform the main action", defaultValue = "true")
    private boolean performMainAction;

    @ArraySchema(schema = @Schema(oneOf = {ActionBarSchema.class}))
    private List<Object> actions;

    @Schema(description = "The list of auto-completions", name = "auto-completion")
    private List<String> autoCompletion;
}