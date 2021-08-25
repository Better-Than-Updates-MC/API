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

package com.halotroop.fabric.impl.texture;

import java.awt.image.BufferedImage;

import com.halotroop.fabric.api.registry.Identifier;
import com.halotroop.fabric.api.registry.Registries;
import com.halotroop.fabric.impl.texture.resource.ModelJson;
import com.halotroop.fabric.impl.texture.resource.ModelType;
import com.halotroop.fabric.impl.texture.resource.ResourceLoader;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.BlockItem;
import net.minecraft.block.Block;
import paulevs.corelib.model.prefab.FullCubeModel;
import paulevs.corelib.model.prefab.GrasslikeModel;
import paulevs.corelib.registry.ModelRegistry;

public class ModelDiscoverer implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ResourceLoader.addModelSetup(new Identifier("item/generated"), (id, obj, data) -> {
			BufferedImage image = ResourceLoader.getTexture(new Identifier(data.textures.get("")));

			if (image != null) {
				Item item = (Item) obj;
				item.setTexturePosition(AtlasMapper.registerDefaultSprite(item.id, image));
			}
		});

		ResourceLoader.addModelSetup(new Identifier("block/cube_all"), (id, obj, data) -> {
			String image = getValidatedTextureLocation(data.textures.get("all"));

			if (image != null) {
				// I don't know why. I don't want to know why (well - actually I do, because wtf)
				// But corelib does not load textures *at all* without a slash
				// before every texture location specifier
				if (!image.startsWith("/")) image = "/" + image;

				if (obj instanceof Item) {
					Item item = (Item) obj;
					ModelRegistry.addItemModel(item, new FullCubeModel(image));
				} else {
					Block block = (Block) obj;
					ModelRegistry.addBlockModel(block, new FullCubeModel(image));
				}
			}
		});

		ResourceLoader.addModelSetup(new Identifier("block/cross"), (id, obj, data) -> {
			String image = getValidatedTextureLocation(data.textures.get("cross"));

			if (image != null) {
				image = "/" + image;

				if (obj instanceof Item) {
					Item item = (Item) obj;
					ModelRegistry.addItemModel(item, new CrossModel(image));
				} else {
					Block block = (Block) obj;
					ModelRegistry.addBlockModel(block, new CrossModel(image));
				}
			}
		});

		ResourceLoader.addModelSetup(new Identifier("block/cube_bottom_top"), (id, obj, data) -> {
			String top = getValidatedTextureLocation(data.textures.get("top"));
			String side = getValidatedTextureLocation(data.textures.get("side"));
			String bottom = getValidatedTextureLocation(data.textures.get("bottom"));

			if (top != null && side != null && bottom != null) {
				top = "/" + top;
				side = "/" + side;
				bottom = "/" + bottom;

				if (obj instanceof Item) {
					Item item = (Item) obj;
					ModelRegistry.addItemModel(item, new GrasslikeModel(top, side, bottom));
				} else {
					Block block = (Block) obj;
					ModelRegistry.addBlockModel(block, new GrasslikeModel(top, side, bottom));
				}
			}
		});

		Registries.BLOCK.forEach((id, block) -> {
			if (id == null) throw new IllegalArgumentException(block.getTranslatedName());
			ModelJson model = ResourceLoader.getModel(id, ModelType.BLOCK);
			model.root.setupModel(id, block, model);
		});

		Registries.ITEM.forEach((id, item) -> {
			if (id == null) throw new IllegalArgumentException(item.getTranslatedName());
			ModelJson model = ResourceLoader.getModel(id, (item instanceof BlockItem) ? ModelType.BLOCK_ITEM : ModelType.ITEM);
			model.root.setupModel(id, item, model);
		});
	}

	private static String getValidatedTextureLocation(String provided) {
		return provided == null ? null : ResourceLoader.getValidatedTextureLocation(new Identifier(provided));
	}
}
