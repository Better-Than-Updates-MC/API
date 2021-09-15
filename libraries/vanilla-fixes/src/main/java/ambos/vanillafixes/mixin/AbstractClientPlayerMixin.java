package ambos.vanillafixes.mixin;

import ambos.vanillafixes.MinecraftUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.util.Session;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.ClientPlayPacketHandler;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
abstract class AbstractClientPlayerMixin extends PlayerEntity {
    private AbstractClientPlayerMixin(World level) {
        super(level);
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/lang/StringBuilder;toString()Ljava/lang/String;"), require = 0)
    private String changeSkin(StringBuilder builder, Minecraft minecraft, World level, Session session, int integer) {
        return MinecraftUtil.getPlayerSkin(session.username);
    }

    @Inject(method = "<init>", at = @At("RETURN"), require = 0)
    private void AbstractClientPlayer(Minecraft client, World world, Session session, ClientPlayPacketHandler packetHandler, CallbackInfo ci) {
        if (session != null && session.username != null && session.username.length() > 0) {
            capeUrl = MinecraftUtil.getPlayerCape(session.username);
        }
    }
}
