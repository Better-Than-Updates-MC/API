package com.halotroop.fabric.mixin.terrain.client;

import com.halotroop.fabric.api.terrain.ChunkGenerator;
import com.halotroop.fabric.impl.terrain.InternalWorldSourceAccess;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.World;
import net.minecraft.world.WorldProperties;
import net.minecraft.world.source.WorldSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(World.class)
@Environment(EnvType.CLIENT)
public class WorldMixin {
	@Environment(EnvType.CLIENT)
	@Inject(at = @At("RETURN"), method = "initSpawnPoint")
	private void recomputeSpawnY(CallbackInfo info) {
		World self = (World) (Object) this;
		WorldSource cg = ((InternalWorldSourceAccess) self.dimension).getInternalWorldSource();

		if (cg instanceof ChunkGenerator) {
			WorldProperties properties = self.getProperties();
			properties.setSpawnPosition(properties.getSpawnX(), ((ChunkGenerator) cg).getMinSpawnY() + 1, properties.getSpawnY());
		}
	}
}
