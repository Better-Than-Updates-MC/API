package com.halotroop.fabric.impl.terrain;

import io.github.minecraftcursedlegacy.api.ModPostInitializer;
import net.minecraft.world.biome.Biome;

/**
 * ReComputes the biome array after biome placement registration in ModInitializers run, in order to allow modded biomes to generate.
 */
public class BiomeRecomputer implements ModPostInitializer {
	@Override
	public void onPostInitialize() {
		Biome.createBiomeArray();
	}
}
