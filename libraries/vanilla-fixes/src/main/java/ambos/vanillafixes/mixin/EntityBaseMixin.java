package ambos.vanillafixes.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
final class EntityBaseMixin {
    @Redirect(method = "move", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/EntityBase;x:D", opcode = Opcodes.PUTFIELD), require = 0)
    private void fixX(Entity entityBase, double value) {
        if (!entityBase.world.isClient || entityBase instanceof PlayerEntity || !(entityBase instanceof LivingEntity))
            entityBase.x = value;
    }

    @Redirect(method = "move", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/EntityBase;y:D", opcode = Opcodes.PUTFIELD), require = 0)
    private void fixY(Entity entityBase, double value) {
        if (!entityBase.world.isClient || entityBase instanceof PlayerEntity || !(entityBase instanceof LivingEntity))
            entityBase.y = value;
    }

    @Redirect(method = "move", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/EntityBase;z:D", opcode = Opcodes.PUTFIELD), require = 0)
    private void fixZ(Entity entityBase, double value) {
        if (!entityBase.world.isClient || entityBase instanceof PlayerEntity || !(entityBase instanceof LivingEntity))
            entityBase.z = value;
    }
}
