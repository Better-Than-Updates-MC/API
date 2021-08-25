package com.halotroop.fabric.test;

import net.fabricmc.api.ModInitializer;

public class WorldTypeTest implements ModInitializer {
	@Override
	public void onInitialize() {
		new RandomWorldType();
		new LowFlatWorldType();
	}
}
