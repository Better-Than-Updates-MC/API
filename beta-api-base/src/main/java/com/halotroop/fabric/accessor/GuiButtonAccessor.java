package com.halotroop.fabric.accessor;

import net.minecraft.client.gui.widgets.Button;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Button.class)
public interface GuiButtonAccessor {

	@Accessor
	int getWidth();

	@Accessor
	void setWidth(int width);

	@Accessor
	void setHeight(int height);

}
