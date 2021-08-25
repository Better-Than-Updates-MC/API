package paulevs.corelib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.texture.TextureManager;
import paulevs.corelib.CoreLib;

@Mixin(TextureManager.class)
public class TextureManagerMixin {
	@Inject(method = "getTextureId(Ljava/lang/String;)I", at = @At("HEAD"), cancellable = true)
	private void getTextureId(String string, CallbackInfoReturnable<Integer> info) {
		if (string.equals("/terrain.png")) {
			CoreLib.selectedAtlas = CoreLib.blocksAtlas;
			info.setReturnValue(CoreLib.blocksAtlas.getTexture().getID());
			info.cancel();
		}
		else if (string.equals("/gui/items.png")) {
			CoreLib.selectedAtlas = CoreLib.itemsAtlas;
			info.setReturnValue(CoreLib.itemsAtlas.getTexture().getID());
			info.cancel();
		}
		else {
			CoreLib.selectedAtlas = null;
		}
	}
}
