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

package io.github.minecraftcursedlegacy.mixin.registry;

import io.github.minecraftcursedlegacy.accessor.registry.DimensionFileAccessor;
import io.github.minecraftcursedlegacy.impl.registry.sync.RegistryRemapper;
import net.minecraft.world.dimension.DimensionData;
import net.minecraft.world.dimension.DimensionFile;
import net.minecraft.world.storage.OldWorldStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;

@Mixin(OldWorldStorage.class)
public class OldWorldStorageMixin {
	@Inject(at = @At("RETURN"), method = "createDimensionFile")
	private void addRemapping(String string, boolean flag, CallbackInfoReturnable<DimensionData> info) {
		DimensionFile data = (DimensionFile) info.getReturnValue();
		RegistryRemapper.readAndWrite(new File(((DimensionFileAccessor) data).getParentFolder(), "fabric_registry.dat"), null);
	}
}
