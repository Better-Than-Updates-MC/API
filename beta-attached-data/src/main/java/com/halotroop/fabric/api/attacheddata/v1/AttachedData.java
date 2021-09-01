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

package com.halotroop.fabric.api.attacheddata.v1;

import com.halotroop.fabric.api.registry.Identifier;
import net.minecraft.util.io.CompoundTag;

/**
 * Data which can be attached to various vanilla objects, such as items and worlds.
 * @see {@link DataManager}.
 * @since 1.0.0
 */
public interface AttachedData {
	/**
	 * @return the id of this modded data.
	 */
	Identifier getId();
	/**
	 * @return a tag representation of this data.
	 */
	CompoundTag writeNBT(CompoundTag tag);
	/**
	 * @param tag the tag from which to load data.
	 */
	void readNBT(CompoundTag tag);
	/**
	 * Creates a deep copy of this {@link AttachedData}, similar to the recommendations for {@link Object#clone}.
	 */
	AttachedData copy();
}
