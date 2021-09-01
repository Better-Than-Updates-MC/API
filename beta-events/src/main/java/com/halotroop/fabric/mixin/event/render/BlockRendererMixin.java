package com.halotroop.fabric.mixin.event.render;

import com.halotroop.fabric.api.event.render.ClientRenderEvents;
import net.minecraft.block.Block;
import net.minecraft.client.render.block.BlockRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockRenderer.class)
public class BlockRendererMixin {
	@Inject(method = "render", at = @At("HEAD"))
	private void startBlockRender(Block block, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
		ClientRenderEvents.START_BLOCK_RENDER.invoker().onBlockRender(block, x, y, z);
	}

	@Inject(method = "render", at = @At("HEAD"))
	private void endBlockRender(Block block, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
		ClientRenderEvents.END_BLOCK_RENDER.invoker().onBlockRender(block, x, y, z);
	}
}
