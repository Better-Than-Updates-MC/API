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

import com.halotroop.fabric.api.client.AtlasMap;
import com.halotroop.fabric.api.content.Recipes;
import com.halotroop.fabric.api.registry.Identifier;
import com.halotroop.fabric.api.registry.Registries;
import com.halotroop.fabric.api.content.BlockItems;
import com.halotroop.fabric.api.registry.Translations;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TexturesTest implements ModInitializer {
	private static final Item ITEM, ALSO_ITEM;
	private static final Block CROSS, BETTER_CROSS;
	private static final Block CUBE, REDGRASS;

	static {
		// Choco Atlas Mapping api
		ITEM = Registries.ITEM.register(new Identifier("legacy-textures-test:item_texture"),
				id -> new BasicItem(id).setTexturePosition(2, 0).setTranslationKey("exampleTextureItem"));
		AtlasMap.registerAtlas(ITEM, "/assets/legacy-textures-test/bc/item_textures.png");

		// set with the 1.1.0 model discovery api which uses Chocohead's 0.x API generated atlas impl under the hood
		ALSO_ITEM = Registries.ITEM.register(new Identifier("legacy-textures-test:item_texture_too"),
				id -> new BasicItem(id).setTranslationKey("exampleTextureItemAlso"));

		// This one doesn't mess with the item texture and uses parented texture (you have to re-override a client side method to do this with PlantBlock however)
		CROSS = Registries.BLOCK.register(new Identifier("legacy-textures-test:iron_grass"),
				id -> new TallGrassBlock2(id).setTranslationKey("ironGrass"));
		BlockItems.registerBlockItem(new Identifier("legacy-textures-test:iron_grass"), CROSS);

		// This one does
		BETTER_CROSS = Registries.BLOCK.register(new Identifier("legacy-textures-test:malachite_grass"),
				id -> new TallGrassBlock(id).setTranslationKey("malachiteGrass"));
		BlockItems.registerBlockItem(new Identifier("legacy-textures-test:malachite_grass"), BETTER_CROSS);

		CUBE = Registries.BLOCK.register(new Identifier("legacy-textures-test:cursed_legacy_block"),
				id -> new BasicBlock(id).setTranslationKey("cursedLegacyBlock"));
		BlockItems.registerBlockItem(new Identifier("legacy-textures-test:cursed_legacy_block"), CUBE);

		REDGRASS = Registries.BLOCK.register(new Identifier("legacy-textures-test:red_grass"),
				id -> new CustomGrassBlockBlock(id).setTranslationKey("redGrass"));
		BlockItems.registerBlockItem(new Identifier("legacy-textures-test:red_grass"), REDGRASS);

		Recipes.addShapelessRecipe(new ItemStack(ITEM), Block.WOOD);
		Recipes.addShapelessRecipe(new ItemStack(ALSO_ITEM), ITEM);
		Recipes.addShapelessRecipe(new ItemStack(CROSS), ALSO_ITEM);
		Recipes.addShapelessRecipe(new ItemStack(BETTER_CROSS), CROSS);
		Recipes.addShapelessRecipe(new ItemStack(CUBE), Item.STICK);
		Recipes.addShapelessRecipe(new ItemStack(REDGRASS), CUBE);

		Translations.addItemTranslation(ITEM, "Example Item");
		Translations.addItemTranslation(ALSO_ITEM, "Example Item Too");
		Translations.addBlockTranslation(CROSS, "Example Cross Model Block");
		Translations.addBlockTranslation(BETTER_CROSS, "Malachite Grass");
		Translations.addBlockTranslation(CUBE, "Cursed Legacy Block");
		Translations.addBlockTranslation(REDGRASS, "Red Grass");
	}

	@Override
	public void onInitialize() {
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