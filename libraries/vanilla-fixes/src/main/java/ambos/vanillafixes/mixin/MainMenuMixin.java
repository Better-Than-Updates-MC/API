package ambos.vanillafixes.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.menu.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
final class MainMenuMixin extends Screen {
    @Inject(method = "init", at = @At("HEAD"), require = 0)
    private void onInit(CallbackInfo ci) {
        client.isApplet = false;
    }
}
