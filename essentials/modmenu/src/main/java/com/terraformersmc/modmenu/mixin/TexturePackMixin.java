package com.terraformersmc.modmenu.mixin;

import com.terraformersmc.modmenu.ModMenu;
import net.minecraft.client.resource.TexturePack;
import net.minecraft.client.resource.ZippedTexturePack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.InputStream;

@Mixin({TexturePack.class, ZippedTexturePack.class})
public class TexturePackMixin {
	@Inject(method = "getResourceAsStream", at = @At(value = "INVOKE", target = "Ljava/lang/Class;getResourceAsStream(Ljava/lang/String;)Ljava/io/InputStream;", remap = false), cancellable = true)
	private void onGetResource(String resource, CallbackInfoReturnable<InputStream> ci) {
		InputStream in = ModMenu.class.getClassLoader().getResourceAsStream(resource);
		if (in != null) ci.setReturnValue(in);
	}
}
