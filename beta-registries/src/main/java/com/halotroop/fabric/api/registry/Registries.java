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

import com.halotroop.fabric.impl.registry.EntityType;
import com.halotroop.fabric.impl.registry.RegistryImpl;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;

/**
 * Class containing the {@link Registry registries} that are part of the api.
 */
public final class Registries {
	/**
	 * Registry for Item.
	 */
	public static final Registry<@NotNull Item> ITEM = RegistryImpl.ITEM;

	/**
	 * Registry for Blocks.
	 */
	public static final Registry<@NotNull Block> BLOCK = RegistryImpl.BLOCK;

	/**
	 * Registry for Entity types.
	 */
	public static final Registry<@NotNull EntityType> ENTITY_TYPE = RegistryImpl.ENTITY_TYPE;
}
