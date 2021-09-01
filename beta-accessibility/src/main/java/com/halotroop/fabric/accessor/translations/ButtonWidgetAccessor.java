package com.halotroop.fabric.accessor.translations;

import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ButtonWidget.class)
public interface ButtonWidgetAccessor {
	@Accessor
	int getWidth();

	@Accessor
	void setWidth(int width);

	@Accessor
	int getHeight();

	@Accessor
	void setHeight(int width);
}
