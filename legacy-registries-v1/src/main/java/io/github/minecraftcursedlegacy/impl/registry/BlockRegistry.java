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

package io.github.minecraftcursedlegacy.impl.registry;

import java.util.HashMap;
import java.util.Map;

import io.github.minecraftcursedlegacy.api.registry.Identifier;
import io.github.minecraftcursedlegacy.api.registry.Registry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.io.CompoundTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class BlockRegistry extends Registry<Block> {
	private final Map<Block, Boolean> ticksRandomly = new HashMap<>();
	private final Map<Block, Boolean> isFullOpaque = new HashMap<>();
	private final Map<Block, Boolean> hasBlockEntity = new HashMap<>();
	private final Map<Block, Integer> lightOpacity = new HashMap<>();
	private final Map<Block, Boolean> allowsGrassUnder = new HashMap<>();
	private final Map<Block, Integer> emittance = new HashMap<>();
	private final Map<Block, Boolean> noNotifyOnMetaChange = new HashMap<>();

	BlockRegistry(Identifier registryName) {
		super(Block.class, registryName, null);

		VanillaIds.initialiseBlocks();

		// add vanilla blocks
		for (int i = 0; i < Block.BY_ID.length; ++i) {
			Block value = Block.BY_ID[i];

			if (value != null) {
				this.byRegistryId.put(VanillaIds.getVanillaId(value), value);
				this.bySerialisedId.put(i, value);
			}
		}
	}

	@Override
	public <E extends Block> E registerValue(@NotNull Identifier id, E value) {
		throw new UnsupportedOperationException("Use register(Identifier, IntFunction<E>) instead, since blocks need to use the provided int serialised ids in their constructor!");
	}

	@Override
	protected int getNextSerialisedId() {
		return RegistryImpl.nextBlockId();
	}

	@Override
	protected int getStartSerialisedId() {
		return 1; // Because 0 is taken by air and is a null entry because notch spaghetti.
	}

	@Override
	public @Nullable Block getById(@NotNull Identifier id) {
		return super.getById(VanillaIds.correctLegacyBlockId(id));
	}
	
	// refactor note: not keeping the old code in the ()V beforeRemap method 'cuz literally no one will be manually invoking that 

	@Override
	protected void beforeRemap(CompoundTag tag) {
		int size = Block.BY_ID.length;

		// Clear the block array

		for (int i = 1; i < size; i++) { // Starting at 1 as microoptimisation because 0 is taken by a forced null value: Air
			Block block = Block.BY_ID[i];

			if (block != null) {
				ticksRandomly.put(block, Block.TICKS_RANDOMLY[i]);
				isFullOpaque.put(block, Block.FULL_OPAQUE[i]);
				hasBlockEntity.put(block, Block.HAS_BLOCK_ENTITY[i]);
				lightOpacity.put(block, Block.LIGHT_OPACITY[i]);
				allowsGrassUnder.put(block, Block.ALLOWS_GRASS_UNDER[i]);
				emittance.put(block, Block.EMITTANCE[i]);
				noNotifyOnMetaChange.put(block, Block.NO_NOTIFY_ON_META_CHANGE[i]);
			}
		}

		System.arraycopy(new Block[size], 0, Block.BY_ID, 0, size);
		System.arraycopy(new boolean[size], 0, Block.TICKS_RANDOMLY, 0, size);
		System.arraycopy(new boolean[size], 0, Block.FULL_OPAQUE, 0, size);
		System.arraycopy(new boolean[size], 0, Block.HAS_BLOCK_ENTITY, 0, size);
		System.arraycopy(new int[size], 0, Block.LIGHT_OPACITY, 0, size);
		System.arraycopy(new boolean[size], 0, Block.ALLOWS_GRASS_UNDER, 0, size);
		System.arraycopy(new int[size], 0, Block.EMITTANCE, 0, size);
		System.arraycopy(new boolean[size], 0, Block.NO_NOTIFY_ON_META_CHANGE, 0, size);

		VanillaIds.fixOldIds(tag, true);
	}

	@Override
	protected void onRemap(Block remappedValue, int newSerialisedId) {
		// Set the new values in the arrays
		Block.BY_ID[newSerialisedId] = remappedValue;
		((IdSetter) remappedValue).setId(newSerialisedId);

		// block item
		Item blockItem = RegistryImpl.T_2_TI.get(remappedValue);

		if (blockItem != null) {
			((HasParentId) blockItem).setParentId(newSerialisedId);
		}

		Block.TICKS_RANDOMLY[newSerialisedId] = ticksRandomly.getOrDefault(remappedValue, false);
		Block.FULL_OPAQUE[newSerialisedId] = isFullOpaque.getOrDefault(remappedValue, false);
		Block.HAS_BLOCK_ENTITY[newSerialisedId] = hasBlockEntity.getOrDefault(remappedValue, false);
		Block.LIGHT_OPACITY[newSerialisedId] = lightOpacity.getOrDefault(remappedValue, 0);
		Block.ALLOWS_GRASS_UNDER[newSerialisedId] = allowsGrassUnder.getOrDefault(remappedValue, false);
		Block.EMITTANCE[newSerialisedId] = emittance.getOrDefault(remappedValue, 0);
		Block.NO_NOTIFY_ON_META_CHANGE[newSerialisedId] = noNotifyOnMetaChange.getOrDefault(remappedValue, false);
	}
}