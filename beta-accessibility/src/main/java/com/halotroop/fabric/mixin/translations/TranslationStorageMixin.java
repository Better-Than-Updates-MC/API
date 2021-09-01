package com.halotroop.fabric.mixin.translations;

import com.halotroop.fabric.api.registry.Translations;
import net.minecraft.client.resource.language.TranslationStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Locale;

@Mixin(TranslationStorage.class)
public class TranslationStorageMixin {
	@Shadow
	private static TranslationStorage instance;

	@Inject(method = "<init>", at = @At("RETURN"))
	private void afterLangLoaded(CallbackInfo ci) {
		instance = (TranslationStorage) (Object) this;
		Translations.changeLanguage(Locale.getDefault(Locale.Category.DISPLAY).toLanguageTag());
		instance = null;
	}
}
