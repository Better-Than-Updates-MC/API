package com.terraformersmc.modmenu.util;

import com.terraformersmc.modmenu.gui.ModListScreen;
import net.minecraft.client.gui.screen.Screen;

public interface ModMenuApiMarker {
	static Screen createModsScreen(Screen parent) {
		return new ModListScreen(parent);
	}
}
