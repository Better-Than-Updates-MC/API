package com.terraformersmc.modmenu.accessor;

import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ButtonWidget.class)
public interface ButtonWidgetAccessor {
	@Accessor
	int getWidth();

	@Accessor
	void setWidth(int width);
}
