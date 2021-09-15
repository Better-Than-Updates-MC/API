package com.halotroop.fabric.api.base.block;

import net.minecraft.client.render.block.BlockRenderer;

public interface HasCustomItemRenderer {
	void renderInventory (BlockRenderer blockRenderer, int itemID, int meta);
}
