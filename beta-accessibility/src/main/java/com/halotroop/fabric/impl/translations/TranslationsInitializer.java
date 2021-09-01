package com.halotroop.fabric.impl.translations;

import com.halotroop.fabric.api.registry.Translations;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

import java.net.URL;

/**
 * @author mine_diver
 */
@Environment(EnvType.CLIENT)
public class TranslationsInitializer implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		FabricLoader.getInstance().getAllMods().forEach(modContainer -> {
			String modId = modContainer.getMetadata().getId();
			String path = "/assets/" + modId + "/lang";
			URL location = TranslationsInitializer.class.getResource(path);
			if (location != null) {
				Translations.addLanguageFolder(modId, path);
				System.out.printf("Registered lang location for %s.", modId);
			}
		});
	}
}
