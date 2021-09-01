/*
 * This software is provided under the terms of the Minecraft Forge Public License v1.0.
 */

package net.minecraft.src.forge;

import com.halotroop.fabric.api.event.interaction.BlockHighlightEvents;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

/**
 * @deprecated use {@link com.halotroop.fabric.api.event Fabric Beta Essentials events} directly instead.
 */
@Deprecated
public class ForgeHooksClient {
	private static int renderPass = -1;

	static int getRenderPass() {
		return renderPass;
	}

	/**
	 * @deprecated use {@link BlockHighlightEvents#BLOCK_HIGHLIGHT the Fabric alternative} directly.
	 * @see BlockHighlightEvents.BlockHighlight#onBlockHighlight
	 */
	@Deprecated
	public static boolean onBlockHighlight(GameRenderer gameRenderer,
	                                       PlayerEntity player, Vec3d pos, int i,
	                                       ItemStack stack, float f) {
		return BlockHighlightEvents.BLOCK_HIGHLIGHT.invoker().onBlockHighlight(gameRenderer, player, pos, i, stack, f);
	}
}
