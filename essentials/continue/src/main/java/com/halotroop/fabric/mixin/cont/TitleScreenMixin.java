package com.halotroop.fabric.mixin.cont;

import com.halotroop.fabric.accessor.ButtonWidgetAccessor;
import com.halotroop.fabric.accessor.SelectWorldScreenAccessor;
import net.minecraft.client.SingleplayerClientInteractionManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SelectWorldScreen;
import net.minecraft.client.gui.screen.menu.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.world.storage.WorldMetadata;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
	@SuppressWarnings("unchecked")
	@Inject(at = @At("RETURN"), method = "init")
	public void drawMenuButton(CallbackInfo info) {
		TranslationStorage translator = TranslationStorage.getInstance();

		ButtonWidget singlePlayerButton = null;
		for (Object button : this.buttons) {
			if (button instanceof ButtonWidget && ((ButtonWidget) button).id == 1) {
				singlePlayerButton = (ButtonWidget) button;
			}
		}

		if (singlePlayerButton == null) return;

		int newWidth = ((ButtonWidgetAccessor)(Object)singlePlayerButton).getWidth() / 2 - 1;
		((ButtonWidgetAccessor) singlePlayerButton).setWidth(newWidth);

		this.buttons.add(new ButtonWidget(99, this.width / 2 + 2, singlePlayerButton.y,
						newWidth, 20, translator.translate("fbe.continue"))
		);
	}

	@Inject(method = "buttonClicked", at = @At("HEAD"))
	private void onActionPerformed(ButtonWidget button, CallbackInfo ci) {
		if (button.id == 99) {
			SelectWorldScreen screen = new SelectWorldScreen(this);
			client.openScreen(screen);
			if (((SelectWorldScreenAccessor)(Object)screen).getWorlds().size() > 0) {
				screen.loadWorld(0);
			}
		}
	}
}
