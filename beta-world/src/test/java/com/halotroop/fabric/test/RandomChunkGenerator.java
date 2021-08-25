package com.halotroop.fabric.test;

import com.halotroop.fabric.api.terrain.ChunkGenerator;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.source.OverworldWorldSource;
import net.minecraft.world.source.WorldSource;

public class RandomChunkGenerator extends ChunkGenerator {
	public RandomChunkGenerator(World world, long seed) {
		super(world, seed);
		this.surface = new OverworldWorldSource(world, seed);
	}

	private final OverworldWorldSource surface;

	@Override
	public void decorate(WorldSource worldSource, int chunkX, int chunkZ) {
	}

	@Override
	protected void shapeChunk(int chunkX, int chunkZ, byte[] tiles, Biome[] biomes) {
		for (int localX = 0; localX < 16; ++localX) {
			for (int localZ = 0; localZ < 16; ++localZ) {
				int height = 61 + this.rand.nextInt(6) + this.rand.nextInt(3) + ((chunkX + chunkZ) & 0b11);

				if (height < 63) {
					for (int y = 63; y > height; --y) {
						tiles[getIndex(localX, y, localZ)] = (byte) Block.STILL_WATER.id;
					}
				}

				for (int y = height; y >= 0; --y) {
					tiles[getIndex(localX, y, localZ)] = (byte) Block.STONE.id;
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
		return 67;
	}

	@Override
	public boolean isValidSpawnPos(int x, int z) {
		int surfaceTile = this.world.getSurfaceBlockId(x, z);
		return surfaceTile == Block.GRASS.id;
	}
}
