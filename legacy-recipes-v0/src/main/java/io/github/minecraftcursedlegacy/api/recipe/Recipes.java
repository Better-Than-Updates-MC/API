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

package io.github.minecraftcursedlegacy.api.recipe;

import io.github.minecraftcursedlegacy.accessor.recipe.RecipeRegistryAccessor;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.DyeRecipes;
import net.minecraft.recipe.RecipeRegistry;

/**
 * Class that acts as an interface for the recipe registry.
 */
public final class Recipes {
	private Recipes() {
		// NO-OP
	}

	/**
	 * Adds a shaped recipe to the game.
	 * For Example, the vanilla cake recipe:<br/>&nbsp;<b>{@code Recipes.addShapedRecipe(new ItemStack(Item.CAKE, 1), "AAA", "BEB", "CCC", 'A', Item.MILK_BUCKET, 'B', Item.SUGAR, 'C', Item.WHEAT, 'E', Item.EGG);}</b>
	 * @param result the item instance the recipe crafts.
	 * @param recipe the recipe, shaped.
	 * @see RecipeRegistry
	 */
	public static void addShapedRecipe(ItemStack result, Object... recipe) {
		((RecipeRegistryAccessor) RecipeRegistry.getInstance()).invokeAddShapedRecipe(result, recipe);
	}

	/**
	 * Adds a shapeless recipe to the game.
	 * For Example, the vanilla bonemeal recipe:<br/>&nbsp<b>{@code arg.addShapelessRecipe(new ItemStack(Item.DYE_POWDER, 3, 15), Item.BONE);}</b>
	 * @param result the item instance the recipe crafts.
	 * @param ingredients a list of various item instances, item, and blocks which are the required ingredients of this recipe.
	 * @see DyeRecipes
	 */
	public static void addShapelessRecipe(ItemStack result, Object... ingredients) {
		((RecipeRegistryAccessor) RecipeRegistry.getInstance()).invokeAddShapelessRecipe(result, ingredients);
	}
}
