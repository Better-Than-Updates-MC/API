package io.github.prospector.modmenu.mixin;

import com.terraformersmc.modmenu.ModMenu;
import io.github.prospector.modmenu.gui.ModListScreen;
import io.github.prospector.modmenu.gui.ModMenuButtonWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.PauseScreen;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.TranslationStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PauseScreen.class)
public class PauseScreenMixin extends Screen {
	@SuppressWarnings("unchecked")
	@Inject(at = @At("RETURN"), method = "init")
	public void drawMenuButton(CallbackInfo info) {
		TranslationStorage translator = TranslationStorage.getInstance();
		this.buttons.add(new ModMenuButtonWidget(100, this.width / 2 - 100, this.height / 4 + 72 - 16,
				200, 20,
				translator.translate("modmenu.title") + " " + translator.translate("modmenu.loaded", ModMenu.getFormattedModCount()))
		);
	}

	@Inject(method = "buttonClicked", at = @At("HEAD"))
	private void onActionPerformed(ButtonWidget button, CallbackInfo ci) {
		if (button.id == 100) {
			client.openScreen(new ModListScreen(this));
		}
	}
}
