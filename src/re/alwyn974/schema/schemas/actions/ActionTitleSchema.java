package re.alwyn974.schema.schemas.actions;

import re.alwyn974.schema.annotations.Schema;

/**
 * JSON Schema for the action title
 * @see fr.maxlego08.menu.loader.actions.TitleLoader
 */
public class ActionTitleSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", required = true, allowedValues = {"title"})
    public String type;

    @Schema(description = "The title to display", required = true)
    public String title;

    @Schema(description = "The subtitle to display", required = true)
    public String subtitle;

    @Schema(description = "The start (fade-in) time in milliseconds with minimessage enabled, in ticks without", examples = "10", required = true)
    public int start = 0;

    @Schema(description = "The duration time in milliseconds with minimessage enabled, in ticks without", examples = "70", required = true)
    public int duration = 0;

    @Schema(description = "The end (fade-out) time in milliseconds with minimessage enabled, in ticks without", examples = "20", required = true)
    public int end = 0;
}