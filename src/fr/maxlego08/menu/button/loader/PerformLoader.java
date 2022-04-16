package fr.maxlego08.menu.button.loader;

import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import fr.maxlego08.menu.api.button.Button;
import fr.maxlego08.menu.api.button.buttons.PerformButton;
import fr.maxlego08.menu.api.loader.ButtonLoader;
import fr.maxlego08.menu.button.buttons.ZPerformButton;

public class PerformLoader implements ButtonLoader {

	private final Plugin plugin;

	/**
	 * @param plugin
	 */
	public PerformLoader(Plugin plugin) {
		super();
		this.plugin = plugin;
	}

	@Override
	public Class<? extends Button> getButton() {
		return PerformButton.class;
	}

	@Override
	public String getName() {
		return "perform_command";
	}

	@Override
	public Plugin getPlugin() {
		return this.plugin;
	}

	@Override
	public Button load(YamlConfiguration configuration, String path) {
		List<String> commands = configuration.getStringList(path + "commands");
		List<String> consoleCommands = configuration.getStringList(path + "consoleCommands");
		List<String> consoleRightCommands = configuration.getStringList(path + "consoleRightCommands");
		List<String> consoleLeftCommands = configuration.getStringList(path + "consoleLeftCommands");
		List<String> consolePermissionCommands = configuration.getStringList(path + "consolePermissionCommands");
		String consolePermission = configuration.getString(path + "consolePermission");
		return new ZPerformButton(commands, consoleCommands, consoleRightCommands, consoleLeftCommands,
				consolePermissionCommands, consolePermission);
	}

}
