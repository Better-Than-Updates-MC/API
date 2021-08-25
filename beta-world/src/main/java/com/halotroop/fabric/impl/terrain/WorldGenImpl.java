package com.halotroop.fabric.impl.terrain;

import com.halotroop.fabric.api.terrain.ChunkGenEvents;
import com.halotroop.fabric.api.terrain.ExtendedBiome;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.feature.Feature;

public class WorldGenImpl implements ModInitializer {
	@Override
	public void onInitialize() {
		// Extended Biome
		ChunkGenEvents.Decorate decoration = (world, biome, rand, x, z) -> {
			if (biome instanceof ExtendedBiome) {
				ExtendedBiome eBiome = (ExtendedBiome) biome;

				for(int i = 0; i < eBiome.getTreesPerChunk(); ++i) {
					int xToGen = x + rand.nextInt(16) + 8;
					int zToGen = z + rand.nextInt(16) + 8;
					Feature feature = biome.getTree(rand);
					feature.setScale(1.0D, 1.0D, 1.0D);
					feature.generate(world, rand, xToGen, world.getHeight(xToGen, zToGen), zToGen);
				}
			}
		};

		ChunkGenEvents.Decorate.OVERWORLD.register(decoration);
		ChunkGenEvents.Decorate.NETHER.register(decoration);
	}
}
