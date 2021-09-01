package com.halotroop.fabric.api.event.interaction;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

public class BlockHighlightEvents {
	/**
	 * Event called when a block is highlighted.
	 */
	public static final Event<BlockHighlight> BLOCK_HIGHLIGHT = EventFactory.createArrayBacked(BlockHighlight.class,
			listeners -> (renderer, player, pos, i, stack, f) -> {
				for (BlockHighlight listener : listeners) {
					if (listener.onBlockHighlight(renderer, player, pos, i, stack, f)) {
						return true;
					}
				}
				return false;
			});

	@FunctionalInterface
	public interface BlockHighlight {
		/**
		 * Allow custom handling of highlights.
		 * @return true if the highlight has been handled.
		 */
		boolean onBlockHighlight(GameRenderer gameRenderer,
		                         PlayerEntity player, Vec3d pos, int i,
		                         ItemStack heldItem, float f);
	}
}
