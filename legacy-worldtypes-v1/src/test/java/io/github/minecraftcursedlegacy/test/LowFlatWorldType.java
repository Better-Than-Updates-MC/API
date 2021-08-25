package io.github.minecraftcursedlegacy.test;

import io.github.minecraftcursedlegacy.api.registry.Identifier;
import io.github.minecraftcursedlegacy.api.registry.Translations;
import io.github.minecraftcursedlegacy.api.worldtype.WorldType;
import net.minecraft.world.World;
import net.minecraft.world.source.WorldSource;
import net.minecraft.util.io.CompoundTag;

public class LowFlatWorldType extends WorldType {
	public LowFlatWorldType() {
		super(new Identifier("modid", "lowflat"));
		Translations.addTranslation(this.toString(), "Low Flat");
	}

	@Override
	public WorldSource createChunkGenerator(World world, CompoundTag additionalData) {
		return new LowFlatChunkGenerator(world, world.getSeed());
	}
}
