package paulevs.corelib.mixin;

import java.util.Collection;
import java.util.HashSet;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.texture.TextureManager;
import paulevs.corelib.CoreLib;
import paulevs.corelib.model.Model;
import paulevs.corelib.registry.ModelRegistry;
import paulevs.corelib.texture.TextureAtlas;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Shadow
	public TextureManager textureManager;

	@Inject(method = "init", at = @At("RETURN"))
	private void onInit(CallbackInfo info) {
		Collection<Model> models = ModelRegistry.getBlockModels();
		HashSet<String> textures = new HashSet<String>();
		models.forEach((model) -> {
			textures.addAll(model.getTextures());
		});
		CoreLib.blocksAtlas = new TextureAtlas("/terrain.png", textures);
		models.forEach((model) -> {
			model.init(CoreLib.blocksAtlas);
		});

		models = ModelRegistry.getItemModels();
		textures.clear();
		models.forEach((model) -> {
			textures.addAll(model.getTextures());
		});
		CoreLib.itemsAtlas = new TextureAtlas("/gui/items.png", textures);
		models.forEach((model) -> {
			model.init(CoreLib.itemsAtlas);
		});
	}
}
