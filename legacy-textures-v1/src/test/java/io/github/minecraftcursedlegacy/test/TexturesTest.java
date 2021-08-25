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

package io.github.minecraftcursedlegacy.test;

import io.github.minecraftcursedlegacy.api.client.AtlasMap;
import io.github.minecraftcursedlegacy.api.recipe.Recipes;
import io.github.minecraftcursedlegacy.api.registry.Identifier;
import io.github.minecraftcursedlegacy.api.registry.Registries;
import io.github.minecraftcursedlegacy.api.registry.BlockItems;
import io.github.minecraftcursedlegacy.api.registry.Translations;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TexturesTest implements ModInitializer {
	private static Item item, alsoItem;
	private static Block cross, betterCross;
	private static Block cube, redgrass;

	@Override
	public void onInitialize() {
		// Choco Atlas Mapping api
		item = Registries.ITEM.register(new Identifier("legacy-textures-test:item_texture"),
				id -> new BasicItem(id).setTexturePosition(2, 0).setTranslationKey("exampleTextureItem"));
		AtlasMap.registerAtlas(item, "/assets/legacy-textures-test/bc/item_textures.png");

		// set with the 1.1.0 model discovery api which uses choco's 0.x api generated atlas impl under the hood
		alsoItem = Registries.ITEM.register(new Identifier("legacy-textures-test:item_texture_too"),
				id -> new BasicItem(id).setTranslationKey("exampleTextureItemAlso"));

		// This one doesn't mess with the item texture and uses parented texture (you have to re-override a client side method to do this with PlantBlock however)
		cross = Registries.BLOCK.register(new Identifier("legacy-textures-test:iron_grass"),
				id -> new TallGrassBlock2(id).setTranslationKey("ironGrass"));
		BlockItems.registerBlockItem(new Identifier("legacy-textures-test:iron_grass"), cross);

		// This one does
		betterCross = Registries.BLOCK.register(new Identifier("legacy-textures-test:malachite_grass"),
				id -> new TallGrassBlock(id).setTranslationKey("malachiteGrass"));
		BlockItems.registerBlockItem(new Identifier("legacy-textures-test:malachite_grass"), betterCross);

		cube = Registries.BLOCK.register(new Identifier("legacy-textures-test:cursed_legacy_block"),
				id -> new BasicBlock(id).setTranslationKey("cursedLegacyBlock"));
		BlockItems.registerBlockItem(new Identifier("legacy-textures-test:cursed_legacy_block"), cube);

		redgrass = Registries.BLOCK.register(new Identifier("legacy-textures-test:red_grass"),
				id -> new CustomGrassBlockBlock(id).setTranslationKey("redGrass"));
		BlockItems.registerBlockItem(new Identifier("legacy-textures-test:red_grass"), redgrass);

		Recipes.addShapelessRecipe(new ItemStack(item), Block.WOOD);
		Recipes.addShapelessRecipe(new ItemStack(alsoItem), item);
		Recipes.addShapelessRecipe(new ItemStack(cross), alsoItem);
		Recipes.addShapelessRecipe(new ItemStack(betterCross), cross);
		Recipes.addShapelessRecipe(new ItemStack(cube), Item.STICK);
		Recipes.addShapelessRecipe(new ItemStack(redgrass), cube);

		Translations.addItemTranslation(item, "Example Item");
		Translations.addItemTranslation(alsoItem, "Example Item Too");
		Translations.addBlockTranslation(cross, "Example Cross Model Block");
		Translations.addBlockTranslation(betterCross, "Malachite Grass");
		Translations.addBlockTranslation(cube, "Cursed Legacy Block");
		Translations.addBlockTranslation(redgrass, "Red Grass");
	}

	static class BasicItem extends Item {
		BasicItem(int i) {
			super(i);
		}
	}

	static class TallGrassBlock extends PlantBlock {
		TallGrassBlock(int id) {
			super(id, 69);
			this.setHardness(0.0F);
			this.setSounds(GRASS_SOUNDS);
		}
	}

	static class TallGrassBlock2 extends TallGrassBlock {
		public TallGrassBlock2(int id) {
			super(id);
		}

		@Override
		@Environment(EnvType.CLIENT)
		public int getRenderType() {
			return 0;
		}
	}

	static class CustomGrassBlockBlock extends Block { // I would extend GrassBlock but that tints the whole block based on grass colour which is cursed
		CustomGrassBlockBlock(int id) {
			super(id, Material.ORGANIC);

			this.setHardness(0.6F);
			this.setSounds(GRASS_SOUNDS);
		}
	}

	static class BasicBlock extends Block {
		BasicBlock(int i) {
			super(i, 69, Material.DIRT);
		}
	}
}