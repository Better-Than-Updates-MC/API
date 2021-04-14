package io.github.minecraftcursedlegacy.api.terrain;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import io.github.minecraftcursedlegacy.api.registry.Id;
import net.minecraft.level.Level;
import net.minecraft.level.dimension.Dimension;
import net.minecraft.level.gen.BiomeSource;
import net.minecraft.level.source.LevelSource;
import net.minecraft.level.source.OverworldLevelSource;
import net.minecraft.util.io.CompoundTag;

/**
 * Indicates a world generator type that can be selected in the game.
 * @since 1.0.0
 */
public class WorldType {
	/**
	 * Create a new world type.
	 * @param id the id of the world type.
	 */
	public WorldType(Id id) {
		this(id, false);
	}

	/**
	 * Create a new world type.
	 * @param id the id of the world type.
	 * @param storeAdditionalData whether the world type should store additional nbt data.
	 */
	public WorldType(Id id, boolean storeAdditionalData) {
		this.id = id;
		REVERSE_LOOKUP.put(id, this);

		this.storeAdditionalData = storeAdditionalData;
		this.translationKey = "generator." + this.id.getNamespace() + "." + this.id.getName();
	}

	private final Id id;
	private final String translationKey;
	private final boolean storeAdditionalData;

	/**
	 * Creates the world type's overworld biome source for the given level.
	 * @param level the level.
	 * @param additionalData if this world type stores additional data, a tag to read and write such data from.
	 * @return an instance of {@linkplain BiomeSource} or one of its subclassses, for placing biomes in the world.
	 */
	public BiomeSource createBiomeSource(Level level, @Nullable CompoundTag additionalData) {
		return new BiomeSource(level);
	}

	/**
	 * Creates the world type's overworld chunk gemerator for the given level.
	 * @param level the level.
	 * @return an instance of {@linkplain LevelSource} or one of its subclassses (modded ones will typically be an instance of {@linkplain ChunkGenerator}), for generating the shape and decorations of the world.
	 */
	public LevelSource createChunkGenerator(Level level, @Nullable CompoundTag additionalData) {
		return new OverworldLevelSource(level, level.getSeed());
	}

	/**
	 * Retrieves the id of this world type.
	 * @return the id of this world type.
	 */
	public Id getId() {
		return this.id;
	}

	/**
	 * Create a compound tag of additional stored data to be used in functions of this world type.
	 * @return a compound tag of additional data, if additional data should be written. Otherwise, returns null.
	 */
	@Nullable
	public CompoundTag writeAdditionalData(Dimension dimension, @Nullable CompoundTag previousData) {
		return null;
	}

	/**
	 * Returns the cached translation key for this world type.
	 * @return the translation key for this world type.
	 */
	@Override
	public String toString() {
		return this.translationKey;
	}

	/**
	 * Gets the {@linkplain WorldType world type} for the given id.
	 * @param id the id of the world type.
	 * @return the world type associated with this id, or the default world type otherwise.
	 */
	public static WorldType getById(Id id) {
		return REVERSE_LOOKUP.getOrDefault(id, DEFAULT);
	}

	private static final Map<Id, WorldType> REVERSE_LOOKUP = new HashMap<>();

	/**
	 * The default world type with the id minecraft:default.
	 */
	public static final WorldType DEFAULT = new WorldType(new Id("default"));
}
