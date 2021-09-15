package ambos.vanillafixes.mixin;

import net.minecraft.client.SingleplayerClientInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SingleplayerClientInteractionManager.class)
final class RemoteClientInteractionManagerMixin {
    @Shadow
    private int hitDelay;

    @Inject(method = "method_1721", at = @At("RETURN"), require = 0)
    private void changeHitDelay(CallbackInfo ci) {
        hitDelay = 0;
    }
}
