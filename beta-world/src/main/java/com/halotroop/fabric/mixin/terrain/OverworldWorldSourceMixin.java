package com.halotroop.fabric.mixin.terrain;

import java.util.Random;

import com.halotroop.fabric.api.terrain.ChunkGenEvents;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.source.OverworldWorldSource;
import net.minecraft.world.source.WorldSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OverworldWorldSource.class)
public class OverworldWorldSourceMixin {
	@Shadow
	private Random rand;
	@Shadow
	private World world;

	@Inject(at = @At(value = "INVOKE", target = "net/minecraft/world/World.getBiomeSource()Lnet/minecraft/world/gen/BiomeSource;", ordinal = 1), method = "decorate")
	private void onDecorate(WorldSource worldSource, int x, int z, CallbackInfo info) {
		// the parameter variables x and z are multiplied by 16 in the code early on and stored in a local variable
		// So we replicate it here for ease of mods using this, as block x/z is more applicable for decoration
		x *= 16;
		z *= 16;
		ChunkGenEvents.Decorate.OVERWORLD.invoker().onDecorate(
				this.world,
				this.world.getBiomeSource().getBiome(x + 16, z + 16), // yes, vanilla does +16 in getBiome calls for features.
				this.rand, x, z
		);
	}

	@Inject(at = @At("RETURN"), method = "shapeChunk")
	private void shapeChunk(int chunkX, int chunkZ, byte[] blocks, Biome[] biomes, double[] temperatures, CallbackInfo info) {
		ChunkGenEvents.Shape.OVERWORLD.invoker().onShape(chunkX, chunkZ, blocks, biomes, temperatures);
	}
}
