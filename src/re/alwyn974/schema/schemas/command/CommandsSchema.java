package re.alwyn974.schema.schemas.command;

import org.bukkit.plugin.Plugin;
import re.alwyn974.schema.annotations.Schema;

import java.io.File;
import java.util.Map;

/**
 * JSON Schema for the command file
 * @see fr.maxlego08.menu.ZCommandManager#loadCommand(Plugin, File)
 */
public class CommandsSchema {
    @Schema(description = "A list of commands")
    public Map<String, CommandSchema> commands;
}
