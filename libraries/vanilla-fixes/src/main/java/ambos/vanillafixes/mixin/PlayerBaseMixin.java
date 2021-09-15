package ambos.vanillafixes.mixin;

import ambos.vanillafixes.MinecraftUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
final class PlayerBaseMixin extends LivingEntity {
    @Shadow
    public String playerCapeUrl;

    @Shadow
    public String name;

    private PlayerBaseMixin(World level) {
        super(level);
    }

    @Inject(method = "initCape", at = @At("RETURN"), require = 0)
    private void onInitCloak(CallbackInfo ci) {
        playerCapeUrl = MinecraftUtil.getPlayerCape(name);
        capeUrl = playerCapeUrl;
    }
}
