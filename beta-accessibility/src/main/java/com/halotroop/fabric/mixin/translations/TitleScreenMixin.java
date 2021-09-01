package com.halotroop.fabric.mixin.translations;

import com.halotroop.fabric.accessor.translations.ButtonWidgetAccessor;
import com.halotroop.fabric.api.registry.Identifier;
import com.halotroop.fabric.impl.translations.gui.widget.IconButtonWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.menu.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
	@SuppressWarnings("unchecked")
	@Inject(method = "init", at = @At("TAIL"))
	private void addLocaleButton(CallbackInfo ci) {
		for (Object obj : this.buttons) {
			if (!(obj instanceof ButtonWidget)) return;
			ButtonWidget button = (ButtonWidget) obj;

			if (button.id == 0) {
				int quarterWidth = (((ButtonWidgetAccessor)button).getWidth() / 4);
				int square = ((ButtonWidgetAccessor) button).getHeight();

				this.buttons.add(new IconButtonWidget(5, button.x, button.y, square + 2, square,
						new Identifier("beta-accessibility", "icons"), "Locale"));

				((ButtonWidgetAccessor) button).setWidth((quarterWidth * 3));
				button.x += (quarterWidth + 2);

				break;
			}
		}
	}
}
