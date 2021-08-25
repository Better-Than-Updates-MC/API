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

package com.halotroop.fabric.api.registry;

import java.util.function.IntFunction;

import com.halotroop.fabric.impl.registry.RegistryImpl;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

/**
 * Utilities for adding and registering block items.
 */
public class BlockItems {
	/**
	 * Register an {@link Item} for the given {@link Block}.
	 *
	 * @param id The identifier of the item to be registered
	 * @param block The block the item is for
	 *
	 * @return An item for the given block
	 *
	 * @since 0.4.1
	 */
	public static Item registerBlockItem(Identifier id, Block block) {
		return registerBlockItem(id, block, BlockItem::new);
	}

	/**
	 * Register an {@link Item} for the given {@link Block} created using the given factory.
	 *
	 * @param id The identifier of the item to be registered
	 * @param block The block the item is for
	 * @param itemFactory A factory for creating the item, given the item ID to use
	 *
	 * @return An item for the given block
	 *
	 * @since 0.4.1
	 */
	public static <I extends BlockItem> I registerBlockItem(Identifier id, Block block, IntFunction<I> itemFactory) {
		return RegistryImpl.addBlockItem(id, block, itemFactory);
	}
}
