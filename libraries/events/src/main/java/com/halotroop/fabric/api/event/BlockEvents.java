package com.halotroop.fabric.api.event;

import io.github.minecraftcursedlegacy.api.event.ActionResult;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * <b/>Includes the following block-related events:
 * <li/>{@link #BLOCK_HIGHLIGHT Highlight}
 * <li/>{@link #BLOCK_INTERACTION Interaction}
 */
@SuppressWarnings("unused")
public final class BlockEvents {
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

	public static final Event<BlockInteraction> BLOCK_INTERACTION = EventFactory.createArrayBacked(BlockInteraction.class,
			(listeners) -> (player, world, item, block, x, y, z, face) -> {
				for (BlockInteraction listener : listeners) {
					ActionResult result = listener.onBlockInteract(player, world, item, block, x, y, z, face);

					if (result != ActionResult.PASS) {
						return result;
					}
				}

				return ActionResult.PASS;
			});

	public interface BlockHighlight {
		/**
		 * Allow custom handling of highlights.
		 * @return true if the highlight has been handled.
		 */
		boolean onBlockHighlight(GameRenderer gameRenderer,
		                         PlayerEntity player, Vec3d pos, int i,
		                         ItemStack heldItem, float f);
	}

	/**
	 * Callback for using a block (default: right mouse button). This is run both client and server side.
	 *
	 * <p>Upon return:
	 * <ul>
	 * <li> SUCCESS cancels further event processing and vanilla code, and the method this is event is called from returns true (succeeds).
	 * <li> PASS falls back to further event processing. If all events PASS, then vanilla behaviour runs.
	 * <li> FAIL cancels further event processing and vanilla code, and the method this is event is called from returns false (fails).
	 * </ul>
	 */
	@FunctionalInterface
	public interface BlockInteraction {

		/**
		 * @param player the player causing the block interaction.
		 * @param world the world the block is being interacted with in.
		 * @param stack the item instance that the player is using to interact with the block.
		 * @param block the block being interacted with at the time of this event firing. This does not change if an event subscriber alters the block at that position.
		 * @param face probably the block face. The last parameter of {@link net.minecraft.item.ItemStack#useOnBlock(net.minecraft.entity.player.PlayerEntity, net.minecraft.world.World, int, int, int, int)};
		 * @return the action result, as specified in the javadoc of {@link com.halotroop.fabric.api.event.BlockEvents.BlockInteraction}.
		 */
		ActionResult onBlockInteract(PlayerEntity player, World world, @Nullable ItemStack stack, Block block, int x, int y, int z, int face);
	}
}
