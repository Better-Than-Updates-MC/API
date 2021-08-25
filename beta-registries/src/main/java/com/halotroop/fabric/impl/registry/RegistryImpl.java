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

package com.halotroop.fabric.impl.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntFunction;

import com.halotroop.fabric.accessor.registry.EntityRegistryAccessor;
import com.halotroop.fabric.api.networking.PluginChannel;
import com.halotroop.fabric.api.networking.PluginChannelRegistry;
import com.halotroop.fabric.api.registry.Registry;
import com.halotroop.fabric.api.registry.RegistryEntryAddedCallback;
import com.halotroop.fabric.api.registry.RegistryRemappedCallback;
import com.halotroop.fabric.impl.registry.sync.RegistrySyncChannelS2C;
import com.halotroop.fabric.api.registry.Identifier;
import io.github.minecraftcursedlegacy.impl.Hacks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraft.util.io.CompoundTag;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class RegistryImpl implements ModInitializer {
	@Override
	public void onInitialize() {
		Hacks.hack = Registry::lockAll;

		PluginChannelRegistry.registerPluginChannel(syncChannel = new RegistrySyncChannelS2C());
	}
	

	public static <I extends Item> I addBlockItem(Identifier id, Block value, IntFunction<I> constructor) {
		return ((ItemRegistry) ITEM).addBlockItem(id, value, constructor);
	}

	public static <T> Event<RegistryEntryAddedCallback<T>> createEvent(Class<T> clazz) {
		return EventFactory.createArrayBacked(RegistryEntryAddedCallback.class, listeners -> (object, id, rawId) -> {
			for (RegistryEntryAddedCallback<T> listener : listeners) {
				listener.onEntryAdded(object, id, rawId);
			}
		});
	}

	public static <T> Event<RegistryRemappedCallback<T>> createRemapEvent(Class<T> clazz) {
		return EventFactory.createArrayBacked(RegistryRemappedCallback.class, listeners -> (registry, diff) -> {
			for (RegistryRemappedCallback<T> listener : listeners) {
				listener.onRemap(registry, diff);
			}
		});
	}

	static int nextItemId() {
		while (Item.byId[currentItemId] != null) {
			++currentItemId;
		}

		return currentItemId;
	}

	static int nextBlockId() {
		while (Block.BY_ID[currentBlockId] != null) {
			++currentBlockId;
		}

		return currentBlockId;
	}

	public static final Registry<@NotNull Item> ITEM;
	public static final Registry<@NotNull Block> BLOCK;
	public static final Registry<@NotNull EntityType> ENTITY_TYPE;

	private static int currentItemId = Block.BY_ID.length;
	private static int currentBlockId = 1;

	static final Map<Block, Item> T_2_TI = new HashMap<>();

	// Sync Stuff
	public static PluginChannel syncChannel;
	public static CompoundTag registryData; // this is used server side only

	static {
		//noinspection ResultOfMethodCallIgnored
		Block.BED.hashCode(); // make sure blocks are initialised
		EntityRegistryAccessor.getIdToClassMap(); // make sure entities are initialised
		BLOCK = new BlockRegistry(new Identifier("api:block")); // BLOCKS BEFORE ITEMS SO ITEM REMAPPING HAPPENS LATER AND BLOCK IDS ARE PROPERLY ADDED TO THE VANILLA ID SET!
		ITEM = new ItemRegistry(new Identifier("api:item"));
		ENTITY_TYPE = new EntityTypeRegistry(new Identifier("api:entity_type"));
	}
}
