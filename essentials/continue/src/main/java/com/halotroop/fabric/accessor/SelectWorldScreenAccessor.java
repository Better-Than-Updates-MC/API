package com.halotroop.fabric.accessor;

import net.minecraft.client.gui.screen.SelectWorldScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(SelectWorldScreen.class)
public interface SelectWorldScreenAccessor {
	@SuppressWarnings("rawtypes")
	@Accessor("worlds")
	List getWorlds();
}
