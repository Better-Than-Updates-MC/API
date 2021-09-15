package com.halotroop.fabric.accessor;

import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ButtonWidget.class)
public interface ButtonWidgetAccessor {
	@Accessor("width")
	int getWidth();

	@Accessor
	void setWidth(int width);
}
