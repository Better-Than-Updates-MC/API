package ambos.vanillafixes.mixin;

import ambos.vanillafixes.MinecraftUtil;
import net.minecraft.client.entity.player.RemoteClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RemoteClientPlayerEntity.class)
abstract class RemoteClientPlayerMixin extends PlayerEntity {
    private RemoteClientPlayerMixin(World level) {
        super(level);
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/lang/StringBuilder;toString()Ljava/lang/String;"), require = 0)
    private String toStr(StringBuilder builder, World level, String username) {
        return MinecraftUtil.getPlayerSkin(username);
    }

    @Inject(method = "<init>", at = @At("RETURN"), require = 0)
    private void RemoteClientPlayer(World world, String username, CallbackInfo ci) {
        if (username != null && username.length() > 0) {
            capeUrl = MinecraftUtil.getPlayerCape(username);
        }
    }
}
