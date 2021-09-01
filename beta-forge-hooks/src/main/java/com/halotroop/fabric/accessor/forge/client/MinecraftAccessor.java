package com.halotroop.fabric.accessor.forge.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.texture.TextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Minecraft.class)
@Environment(EnvType.CLIENT)
public interface MinecraftAccessor {
	@Accessor("gameRenderer")
	GameRenderer getGameRenderer();

	@Accessor("textureManager")
	TextureManager getTextureManager();

	@Accessor("instance")
	static Minecraft getInstance() {
		throw new AssertionError();
	}
}
