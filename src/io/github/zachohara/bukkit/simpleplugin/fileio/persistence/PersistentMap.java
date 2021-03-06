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

package io.github.zachohara.bukkit.simpleplugin.fileio.persistence;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.github.zachohara.bukkit.simpleplugin.plugin.SimplePlugin;

/**
 * A {@code PersistentMap} is responsible for loading and storing any given {@code HashMap}
 * object as an external file, so that it remains persistent through server restarts.
 *
 * @param <K> the key type of the map
 * @param <V> the value type of the map
 * @see java.util.Map
 * @author Zach Ohara
 */
public class PersistentMap<K extends Serializable, V extends Serializable> extends PersistentObject implements Map<K, V> {

	/**
	 * Constructs a new {@code PersistentMap} with the given plugin as an owner, the data
	 * to store, and the filename to store that data to.
	 *
	 * @param <T> any type that is a {@code Map<K, V>} and is {@code Serializable}.
	 * @param owner the plugin that created this object.
	 * @param data the serializable {@code Map} that should be stored in an external file.
	 * @param filename the filename to store the object as.
	 */
	public <T extends Map<K, V> & Serializable> PersistentMap(SimplePlugin owner, String filename, T data) {
		super(owner, filename, data);
	}

	/**
	 * Constructs a new {@code PersistentMap} with the given plugin as an owner, and the
	 * filename to store that data to. This constructor will create a new {@code HashMap}
	 * object that will contain the data.
	 *
	 * @param owner the plugin that created this object.
	 * @param filename the filename to store the object as.
	 */
	public PersistentMap(SimplePlugin owner, String filename) {
		this(owner, filename, new HashMap<K, V>());
	}

	/**
	 * Gets the {@code Map} that is stored by this {@code PersistentMap}.
	 *
	 * @return a reference to the underlying {@code Map}.
	 */
	@SuppressWarnings("unchecked")
	private Map<K, V> mapdata() {
		if (this.getPayload() instanceof Map<?, ?>) {
			return (Map<K, V>) this.getPayload();
		} else {
			return null;
		}
	}

	// @formatter:off

	/*
	 * +------------------------------------------------------------------------------------+
	 * | All methods below this point are delegate methods extending this.mapdata() to this |
	 * +------------------------------------------------------------------------------------+
	 */

	// @formatter:on

	@Override
	public void clear() {
		this.mapdata().clear();
	}

	@Override
	public boolean containsKey(Object arg0) {
		return this.mapdata().containsKey(arg0);
	}

	@Override
	public boolean containsValue(Object arg0) {
		return this.mapdata().containsValue(arg0);
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return this.mapdata().entrySet();
	}

	@Override
	public V get(Object arg0) {
		return this.mapdata().get(arg0);
	}

	@Override
	public boolean isEmpty() {
		return this.mapdata().isEmpty();
	}

	@Override
	public Set<K> keySet() {
		return this.mapdata().keySet();
	}

	@Override
	public V put(K arg0, V arg1) {
		return this.mapdata().put(arg0, arg1);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> arg0) {
		this.mapdata().putAll(arg0);
	}

	@Override
	public V remove(Object arg0) {
		return this.mapdata().remove(arg0);
	}

	@Override
	public int size() {
		return this.mapdata().size();
	}

	@Override
	public Collection<V> values() {
		return this.mapdata().values();
	}

}
