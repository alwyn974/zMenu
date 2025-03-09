package re.alwyn974.schema.schemas.actions;

import re.alwyn974.schema.annotations.Schema;

import java.util.List;
import java.util.Map;

/**
 * JSON Schema for the action book
 * @see fr.maxlego08.menu.loader.actions.BookLoader
 */
public class ActionBookSchema extends ActionBaseSchema {
    @Schema(description = "The type of the action", required = true, allowedValues = {"book"})
    public String type;

    @Schema(description = "The title of the book", required = true)
    public String title;

    @Schema(description = "The author of the book")
    public String author;

    @Schema(description = "The pages of the book. You can use <newline> to make a newline", required = true)
    public Map<?, List<String>> pages;
}