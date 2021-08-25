package io.github.minecraftcursedlegacy.api.terrain;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.ProgressListener;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.source.WorldSource;

/**
 * A world source that represents a simple chunk generator template for a world type or dimension.
 * @since 1.0.0
 */
public abstract class ChunkGenerator implements WorldSource {

	protected final World world;
	protected final Random rand;
	private Biome[] biomeCache;
	public ChunkGenerator(World world, long seed) {
		this.world = world;
		this.rand = new Random(seed);
	}

	protected int getIndex(int localX, int y, int localZ) {
		return (localX * 16 + localZ) * 128 + y;
	}

	/**
	 * Creates the base shape of the chunk.
	 * @param chunkX the chunk X position.
	 * @param chunkZ the chunk Z position.
	 * @param blocks the array of blocks in the chunk. The index from the local x, y, z positions is specified by {@linkplain #getIndex(int, int, int)}.
	 * @param biomes the array of biomes in the chunk. Only requires an x,z index equal to {@code localX * 16 + localZ}.
	 */
	protected abstract void shapeChunk(int chunkX, int chunkZ, byte[] blocks, Biome[] biomes);

	/**
	 * Takes the base shape of the chunk and populates it with specific terrain blocks. For example, adding a bedrock floor, and setting the top to use dirt and grass.
	 * @param chunkX the chunk X position.
	 * @param chunkZ the chunk Z position.
	 * @param blocks the array of blocks in the chunk. The index from the local x, y, z positions is specified by {@linkplain #getIndex(int, int, int)}.
	 * @param biomes the array of biomes in the chunk. Only requires an x,z index equal to {@code localX * 16 + localZ}.
	 */
	protected abstract void buildSurface(int chunkX, int chunkZ, byte[] blocks, Biome[] biomes);

	/**
	 * Takes the shape of the chunk after shaping the chunk and building the surface, and generates carvers, such as {@linkplain net.minecraft.world.gen.OverworldCarver caves and ravines}.
	 * @param chunkX the chunk X position.
	 * @param chunkZ the chunk Z position.
	 * @param blocks the array of blocks in the chunk. The index from the local x, y, z positions is specified by {@linkplain #getIndex(int, int, int)}.
	 * @param biomes the array of biomes in the chunk. Only requires an x,z index equal to {@code localX * 16 + localZ}.
	 */
	protected void generateCarvers(int chunkX, int chunkZ, byte[] blocks, Biome[] biomes) {
	}

	/**
	 * Whether the given x/z spawn coordinates are a valid player spawn position.
	 * @param x the block x position to spawn the player at.
	 * @param z the block z position to spawn the player at.
	 * @return whether the player is allowed to spawn here.
	 * @since 1.0.4
	 */
	public boolean isValidSpawnPos(int x, int z) {
		int surfaceBlock = this.world.getSurfaceBlockId(x, z);
		return surfaceBlock == Block.SAND.id;
	}

	/**
	 * @return the minimum y value of the block on which the player can spawn naturally.
	 * @since 1.0.5
	 */
	public int getMinSpawnY() {
		return 63;
	}

	@Override
	public Chunk loadChunk(int x, int z) {
		return this.getChunk(x, z);
	}

	@Override
	public Chunk getChunk(int x, int z) {
		// Setup
		this.rand.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
		byte[] blocks = new byte[128 * 16 * 16]; // HEIGHT * WIDTH * LENGTH
		Chunk result = new Chunk(this.world, blocks, x, z);

		// Biome Gen
		this.biomeCache = this.world.getBiomeSource().getBiomes(this.biomeCache, x * 16, z * 16, 16, 16);

		// Base Chunk Gen
		this.shapeChunk(x, z, blocks, this.biomeCache);
		this.buildSurface(x, z, blocks, this.biomeCache);
		this.generateCarvers(x, z, blocks, this.biomeCache);

		// Finish
		result.generateHeightmap();
		return result;
	}

	// THE FOLLOWING METHODS ARE FOR CHUNK CACHES, AND DO NOT REQUIRE LOGIC IN CHUNK GENERATORS.

	/**
	 * In a chunk generator, this always returns true, as this method is only required for world sources which load and cache chunks (i.e. Chunk Caches).
	 */
	@Override
	public final boolean isChunkLoaded(int chunkX, int chunkZ) {
		return true;
	}

	@Override
	public final boolean saveChunks(boolean flag, ProgressListener arg) {
		return true;
	}

	@Override
	public final boolean unloadOldestChunks() {
		return false;
	}

	@Override
	public final boolean isClean() {
		return true;
	}
}
