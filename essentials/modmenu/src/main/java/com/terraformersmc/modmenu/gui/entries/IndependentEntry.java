package com.terraformersmc.modmenu.gui.entries;

import com.terraformersmc.modmenu.gui.ModListEntry;
import com.terraformersmc.modmenu.gui.ModListWidget;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.Minecraft;

public class IndependentEntry extends ModListEntry {

	public IndependentEntry(Minecraft mc, ModContainer container, ModListWidget list) {
		super(mc, container, list);
	}
}
