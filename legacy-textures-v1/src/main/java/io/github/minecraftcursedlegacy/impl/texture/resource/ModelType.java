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

package io.github.minecraftcursedlegacy.impl.texture.resource;

import java.util.HashMap;

import io.github.minecraftcursedlegacy.api.registry.Identifier;
import io.github.minecraftcursedlegacy.api.registry.Registries;
import io.github.minecraftcursedlegacy.impl.registry.HasParentId;
import net.minecraft.block.Block;

public enum ModelType {
	BLOCK("block") {
		@Override
		public ModelJson createDefaultModel(Identifier id) {
			ModelJson result = new ModelJson();

			result.parent = "block/cube_all";
			result.textures = new HashMap<>();
			result.textures.put("all", this.createLocation(id));
			result.root = ResourceLoader.getSetup(new Identifier(result.parent));

			if (result.root == null) {
				throw new IllegalStateException("Could not get built in setup for \"" + result.parent + "\"!");
			}

			return result;
		}
	},
	ITEM("item") {
		@Override
		public ModelJson createDefaultModel(Identifier id) {
			ModelJson result = new ModelJson();

			result.parent = "item/generated";
			result.textures = new HashMap<>();
			result.textures.put("", this.createLocation(id));
			result.root = ResourceLoader.getSetup(new Identifier(result.parent));

			if (result.root == null) {
				throw new IllegalStateException("Could not get built in setup for \"" + result.parent + "\"!");
			}

			return result;
		}
	},
	BLOCK_ITEM("item") {
		@Override
		public ModelJson createDefaultModel(Identifier id) {
			ModelJson result = new ModelJson();

			// substitute parent id for block items
			// Step 1: Get the Item, guaranteed to be a block item of some sort
			// Step 2: Get the integer id, and look up the block it is associated with
			// Step 3: Get the Identifier for that Tile
			id = Registries.BLOCK.getId(
				Block.BY_ID[
					((HasParentId) Registries.ITEM.getById(id)).getParentId()
				]
			);

			Identifier parentId = new Identifier(id.getNamespace(), "block/" + id.getName());
			result.parent = parentId.toString();
			ModelJson parent = ResourceLoader.getModelDirect(parentId);
			result.root = parent.root;
			result.textures = parent.textures;

			if (result.root == null) {
				throw new IllegalStateException("Could not get built in setup for \"" + result.parent + "\"!");
			}

			return result;
		}
	};

	ModelType(String location) {
		this.location = location;
	}

	private final String location;

	public String getLocation() {
		return this.location;
	}

	protected String createLocation(Identifier id) {
		return id.getNamespace() + ":" + this.getLocation() + "/" + id.getName();
	}

	public abstract ModelJson createDefaultModel(Identifier id);
}
