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

package io.github.minecraftcursedlegacy.api.event;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Callback for right clicking a block. This is run both client and server side.
 *
 * <p>Upon return:
 * <ul>
 * <li> SUCCESS cancels further event processing and vanilla code, and the method this is event is called from returns true (succeeds).
 * <li> PASS falls back to further event processing. If all events PASS, then vanilla behaviour runs.
 * <li> FAIL cancels further event processing and vanilla code, and the method this is event is called from returns false (fails).
 * </ul>
 */
@FunctionalInterface
public interface BlockInteractionCallback {
	Event<BlockInteractionCallback> EVENT = EventFactory.createArrayBacked(BlockInteractionCallback.class,
			(listeners) -> (player, world, item, block, x, y, z, face) -> {
				for (BlockInteractionCallback listener : listeners) {
					ActionResult result = listener.onBlockInteract(player, world, item, block, x, y, z, face);

					if (result != ActionResult.PASS) {
						return result;
					}
				}

				return ActionResult.PASS;
			});

	/**
	 * @param player the player causing the block interaction.
	 * @param world the world the block is being interacted with in.
	 * @param stack the item instance that the player is using to interact with the block.
	 * @param block the block being interacted with at the time of this event firing. This does not change if an event subscriber alters the block at that position.
	 * @param face probably the block face. The last parameter of {@link ItemStack#useOnBlock(PlayerEntity, World, int, int, int, int)};
	 * @return the action result, as specified in the javadoc of {@link BlockInteractionCallback}.
	 */
	ActionResult onBlockInteract(PlayerEntity player, World world, @Nullable ItemStack stack, Block block, int x, int y, int z, int face);
}
