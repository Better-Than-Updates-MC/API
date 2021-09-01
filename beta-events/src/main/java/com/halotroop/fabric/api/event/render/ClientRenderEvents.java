package com.halotroop.fabric.api.event.render;

import com.halotroop.fabric.api.event.lifecycle.ClientLifecycleEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;

@Environment(EnvType.CLIENT)
public class ClientRenderEvents {
	/**
	 * Event for the start of the block render.
	 */
	public static final Event<BlockRender> START_BLOCK_RENDER = EventFactory.createArrayBacked(BlockRender.class,
			listeners -> (block, x, y, z) -> {
				for (BlockRender listener : listeners) {
					listener.onBlockRender(block, x, y ,z);
				}
			});

	/**
	 * Event for the start of a block render.
	 */
	public static final Event<BlockRender> END_BLOCK_RENDER = EventFactory.createArrayBacked(BlockRender.class,
			listeners -> (block, x, y, z) -> {
				for (BlockRender listener : listeners) {
					listener.onBlockRender(block, x, y ,z);
				}
			});

	@FunctionalInterface
	public interface BlockRender {
		/**
		 * Called when a block is rendered.
		 * @param block the block being rendered.
		 * @param blockX the X position of the block
		 * @param blockY the Y position of the block
		 * @param blockZ the Z position of the block
		 */
		void onBlockRender(Block block, int blockX, int blockY, int blockZ);
	}

	/**
	 * Event for the start of the block render.
	 */
	public static final Event<BedBlockRender> START_BED_BLOCK_RENDER = EventFactory.createArrayBacked(BedBlockRender.class,
			listeners -> (block, x, y, z) -> {
				for (BedBlockRender listener : listeners) {
					listener.onBlockRender(block, x, y ,z);
				}
			});

	/**
	 * Event for the start of a block render.
	 */
	public static final Event<BedBlockRender> END_BED_BLOCK_RENDER = EventFactory.createArrayBacked(BedBlockRender.class,
			listeners -> (block, x, y, z) -> {
				for (BedBlockRender listener : listeners) {
					listener.onBlockRender(block, x, y ,z);
				}
			});

	@FunctionalInterface
	public interface BedBlockRender {
		/**
		 * Called when a bed block is rendered.
		 * @param block the block being rendered.
		 * @param blockX the X position of the block
		 * @param blockY the Y position of the block
		 * @param blockZ the Z position of the block
		 */
		void onBlockRender(Block block, int blockX, int blockY, int blockZ);
	}


}
