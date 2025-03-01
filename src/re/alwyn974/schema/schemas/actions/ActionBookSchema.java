package re.alwyn974.schema.schemas.actions;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

/**
 * JSON Schema for the action book
 * @see fr.maxlego08.menu.loader.actions.BookLoader
 */
public class ActionBookSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"book"})
    public String type;

    @Schema(description = "The title of the book", requiredMode = Schema.RequiredMode.REQUIRED)
    public String title;

    @Schema(description = "The author of the book")
    public String author;

    @Schema(description = "The pages of the book. You can use <newline> to make a newline", requiredMode = Schema.RequiredMode.REQUIRED)
    public Map<?, List<String>> pages;
}