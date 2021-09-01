package com.halotroop.fabric.api.block;

import net.minecraft.client.render.block.BlockRenderer;

public interface HasCustomItemRenderer {
	void renderInventory (BlockRenderer blockRenderer, int itemID, int meta);
}
