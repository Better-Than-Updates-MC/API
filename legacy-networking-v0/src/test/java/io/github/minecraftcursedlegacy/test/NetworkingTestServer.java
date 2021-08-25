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

import io.github.minecraftcursedlegacy.api.event.ActionResult;
import io.github.minecraftcursedlegacy.api.event.BlockInteractionCallback;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.minecraft.server.entity.player.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class NetworkingTestServer implements DedicatedServerModInitializer {
	@Override
	public void onInitializeServer() {
		BlockInteractionCallback.EVENT.register((player, world, item, block, x, y, z, face) -> {
			// This may not trigger every time due to load order.
			// That is ok.
			if (face == 0 && world instanceof ServerWorld && player instanceof ServerPlayerEntity) {
				NetworkingTest.testPluginChannel.send(new byte[] {(byte) block.id}, (ServerPlayerEntity) player);
			}

			return ActionResult.PASS;
		});
	}
}
