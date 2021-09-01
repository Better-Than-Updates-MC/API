package paulevs.corelib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.GuiElement;
import net.minecraft.client.render.Tessellator;
import paulevs.corelib.CoreLib;

@Mixin(GuiElement.class)
public class DrawableHelperMixin {
	@Shadow
	protected float zOffset;

	@Inject(method = "blit", at = @At("HEAD"), cancellable = true)
	private void blit(int x, int y, int u, int v, int width, int height, CallbackInfo info) {
		float size = CoreLib.getAtlasSize();
		if (size != 256) {
			Tessellator tesselator = Tessellator.INSTANCE;
			tesselator.start();
			tesselator.vertex(x, y + height, this.zOffset, u / size, (v + height) / size);
			tesselator.vertex(x + width, y + height, this.zOffset, (u + width) / size, (v + height) / size);
			tesselator.vertex(x + width, y, this.zOffset, (u + width) / size, v / size);
			tesselator.vertex(x, y, this.zOffset, u / size, v / size);
			tesselator.draw();
			info.cancel();
		}
	}
}
