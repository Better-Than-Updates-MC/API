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

package com.halotroop.fabric.mixin.attacheddata;

import java.util.HashMap;
import java.util.Map;

import com.halotroop.fabric.api.registry.Identifier;
import net.minecraft.world.WorldProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.halotroop.fabric.api.attacheddata.v1.AttachedData;
import com.halotroop.fabric.api.attacheddata.v1.DataManager;
import com.halotroop.fabric.api.attacheddata.v1.SimpleDataStorage;
import net.minecraft.util.io.CompoundTag;

/**
 * Mixin to add attached data to world properties.
 * @since 1.0.0
 */
@Mixin(WorldProperties.class)
public class WorldPropertiesMixin implements SimpleDataStorage {
	private final Map<Identifier, AttachedData> api_attachedDataMap = new HashMap<>();

	@Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/util/io/CompoundTag;)V")
	private void api_onLoadProperties(CompoundTag tag, CallbackInfo info) {
		if (tag.containsKey("moddedData")) {
			CompoundTag data = tag.getCompoundTag("moddedData");

			// Load Data
			DataManager.WORLD_PROPERTIES.loadData((WorldProperties) (Object) this, data);
		}
	}

	@Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/world/WorldProperties;)V")
	private void api_onCopyProperties(WorldProperties from, CallbackInfo info) {
		DataManager.WORLD_PROPERTIES.copyData(from, (WorldProperties) (Object) this);
	}

	@Inject(at = @At("RETURN"), method = "updateProperties")
	private void updateProperties(CompoundTag writeTo, CompoundTag playerData, CallbackInfo info) {
		writeTo.put("moddedData", this.getModdedTag());
	}

	@Override
	public Map<Identifier, AttachedData> getRawAttachedDataMap() {
		return this.api_attachedDataMap;
	}
}
