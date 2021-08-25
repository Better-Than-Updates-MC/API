package com.halotroop.fabric.mixin.terrain;

import java.util.concurrent.atomic.AtomicReference;

import com.halotroop.fabric.api.terrain.BiomeEvents;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.halotroop.fabric.api.event.ActionResult;

@Mixin(Biome.class)
public class BiomeMixin {
	@Inject(at = @At("HEAD"), method = "getClimateBiome", cancellable = true)
	private static void addModdedBiomeGen(float temperature, float humidity, CallbackInfoReturnable<Biome> info) {
		AtomicReference<Biome> biomeResultWrapper = new AtomicReference<>();
		ActionResult result = BiomeEvents.BiomePlacementCallback.EVENT.invoker().onClimaticBiomePlace(temperature, humidity, biomeResultWrapper::set);

		if (result == ActionResult.SUCCESS) {
			Biome biomeResult = biomeResultWrapper.get();

			if (biomeResult != null) {
				info.setReturnValue(biomeResult);
			}
		}
	}
}
