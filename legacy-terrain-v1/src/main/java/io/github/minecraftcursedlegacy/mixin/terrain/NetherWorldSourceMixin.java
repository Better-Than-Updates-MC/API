package io.github.minecraftcursedlegacy.mixin.terrain;

import java.util.Random;

import net.minecraft.block.FallingBlock;
import net.minecraft.world.World;
import net.minecraft.world.source.NetherWorldSource;
import net.minecraft.world.source.WorldSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.minecraftcursedlegacy.api.terrain.ChunkGenEvents;

@Mixin(NetherWorldSource.class)
public class NetherWorldSourceMixin {
	@Shadow
	private Random rand;
	@Shadow
	private World world;

	@Inject(at = @At("RETURN"), method = "decorate")
	private void onDecorate(WorldSource worldSource, int x, int z, CallbackInfo info) {
		FallingBlock.fallInstantly = true;
		x *= 16;
		z *= 16;

		ChunkGenEvents.Decorate.NETHER.invoker().onDecorate(
				this.world,
				this.world.getBiomeSource().getBiome(x + 16, z + 16),
				this.rand,
				x, z);

		FallingBlock.fallInstantly = false;
	}
}
