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

import com.halotroop.fabric.api.attacheddata.v1.AttachedData;
import com.halotroop.fabric.api.attacheddata.v1.DataManager;
import com.halotroop.fabric.api.attacheddata.v1.DataManager.DataKey;
import com.halotroop.fabric.api.event.ActionResult;
import com.halotroop.fabric.api.event.interaction.BlockInteractionCallback;
import com.halotroop.fabric.api.registry.Identifier;
import com.halotroop.fabric.api.registry.Registries;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.util.io.CompoundTag;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

// Adds data attached to wooden axes. When a wooden axe is first right-clicked on a block, it picks up that block's essence.
// Subsequent right clicks change the blocks interacted with to the stored essence.
public class ItemDataTest implements ModInitializer {
	@Override
	public void onInitialize() {
		test_axe = DataManager.ITEM_STACK.addAttachedData(TestAxeData.ID, item ->
				new TestAxeData(TestAxeData.ID));

		BlockInteractionCallback.EVENT.register((player, world, stack, block, x, y, z, i1) -> {
			if (block != null && stack != null && stack.getItem() == Item.WOOD_AXE) {
				TestAxeData data = DataManager.ITEM_STACK.getAttachedData(stack, test_axe);

				world.setBlock(x, y, z, Objects.requireNonNull(Registries.BLOCK.getById(data.blockId)).id);
			}

			return ActionResult.PASS;
		});
	}

	public static DataKey<TestAxeData> test_axe;

	public static class TestAxeData implements AttachedData {
		public TestAxeData(@NotNull Identifier blockId) {
			this.blockId = blockId;
		}


		public @NotNull Identifier blockId;

		@Override
		public Identifier getId() {
			return ID;
		}

		@Override
		public CompoundTag writeNBT(CompoundTag tag) {
			tag.put("block", this.blockId.toString());
			return tag;
		}

		@Override
		public void readNBT(CompoundTag tag) {
			String block = tag.getString("block");

			if (block.equals("NULL")) {
				this.blockId = ID;
			} else {
				this.blockId = new Identifier(block);
			}
		}

		@Override
		public AttachedData copy() {
			return new TestAxeData(this.blockId);
		}

		public static final Identifier ID = new Identifier("modid", "test_axe");
	}
}
