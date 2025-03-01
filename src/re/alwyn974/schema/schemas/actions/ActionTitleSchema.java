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

    @Schema(description = "The fade-in time in milliseconds", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    public int fadeIn = 10;

    @Schema(description = "The stay time in milliseconds", example = "70", requiredMode = Schema.RequiredMode.REQUIRED)
    public int stay = 70;

    @Schema(description = "The fade-out time in milliseconds", example = "20", requiredMode = Schema.RequiredMode.REQUIRED)
    public int fadeOut = 20;
}