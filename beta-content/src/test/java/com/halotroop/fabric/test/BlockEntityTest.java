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

import com.halotroop.fabric.api.registry.Identifier;
import com.halotroop.fabric.api.content.BlockItems;
import com.halotroop.fabric.api.registry.Registries;
import com.halotroop.fabric.api.content.BlockEntities;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.SmeltingRecipeRegistry;

public class BlockEntityTest implements ModInitializer {
	@Override
	public void onInitialize() {
		System.out.println("Hello, Fabric Block Entities World!");
		blockWithEntity = Registries.BLOCK.register(new Identifier("modid:block_with_entity"),
				i -> new BasicBlockWithEntity(i).setTranslationKey("exampleBlockWithEntity"));
		blockWithEntityItem = BlockItems.registerBlockItem(new Identifier("modid:block_with_entity"), blockWithEntity);
		blockEntityClass = BasicBlockWithEntity.BasicBlockEntity.class;
		BlockEntities.registerBlockEntity(blockEntityClass, new Identifier("modid:block_entity"));

		SmeltingRecipeRegistry.getInstance().addSmeltingRecipe(Block.DIRT.id, new ItemStack(blockWithEntity));
	}

	public static Block blockWithEntity;
	public static Item blockWithEntityItem;
	public static Class<? extends BlockEntity> blockEntityClass;

	public static class BasicBlockWithEntity extends BlockWithEntity {
		protected BasicBlockWithEntity(int i) {
			super(i, 69, Material.DIRT);
		}

		@Override
		protected BlockEntity createBlockEntity() {
			return new BasicBlockEntity();
		}

		static class BasicBlockEntity extends BlockEntity {
			@Override
			public void tick() {
				System.out.println("test block tick!");
			}
		}
	}
}
