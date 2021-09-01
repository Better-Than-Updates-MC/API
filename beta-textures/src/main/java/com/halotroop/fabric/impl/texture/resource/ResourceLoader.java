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

package com.halotroop.fabric.impl.texture.resource;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.halotroop.fabric.api.registry.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import javax.imageio.ImageIO;

import com.google.gson.Gson;

/**
 * Resource Loader for models and textures.
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class ResourceLoader {
	/**
	 * Retrieves the resource specified as a JModel.
	 * @param id the resource id, including type.
	 * @param type the sub-file type.
	 * @return the loaded model object.
	 */
	public static ModelJson getModel(@NotNull Identifier id, ModelType type) {
		Identifier modelId = new Identifier(id.getNamespace(), type.getLocation() + "/" + id.getName());
		ModelJson prelim = getModel(modelId);

		if (prelim == null) {
			MODELS.put(modelId, prelim = type.createDefaultModel(id));
		}

		return prelim;
	}

	/**
	 * Retrieves the resource specified as a JModel.
	 * @param id the resource id.
	 * @return the loaded model object.
	 */
	@Nullable
	private static ModelJson getModel(Identifier id) {
		return MODELS.computeIfAbsent(id, id_ -> {
			InputStream stream = getStream(id_, "models", ".json");
			ModelJson result = stream == null ? null : GSON.fromJson( // load from json
					new InputStreamReader(stream),
					ModelJson.class);

			if (result != null) { // if not null, resolve parent stuff
				// recursive initialisation!
				Identifier next = new Identifier(result.parent);
				ModelSetup setup = SETUPS.get(next); // get the model setup to check if it's a root java impl or an intermediary step

				if (setup == null) { // if intermediary, we do ye olde recursive function
					ModelJson parent = getModel(next);

					// fill in parent values where not set
					for (Map.Entry<String, String> entry : Objects.requireNonNull(parent, "Parent mustn't be null.").textures.entrySet()) {
						result.textures.putIfAbsent(entry.getKey(), entry.getValue());
					}

					// set model setup
					result.root = parent.root;
				} else {
					// done!
					result.root = setup;
				}

				if (result.root == null) {
					throw new IllegalStateException("Could not get built in setup for \"" + result.parent + "\"!");
				}
			}

			return result;
		});
	}

	@Nullable
	static ModelJson getModelDirect(Identifier id) {
		return MODELS.get(id);
	}

	/**
	 * Retrieves the resource specified as a BufferedImage, caching results.
	 * @param id the resource id.
	 * @return the respective buffered image.
	 */
	@Nullable
	public static BufferedImage getTexture(Identifier id) {
		return TEXTURES.computeIfAbsent(id, id_ -> {
			try {
				URL url = getURL(id_, "textures", ".png");
				return url == null ? null : ImageIO.read(url);
			} catch (IOException e) {
				throw new UncheckedIOException("Execption loading texture " + id_, e);
			}
		});
	}

	@Nullable
	public static String getValidatedTextureLocation(Identifier provided) {
		String resourceLocation = getResourceLocation(provided, "textures", ".png");

		// validate
		if (ResourceLoader.class.getClassLoader().getResource(resourceLocation) == null) {
			return null;
		}

		return resourceLocation;
	}

	@SuppressWarnings("UnusedReturnValue")
	public static ModelSetup addModelSetup(Identifier id, ModelSetup setup) {
		SETUPS.put(id, setup);
		return setup;
	}

	@Nullable
	static ModelSetup getSetup(Identifier id) {
		return SETUPS.get(id);
	}

	@Nullable
	@SuppressWarnings("SameParameterValue")
	private static InputStream getStream(Identifier id, String locator, String extension) {
		return ResourceLoader.class.getClassLoader().getResourceAsStream(getResourceLocation(id, locator, extension));
	}

	@Nullable
	@SuppressWarnings("SameParameterValue")
	private static URL getURL(Identifier id, String locator, String extension) {
		String s = getResourceLocation(id, locator, extension);
		return ResourceLoader.class.getClassLoader().getResource(s);
	}

	private static String getResourceLocation(Identifier id, String locator, String extension) {
		return "assets/" + id.getNamespace() + "/" + locator + "/" + id.getName() + extension;
	}

	private static final Map<Identifier, ModelSetup> SETUPS = new HashMap<>();
	private static final Map<Identifier, ModelJson> MODELS = new HashMap<>();
	private static final Map<Identifier, BufferedImage> TEXTURES = new HashMap<>();
	private static final Gson GSON = new Gson();
}
