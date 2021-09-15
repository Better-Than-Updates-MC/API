package ambos.vanillafixes.mixin;

import net.minecraft.block.FenceBlock;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FenceBlock.class)
final class FenceMixin {
    @Inject(method = "canPlaceAt", at = @At("RETURN"), cancellable = true, require = 0)
    private void onCanPlaceAt(World level, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
