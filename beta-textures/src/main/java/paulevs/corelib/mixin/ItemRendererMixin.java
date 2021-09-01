package paulevs.corelib.mixin;

import java.util.Random;

import net.minecraft.client.render.block.BlockRenderer;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.entity.ItemEntity;
import paulevs.corelib.CoreLib;
import paulevs.corelib.model.Model;
import paulevs.corelib.model.shape.Shape;
import paulevs.corelib.registry.ModelRegistry;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
	@Shadow
	private BlockRenderer field_1708;

	@Shadow
	public boolean field_1707 = true;

	@Shadow
	private Random rand;

	/**
	 * Render item entity in the world
	 */
	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	private void renderEntity(ItemEntity entity, double posX, double posY, double posZ, float pitch, float yaw, CallbackInfo info) {
		Model model = ModelRegistry.getItemModel(entity.stack.getItem(), entity.stack.getDamage());
		if (model != null && model.hasItem()) {
			CoreLib.ITEM_VIEW.setBlockId(0);
			Shape.setBlockView(CoreLib.ITEM_VIEW);
			Shape.setMeta(entity.stack.getDamage());
			Shape.setRenderer(field_1708);
			Shape.setWorldCulling(true);
			Shape.drawAll();

			rand.setSeed(187L);
			GL11.glPushMatrix();

			float offset = MathHelper.sin(((float) entity.age + yaw) / 10.0F + entity.field_567) * 0.1F + 0.1F;
			float angle = (((float) entity.age + yaw) / 20.0F + entity.field_567) * (180F / (float) Math.PI);

			int count = 1;
			if (entity.stack.count > 1) {
				count = 2;
			}

			if (entity.stack.count > 5) {
				count = 3;
			}

			if (entity.stack.count > 20) {
				count = 4;
			}

			GL11.glTranslatef((float) posX, (float) posY + offset, (float) posZ);
			GL11.glEnable(32826);

			GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
			model.bindAtlas();
			float scale = 0.25F;

			GL11.glScalef(scale, scale, scale);
			
			for (int var15 = 0; var15 < count; ++var15) {
				GL11.glPushMatrix();
				if (var15 > 0) {
					float var16 = (this.rand.nextFloat() * 2.0F - 1.0F) * 0.2F / scale;
					float var17 = (this.rand.nextFloat() * 2.0F - 1.0F) * 0.2F / scale;
					float var18 = (this.rand.nextFloat() * 2.0F - 1.0F) * 0.2F / scale;
					GL11.glTranslatef(var16, var17, var18);
				}

				Shape.setOffsetItem();
				Tessellator.INSTANCE.start();
				Tessellator.INSTANCE.setNormal(0.0F, -1.0F, 0.0F);
				model.renderItem();
				Tessellator.INSTANCE.draw();
				Shape.resetOffset();
				GL11.glPopMatrix();
			}

			GL11.glDisable(32826);
			GL11.glPopMatrix();

			info.cancel();
		}
	}

	@Inject(method = "method_1483", at = @At("HEAD"), cancellable = true)
	private void method_1483(int x, int y, int u, int v, int width, int height, CallbackInfo info) {
		float scale = CoreLib.getAtlasSize();
		if (scale != 256) {
			CoreLib.selectedAtlas.bind();
			Tessellator.INSTANCE.start();
			Tessellator.INSTANCE.vertex(x, y + height, 0, u / scale, (v + height) / scale);
			Tessellator.INSTANCE.vertex(x + width, y + height, 0, (u + width) / scale, (v + height) / scale);
			Tessellator.INSTANCE.vertex(x + width, y, 0, (u + width) / scale, v / scale);
			Tessellator.INSTANCE.vertex(x, y, 0, u / scale, v / scale);
			Tessellator.INSTANCE.draw();
			info.cancel();
		}
	}

	/**
	 * Render item in the inventory
	 */
	@Inject(method = "renderItemOnGui", at = @At("HEAD"), cancellable = true)
	private void renderModel(TextRenderer textRenderer, TextureManager textureManager, int id, int meta, int textureID, int x, int y, CallbackInfo info) {
		Item item = Item.byId[id];
		Model model = ModelRegistry.getItemModel(item, meta);
		if (model != null) {
			CoreLib.ITEM_VIEW.setBlockId(0);
			CoreLib.ITEM_VIEW.setBlockMeta(meta);
			Shape.setBlockView(CoreLib.ITEM_VIEW);
			Shape.setPos(0, 0, 0);
			Shape.setRenderer(field_1708);
			Shape.setMeta(meta);
			Shape.setWorldCulling(true);
			Shape.resetOffset();
			Shape.drawAll();

			GL11.glPushMatrix();
			GL11.glTranslatef((float) (x - 2), (float) (y + 3), -3.0F);
			GL11.glScalef(10.0F, 10.0F, 10.0F);
			GL11.glTranslatef(1.0F, 0.5F, 1.0F);
			GL11.glScalef(1.0F, 1.0F, -1.0F);
			GL11.glRotatef(210.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			// GL11.glEnable(GL11.GL_BLEND);

			int var10 = item.getNameColor(meta);
			float var11 = (float) (var10 >> 16 & 255) / 255.0F;
			float var12 = (float) (var10 >> 8 & 255) / 255.0F;
			float var13 = (float) (var10 & 255) / 255.0F;
			if (this.field_1707) {
				GL11.glColor4f(var11, var12, var13, 1.0F);
			}

			GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glColor4f(1, 0, 0, 1F);

			this.field_1708.field_81 = this.field_1707;
			
			Shape.setColorWhite();
			model.bindAtlas();
			Tessellator.INSTANCE.start();
			Tessellator.INSTANCE.setNormal(0.0F, -1.0F, 0.0F);
			model.renderItem();
			Tessellator.INSTANCE.draw();

			this.field_1708.field_81 = true;

			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();

			info.cancel();
		}
	}

	@ModifyConstant(method = "render", constant = @Constant(floatValue = 256.0F), expect = 3)
	private float changeSizeF(float original) {
		return CoreLib.getAtlasSize();
	}
}
