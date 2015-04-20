package net.moltendorf.Bukkit.LieDispenser;

import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by moltendorf on 15/04/03.
 *
 * @author moltendorf
 */
public class Plugin extends JavaPlugin {

	// Variable data.
	protected Configuration configuration = null;

	@Override
	public synchronized void onDisable() {
		// Do nothing.
	}

	@Override
	public synchronized void onEnable() {

		// Construct new configuration.
		configuration = new Configuration();

		// Are we enabled?
		if (!configuration.global.enabled) {
			return;
		}

		// Get server.
		final Server server = getServer();

		// Get plugin manager.
		final PluginManager manager = server.getPluginManager();

		// Register our event listeners.
		manager.registerEvents(new Listeners(this), this);
	}
}
