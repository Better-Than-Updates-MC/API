package com.halotroop.fabric.test;

import com.halotroop.fabric.api.registry.Identifier;
import com.halotroop.fabric.api.registry.Translations;
import com.halotroop.fabric.api.worldtype.WorldType;
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
