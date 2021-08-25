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

package com.halotroop.fabric.test;

import com.halotroop.fabric.api.recipe.Recipes;
import com.halotroop.fabric.api.registry.BlockItems;
import com.halotroop.fabric.api.registry.Registries;
import com.halotroop.fabric.api.registry.Identifier;
import com.halotroop.fabric.api.registry.Translations;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.SmeltingRecipeRegistry;

public class RegistryTest implements ModInitializer {
	public static Item item;
	public static Block block;
	public static Item blockItem;

	@Override
	public void onInitialize() {
		System.out.println("Hello, Fabric World!");
		item = Registries.ITEM.register(new Identifier("modid:item"),
				i -> new BasicItem(i).setTexturePosition(5, 0).setTranslationKey("exampleItem"));
		block = Registries.BLOCK.register(new Identifier("modid:block"),
				i -> new BasicBlock(i, false).setTranslationKey("exampleBlock"));
		blockItem = BlockItems.registerBlockItem(new Identifier("modid:block"), block);

		SmeltingRecipeRegistry.getInstance().addSmeltingRecipe(item.id, new ItemStack(block));

		Recipes.addShapelessRecipe(new ItemStack(item, 2), Block.DIRT, Block.SAND);
		Recipes.addShapedRecipe(new ItemStack(block), "##", '#', Block.DIRT);

		Translations.addBlockTranslation(block, "Example Block");
		Translations.addItemTranslation(item, "Example Item");
	}
}
