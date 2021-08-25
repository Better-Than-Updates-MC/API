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

package com.halotroop.fabric.mixin.terrain;

import com.halotroop.fabric.impl.terrain.InternalWorldSourceAccess;
import com.halotroop.fabric.api.terrain.ChunkGenerator;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.WorldProperties;
import net.minecraft.world.source.WorldSource;

@Mixin(World.class)
public class WorldMixin {
	@ModifyConstant(constant = @Constant(intValue = 63), method = "getSurfaceBlockId")
	public int alterMinY(int original) {
		World self = (World) (Object) this;
		WorldSource cg = ((InternalWorldSourceAccess) self.dimension).getInternalWorldSource();

		if (cg instanceof ChunkGenerator) {
			return ((ChunkGenerator) cg).getMinSpawnY();
		}

		return original;
	}

	@Inject(at = @At("RETURN"), method = "initSpawnPoint")
	private void recomputeSpawnY(CallbackInfo info) {
		World self = (World) (Object) this;
		WorldSource cg = ((InternalWorldSourceAccess) self.dimension).getInternalWorldSource();

		if (cg instanceof ChunkGenerator) {
			WorldProperties properties = self.getProperties();
			properties.setSpawnPosition(properties.getSpawnX(), ((ChunkGenerator) cg).getMinSpawnY() + 1, properties.getSpawnY());
		}
	}
}
