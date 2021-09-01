package io.github.prospector.modmenu.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.OperatingSystems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Minecraft.class)
public interface MinecraftAccessor {

	@SuppressWarnings("PublicStaticMixinMember")
	@Invoker("getOperatingSystem")
	static OperatingSystems getOS() {
		return OperatingSystems.WINDOWS;
	}

}
