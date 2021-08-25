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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.IntFunction;

import io.github.minecraftcursedlegacy.accessor.registry.BlockItemAccessor;
import io.github.minecraftcursedlegacy.accessor.registry.RecipeRegistryAccessor;
import io.github.minecraftcursedlegacy.accessor.registry.ShapedRecipeAccessor;
import io.github.minecraftcursedlegacy.accessor.registry.ShapelessRecipeAccessor;
import io.github.minecraftcursedlegacy.api.registry.Identifier;
import io.github.minecraftcursedlegacy.api.registry.Registry;
import io.github.minecraftcursedlegacy.impl.registry.sync.RegistryRemapper;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeRegistry;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.SmeltingRecipeRegistry;
import net.minecraft.util.io.CompoundTag;
import org.jetbrains.annotations.NotNull;

class ItemRegistry extends Registry<Item> {
	ItemRegistry(Identifier registryName) {
		super(Item.class, registryName, null);

		VanillaIds.initialiseItems();

		// add vanilla item
		for (int i = 0; i < Item.byId.length; ++i) {
			Item value = Item.byId[i];

			if (value instanceof BlockItem) {
				RegistryImpl.T_2_TI.put(Block.BY_ID[((BlockItemAccessor) value).getBlockId()], value);
			}

			if (value != null) {
				this.byRegistryId.put(VanillaIds.getVanillaId(value), value);
				this.bySerialisedId.put(i, value);
			}
		}
	}

	private final Item[] oldItems = new Item[Item.byId.length];

	@Override
	public <E extends Item> E registerValue(@NotNull Identifier id, E value) {
		throw new UnsupportedOperationException("Use register(Identifier, IntFunction<Item>) instead, since item need to use the provided int serialised ids in their constructor!");
	}

	/**
	 * Item are weird.
	 */
	@Override
	public <E extends Item> E register(@NotNull Identifier id, @NotNull IntFunction<E> valueProvider) {
		return super.register(id, rawSID -> valueProvider.apply(rawSID - Block.BY_ID.length));
	}

	@Override
	protected int getNextSerialisedId() {
		return RegistryImpl.nextItemId();
	}

	@Override
	public @NotNull Item getById(@NotNull Identifier id) {
		return super.getById(VanillaIds.correctLegacyItemId(id));
	}

	// refactor note: not keeping the old code in the ()V beforeRemap method 'cuz literally no one will be manually invoking that 

	@Override
	protected void beforeRemap(CompoundTag tag) {
		int size = Item.byId.length;
		// copy old array for later recipe remapping
		System.arraycopy(Item.byId, 0, this.oldItems, 0, size);
		// clear array
		System.arraycopy(new Item[size], 0, Item.byId, 0, size);

		VanillaIds.fixOldIds(tag, false);
	}

	@Override
	protected void onRemap(Item remappedValue, int newSerialisedId) {
		Item.byId[newSerialisedId] = remappedValue;
		((IdSetter) remappedValue).setId(newSerialisedId);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void postRemap() {
		// Remap Recipes
		RegistryRemapper.LOGGER.info("Remapping Crafting Recipes.");

		for (Recipe recipe : ((RecipeRegistryAccessor) RecipeRegistry.getInstance()).getRecipes()) {
			if (recipe instanceof ShapedRecipe) {
				// remap recipe ingredients
				ItemStack[] ingredients = ((ShapedRecipeAccessor) recipe).getIngredients();

				for (ItemStack ingredient : ingredients) {
					if (ingredient != null) {
						int oldId = ingredient.itemId;
						int newId = this.oldItems[oldId].id;

						// only remap if necessary
						if (oldId != newId) {
							// set new id
							ingredient.itemId = newId;
						}
					}
				}

				// recompute output id
				ItemStack result = ((ShapedRecipeAccessor) recipe).getOutput();
				int newId = this.oldItems[result.itemId].id;

				// only remap if necessary
				if (result.itemId != newId) {
					result.itemId = newId;
					((IdSetter) recipe).setId(newId);
				}
			} else if (recipe instanceof ShapelessRecipe) {
				// remap recipe ingredients
				List<ItemStack> ingredients = ((ShapelessRecipeAccessor) recipe).getInput();

				for (ItemStack instance : ingredients) {
					if (instance != null) {
						int oldId = instance.itemId;
						int newId = this.oldItems[oldId].id;

						// only remap if necessary
						if (oldId != newId) {
							// set new id
							instance.itemId = newId;
						}
					}
				}

				// recompute output id
				ItemStack result = ((ShapelessRecipeAccessor) recipe).getOutput();
				int newId = this.oldItems[result.itemId].id;

				// only remap if necessary
				if (result.itemId != newId) {
					result.itemId = newId;
				}
			}
		}

		RegistryRemapper.LOGGER.info("Remapping Smelting Recipes.");

		SmeltingRecipeRegistry smelting = SmeltingRecipeRegistry.getInstance();
		Map<Integer, ItemStack> newRecipes = new HashMap<>();

		smelting.getRecipes().forEach((oldInputId, output) -> {
			int newInputId = this.oldItems[(Integer) oldInputId].id;

			ItemStack result = (ItemStack) output;
			int newResultId = this.oldItems[result.itemId].id;

			// only remap if necessary
			if (result.itemId != newResultId) {
				result.itemId = newResultId;
			}

			newRecipes.put(newInputId, result);
		});

		((SmeltingRecipeSetter) smelting).setRecipes(newRecipes);

		// Invoke remap event
		super.postRemap();
	}

	@Override
	protected void addNewValues(List<Entry<Identifier, Item>> unmapped, CompoundTag tag) {
		int serialisedItemId = Block.BY_ID.length;

		for (Entry<Identifier, Item> entry : unmapped) {
			Item value = entry.getValue();

			if (value instanceof BlockItem) {
				int blockId = ((HasParentId) value).getParentId();

				// re-add to registry
				this.bySerialisedId.put(blockId, value);
				// add to tag
				tag.put(entry.getKey().toString(), blockId);
				this.onRemap(value, blockId);
			} else {
				while (this.bySerialisedId.get(serialisedItemId) != null) {
					++serialisedItemId;
				}

				// re-add to registry
				this.bySerialisedId.put(serialisedItemId, value);
				// add to tag
				tag.put(entry.getKey().toString(), serialisedItemId);
				this.onRemap(value, serialisedItemId);
			}
		}
	}

	<I extends Item> I addBlockItem(Identifier id, Block block, IntFunction<I> constructor) {
		I item = constructor.apply(block.id - Block.BY_ID.length);
		this.byRegistryId.put(id, item);
		this.bySerialisedId.put(item.id, item);
		RegistryImpl.T_2_TI.put(block, item);
		return item;
	}
}