package re.alwyn974.schema.schemas.actions;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * JSON Schema for the action title
 * @see fr.maxlego08.menu.loader.actions.TitleLoader
 */
public class ActionTitleSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"title"})
    public String type;

    @Schema(description = "The title to display", requiredMode = Schema.RequiredMode.REQUIRED)
    public String title;

    @Schema(description = "The subtitle to display", requiredMode = Schema.RequiredMode.REQUIRED)
    public String subtitle;

    @Schema(description = "The start (fade-in) time in milliseconds with minimessage enabled, in ticks without", example = "10", requiredMode = Schema.RequiredMode.REQUIRED, defaultValue = "0")
    public int start;

    @Schema(description = "The duration time in milliseconds with minimessage enabled, in ticks without", example = "70", requiredMode = Schema.RequiredMode.REQUIRED, defaultValue = "0")
    public int duration;

    @Schema(description = "The end (fade-out) time in milliseconds with minimessage enabled, in ticks without", example = "20", requiredMode = Schema.RequiredMode.REQUIRED, defaultValue = "0")
    public int end;
}