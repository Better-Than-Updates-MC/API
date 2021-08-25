package paulevs.corelib.mixin;

import net.minecraft.block.Block;
import net.minecraft.client.render.block.BlockRenderer;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.Tessellator;
import paulevs.corelib.CoreLib;
import paulevs.corelib.model.Model;
import paulevs.corelib.model.shape.Shape;
import paulevs.corelib.registry.ModelRegistry;

@Mixin(BlockRenderer.class)
public class BlockRendererMixin {
	@Shadow
	private BlockView blockView;

	@Shadow
	public boolean field_81;
	
	/**
	 * Destruction stage, if = -1 then normal block render, otherwise used as index
	 */
	@Shadow
	private int textureOverride;

	/**
	 * Main method to render block in the world
	 */
	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	private void renderBlock(Block block, int x, int y, int z, CallbackInfoReturnable<Boolean> info) {
		int meta = blockView.getBlockMeta(x, y, z);
		Model model = ModelRegistry.getBlockModel(block, meta);
		if (model != null) {
			Shape.setBlockView(blockView);
			Shape.setPos(x, y, z);
			Shape.setBlock(block);
			Shape.setRenderer((BlockRenderer) (Object) this);
			Shape.setWorldCulling(false);
			Shape.setColorFromWorld();
			Shape.setLightFromWorld();
			Shape.setMeta(meta);
			Shape.resetOffset();
			Shape.drawAll();

			if (textureOverride > -1) {
				Shape.setDestruction(textureOverride - 240);
			}
			
			model.renderBlock();

			Shape.setDestruction(-1);
			info.setReturnValue(true);
			info.cancel();
		}
	}

	/**
	 * Main method to render block item in player hand
	 */
	@Inject(method = "method_48", at = @At("HEAD"), cancellable = true)
	private void renderItem(Block block, int meta, float f, CallbackInfo info) {
		Model model = ModelRegistry.getBlockModel(block, meta);
		if (model != null && model.hasItem()) {
			CoreLib.ITEM_VIEW.setBlockId(block.id);
			CoreLib.ITEM_VIEW.setBlockMeta(meta);
			this.blockView = CoreLib.ITEM_VIEW;
			Shape.setBlockView(CoreLib.ITEM_VIEW);
			Shape.setPos(0, 0, 0);
			Shape.setBlock(block);
			Shape.setRenderer((BlockRenderer) (Object) this);
			Shape.setMeta(meta);
			Shape.setWorldCulling(true);
			Shape.resetOffset();
			Shape.drawAll();

			Tessellator.INSTANCE.start();
			Tessellator.INSTANCE.setNormal(0.0F, -1.0F, 0.0F);
			model.renderItem();
			Tessellator.INSTANCE.draw();

			info.cancel();
		}
	}
	
	@ModifyConstant(method = "*", constant = @Constant(floatValue = 256.0F), expect = 3)
	private float changeSizeF(float original) {
		return CoreLib.blocksAtlas.getSize();
	}

	@ModifyConstant(method = "*", constant = @Constant(doubleValue = 256.0D), expect = 3)
	private double changeSizeD(double original) {
		return CoreLib.blocksAtlas.getSize();
	}
}