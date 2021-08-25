/*
 * Copyright (c) 2020 The Cursed Legacy Team.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.halotroop.fabric.api.attacheddata.v1;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.halotroop.fabric.api.registry.Identifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.world.WorldProperties;

/**
 * Manager for data which can be attached to various vanilla objects, such as items and worlds.
 */
public final class DataManager<T> {
	private final Map<Identifier, Function<T, ? extends AttachedData>> attachedDataFactories = new HashMap<>();

	/**
	 * Adds the specified attached data to the {@link DataManager} instance. This data can later be accessed on an instance of the object via {@link #getAttachedData}.
	 * @return a key to use to retrieve the attached data from an object.
	 */
	public <E extends AttachedData> DataManager.DataKey<E> addAttachedData(Identifier id, Function<T, E> dataProvider) {
		this.attachedDataFactories.put(id, dataProvider);
		return new DataManager.DataKey<>(id);
	}

	/**
	 * Retrieves the specified attached data from the object.
	 */
	public <E extends AttachedData> E getAttachedData(T object, DataManager.DataKey<E> key) throws ClassCastException {
		return key.apply(((DataStorage) object).getAttachedData(key.id, () -> this.attachedDataFactories.get(key.id).apply(object)));
	}

	/**
	 * Used by the implementation.
	 * @return a {@linkplain Set set} of all {@linkplain Identifier ids} of {@link AttachedData} instances registered to this manager.
	 */
	public Set<Identifier> getDataKeys() {
		return this.attachedDataFactories.keySet();
	}

	/**
	 * Used by the implementation.
	 * @return an attached data instance of the given type constructed by the given tag.
	 */
	public AttachedData deserialize(T object, Identifier id, CompoundTag data) {
		AttachedData result = this.attachedDataFactories.get(id).apply(object);
		result.fromTag(data);
		return result;
	}

	/**
	 * Used by the implementation.
	 * @param from the object to use the data of.
	 * @param to the object to receive the data.
	 */
	public void copyData(T from, T to) {
		DataStorage to_ = (DataStorage) to;

		((DataStorage) from).getAllAttachedData().forEach(entry -> {
			Identifier dataId = entry.getKey();
			AttachedData data = entry.getValue();
			to_.putAttachedData(dataId, data.copy());
		});
	}

	/**
	 * Used by the implementation. 
	 * @param object the object which is storing the data. The class thereof must implement {@linkplain DataStorage} or have a mixin implementing it on the class. This is assumed to be the latter as this api exists specifically for game classes that do not have useful data attachment interfaces.
	 * @param moddedTag the modded nbt tag which stores existing data.
	 * @since 1.0.0
	 * @throws ClassCastException when you didn't make sure the object is implementing DataStorage through a mixin.
	 */
	public void loadData(T object, CompoundTag moddedTag) throws ClassCastException {
		this.getDataKeys().forEach(id -> { // Iterate over each key
			if (moddedTag.containsKey(id.toString())) { // If it exists in the stored data
				((DataStorage) object).putAttachedData(id, this.deserialize(object, id, moddedTag.getCompoundTag(id.toString()))); // Attach the data from the stored data
			}
		});
	}

	/**
	 * Stores data to an item instance.
	 */
	public static final DataManager<ItemStack> ITEM_STACK = new DataManager<>();
	
	/**
	 * Stores data to the Level.dat file.
	 * @since 1.0.0
	 */
	public static final DataManager<WorldProperties> WORLD_PROPERTIES = new DataManager<>();

	/**
	 * Identifier class that is generically bound to a subclass of attached data, for ease of access.
	 */
	public static final class DataKey<T extends AttachedData> {
		private DataKey(Identifier id) throws NullPointerException {
			this.id = id;
		}

		private final Identifier id;

		@SuppressWarnings("unchecked")
		private T apply(AttachedData data) throws ClassCastException {
			return (T) data;
		}
	}
}
