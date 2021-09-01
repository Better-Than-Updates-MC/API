/*
 * Copyright (c) 2020 The Cursed Legacy Team.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.halotroop.fabric.impl.worldtype;

import java.util.ArrayList;
import java.util.List;

import com.halotroop.fabric.api.attacheddata.v1.DataManager;
import com.halotroop.fabric.api.attacheddata.v1.DataManager.DataKey;
import com.halotroop.fabric.api.registry.Translations;
import com.halotroop.fabric.api.worldtype.WorldType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class WorldTypeImpl implements ModInitializer, ClientModInitializer {
	@Override
	public void onInitialize() {
		// Create world type attached data
		worldTypeData = DataManager.WORLD_PROPERTIES.addAttachedData(WorldTypeData.ID, properties -> new WorldTypeData(getSelected().getId()));
	}

	@Override
	public void onInitializeClient() {
		// Translate default
		Translations.addTranslation(WorldType.DEFAULT.toString(), "Default");
	}

	public static DataKey<WorldTypeData> worldTypeData;
	private static final List<WorldType> TYPES = new ArrayList<>();
	private static int selected;
	
	public static void add(WorldType type) {
		TYPES.add(type);
	}

	public static WorldType cycle() {
		if (++selected >= TYPES.size()) {
			selected = 0;
		}

		return TYPES.get(selected);
	}
	
	public static WorldType getSelected() {
		return TYPES.get(selected);
	}

	public static void setSelected(WorldType type) {
		selected = TYPES.indexOf(type);
	}

	public static boolean hasModdedTypes() {
		return TYPES.size() > 1;
	}
}
