package ambos.vanillafixes.mixin;

import net.minecraft.network.ClientPlayPacketHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ClientPlayPacketHandler.class)
final class ClientPlayNetworkHandlerMixin {
    @ModifyConstant(method = "onHandshake", constant = @Constant(stringValue = "http://www.minecraft.net/game/joinserver.jsp?user="), require = 0)
    private String changeURL(String url) {
        return "http://session.minecraft.net/game/joinserver.jsp?user=";
    }
}
