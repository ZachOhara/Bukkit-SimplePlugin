/* Copyright (C) 2017 Zach Ohara
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.zachohara.bukkit.simpleplugin.plugin;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.zachohara.bukkit.simpleplugin.command.CommandInstance;
import io.github.zachohara.bukkit.simpleplugin.command.CommandSet;
import io.github.zachohara.bukkit.simpleplugin.fileio.PluginDataFile;

/**
 * The {@code SimplePlugin} class acts as a supertype for the main class of any plugin that
 * uses the SimplePlugin library.
 *
 * @author Zach Ohara
 */
public abstract class SimplePlugin extends JavaPlugin {

	/**
	 * The list of all plugins, and their associated {@code SimplePlugin} subclass objects,
	 * that are currently running on the server.
	 */
	private static Map<Class<? extends SimplePlugin>, SimplePlugin> pluginList;

	static {
		SimplePlugin.pluginList = new HashMap<Class<? extends SimplePlugin>, SimplePlugin>();
	}

	/**
	 * The list of all {@code PluginDataFile}s that have been registered to this plugin.
	 */
	private List<PluginDataFile> ownedFilesList;

	/**
	 * Starts the plugin and initializes functionality. This method is called anytime
	 * before the plugin is enabled on the server, including during server startup
	 * procedure.
	 */
	@Override
	public void onEnable() {
		super.onEnable();
		SimplePlugin.pluginList.put(this.getClass(), this);
		this.ownedFilesList = new LinkedList<PluginDataFile>();
	}

	/**
	 * Safely closes the plugin. This method is called anytime before the plugin is
	 * disabled on the server, including during the server shutdown procedure. This method
	 * will close and save all of the registered {@code PluginDataFile} objects registered
	 * to this plugin.
	 */
	@Override
	public void onDisable() {
		super.onDisable();
		SimplePlugin.pluginList.remove(this.getClass());
		for (PluginDataFile file : this.ownedFilesList) {
			file.closeFile(this.getLogger());
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		CommandInstance instance = new CommandInstance(sender, command, args, this.getCommandSet());
		if (instance.verifyCommand()) {
			instance.executeCommand();
		}
		return true;
	}

	/**
	 * Gets the enumeration of {@code CommandSet} that represents the set of commands that
	 * are specific to a plugin.
	 *
	 * @return the {@code CommandSet} enumeration for this plugin.
	 */
	public abstract Class<? extends CommandSet> getCommandSet();

	/**
	 * Register the given {@code PluginDataFile} with this plugin.
	 *
	 * @param data the {@code PluginDataFile} to register.
	 */
	public void registerPluginFile(PluginDataFile data) {
		this.ownedFilesList.add(data);
	}

	/**
	 * Gets the active instance of a given plugin.
	 *
	 * @param pluginMainClass the main class of the plugin, as specified in the
	 * {@code plugin.yml} file.
	 * @return the active instance of the given plugin.
	 */
	public static SimplePlugin getPluginInstance(Class<? extends SimplePlugin> pluginMainClass) {
		return SimplePlugin.pluginList.get(pluginMainClass);
	}
	
	/**
	 * Gets a list of all plugins currently running on SimplePlugin.
	 *
	 * @return a list of all active plugins.
	 */
	public static List<String> getActivePluginList() {
		List<String> pluginList = new LinkedList<String>();
		for (SimplePlugin plugin : SimplePlugin.pluginList.values()) {
			pluginList.add(plugin.getName());
		}
		return pluginList;
	}

	/**
	 * Prints a console message that explains this file's nature as a Bukkit plugin, not a
	 * java app.
	 *
	 * @param args the command line arguments passed to the program.
	 */
	public static void main(String[] args) {
		//@formatter:off
		System.out.println("This is not a normal java app! This is a plugin for a Bukkit server!\n"
				+ "To use it, you have to install Bukkit, then install this plugin.");
		//@formatter:on
	}

}
