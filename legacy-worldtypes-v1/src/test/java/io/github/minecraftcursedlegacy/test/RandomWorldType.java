package io.github.minecraftcursedlegacy.test;

import io.github.minecraftcursedlegacy.api.registry.Identifier;
import io.github.minecraftcursedlegacy.api.registry.Translations;
import io.github.minecraftcursedlegacy.api.worldtype.WorldType;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.world.World;
import net.minecraft.world.source.WorldSource;

public class RandomWorldType extends WorldType {
	public RandomWorldType() {
		super(new Identifier("modid", "random"));
		Translations.addTranslation(this.toString(), "Test");
	}

	@Override
	public WorldSource createChunkGenerator(World world, CompoundTag additionalData) {
		return new RandomChunkGenerator(world, world.getSeed());
	}
}
