package com.halotroop.fabric.test;

import com.halotroop.fabric.api.terrain.ChunkGenerator;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.source.OverworldWorldSource;
import net.minecraft.world.source.WorldSource;

public class LowFlatChunkGenerator extends ChunkGenerator {
	public LowFlatChunkGenerator(World world, long seed) {
		super(world, seed);
		this.surface = new OverworldWorldSource(world, seed);
	}

	private final OverworldWorldSource surface;

	@Override
	public void decorate(WorldSource worldSource, int chunkX, int chunkZ) {
		this.surface.decorate(worldSource, chunkX, chunkZ);
	}

	@Override
	protected void shapeChunk(int chunkX, int chunkZ, byte[] blocks, Biome[] biomes) {
		for (int localX = 0; localX < 16; ++localX) {
			for (int localZ = 0; localZ < 16; ++localZ) {
				int height = 10;

				for (int y = height; y >= 0; --y) {
					blocks[getIndex(localX, y, localZ)] = (byte) Block.STONE.id;
				}
			}
		}
	}

	@Override
	protected void buildSurface(int chunkX, int chunkZ, byte[] blocks, Biome[] biomes) {
		this.surface.buildSurface(chunkX, chunkZ, blocks, biomes);
	}

	@Override
	public int getMinSpawnY() {
		return 9;
	}

	@Override
	public boolean isValidSpawnPos(int x, int z) {
		int surfaceTile = this.world.getSurfaceBlockId(x, z);
		return surfaceTile == Block.GRASS.id;
	}
}
