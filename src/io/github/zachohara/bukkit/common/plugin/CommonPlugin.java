/* Copyright (C) 2015 Zach Ohara
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

package io.github.zachohara.bukkit.common.plugin;

import io.github.zachohara.bukkit.common.command.CommandExecutables;
import io.github.zachohara.bukkit.common.command.CommandInstance;
import io.github.zachohara.bukkit.common.command.CommandRules;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The supertype for all plugins that use the common system.
 *
 * @author Zach Ohara
 */
public abstract class CommonPlugin extends JavaPlugin {

    /**
     * {@inheritDoc}
     */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		CommandInstance instance = new CommandInstance(sender, command, args,
				this.getCommandRuleSet(), this.getCommandExecutableSet());
		if (instance.verifyCommand()) {
			instance.executeCommand();
		}
		return true;
	}
	
	/**
	 * Gets the enumeration of {@code CommandRules} that represents the set of commands
	 * that are specific to a plugin.
	 * @return the enumeration of {@code CommandRules} that represents the set of commands
	 * that are specific to a plugin.
	 */
	public abstract Class<? extends CommandRules> getCommandRuleSet();
	
	/**
	 * Gets the enumeration of {@code CommandExecutables} that represents the set of commands
	 * that are specific to a plugin.
	 * @return the enumeration of {@code CommandExecutables} that represents the set of
	 * commands that are specific to a plugin.
	 */
	public abstract Class<? extends CommandExecutables> getCommandExecutableSet();

}
