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

package io.github.minecraftcursedlegacy.api.registry;

import java.util.function.IntFunction;

import io.github.minecraftcursedlegacy.impl.registry.RegistryImpl;
import net.minecraft.item.ItemType;
import net.minecraft.item.PlaceableTileItem;
import net.minecraft.item.TileItem;
import net.minecraft.tile.Tile;

/**
 * Utilities for adding and registering tile items.
 */
public class TileItems {
	/**
	 * Adds a registered tile item for the tile.
	 * @param id the id of the tile item.
	 * @param value the tile this tile item is for.
	 * @return the tile item created.
	 * @deprecated Prefer using {@link #registerTileItem(Id, Tile)} (which creates a 
	 * 				{@link PlaceableTileItem} for the given tile rather than a {@link TileItem}) so 
	 * 				that it will respect the item meta when deciding on the tile to place in the world.
	 */
	@Deprecated
	public static TileItem addRegisteredTileItem(Id id, Tile value) {
		return RegistryImpl.addTileItem(id, value, itemID -> new TileItem(itemID, value));
	}

	/**
	 * Register an {@link ItemType} for the given {@link Tile}.
	 *
	 * @param id The identifier of the item to be registered
	 * @param tile The tile the item is for
	 *
	 * @return An item for the given tile
	 *
	 * @since 0.4.1
	 */
	public static ItemType registerTileItem(Id id, Tile tile) {
		return registerTileItem(id, tile, PlaceableTileItem::new);
	}

	/**
	 * Register an {@link ItemType} for the given {@link Tile} created using the given factory.
	 *
	 * @param id The identifier of the item to be registered
	 * @param tile The tile the item is for
	 * @param itemFactory A factory for creating the item, given the item ID to use
	 *
	 * @return An item for the given tile
	 *
	 * @since 0.4.1
	 */
	public static <I extends PlaceableTileItem> I registerTileItem(Id id, Tile tile, IntFunction<I> itemFactory) {
		return RegistryImpl.addTileItem(id, tile, itemFactory);
	}
}
