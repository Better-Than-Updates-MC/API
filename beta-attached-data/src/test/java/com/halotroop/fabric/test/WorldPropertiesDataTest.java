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
import com.halotroop.fabric.api.event.BlockInteractionCallback;
import com.halotroop.fabric.api.registry.Identifier;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.util.io.CompoundTag;

public class WorldPropertiesDataTest implements ModInitializer {
	@Override
	public void onInitialize() {
		test_world = DataManager.WORLD_PROPERTIES.addAttachedData(TestWorldData.ID, properties -> new TestWorldData(false));

		BlockInteractionCallback.EVENT.register((player, world, item, block, x, y, z, face) -> {
			if (!world.isClient) {
				if (item != null && item.getType() == Item.STICK) {
					TestWorldData data = DataManager.WORLD_PROPERTIES.getAttachedData(world.getProperties(), test_world);
					data.active = !data.active;
				} else if (DataManager.WORLD_PROPERTIES.getAttachedData(world.getProperties(), test_world).active) {
					world.createExplosion(player, x, y, z, 3.0f);
					return ActionResult.SUCCESS;
				}
			}

			return ActionResult.PASS;
		});
	}

	public static DataKey<TestWorldData> test_world;

	private static class TestWorldData implements AttachedData {
		private TestWorldData(boolean active) {
			this.active = active;
		}

		private boolean active;

		@Override
		public Identifier getId() {
			return ID;
		}

		@Override
		public CompoundTag toTag(CompoundTag tag) {
			tag.put("active", this.active);
			return tag;
		}

		@Override
		public void fromTag(CompoundTag tag) {
			this.active = tag.getBoolean("active");
		}

		@Override
		public AttachedData copy() {
			return new TestWorldData(this.active);
		}

		static final Identifier ID = new Identifier("modid", "test_world");
	}
}
