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

package io.github.minecraftcursedlegacy.api.registry;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import io.github.minecraftcursedlegacy.accessor.translations.TranslationStorageAccessor;
import net.minecraft.achievement.Achievement;
import net.minecraft.block.Block;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.item.Item;

/**
 * Utilities for adding translations for things.
 *
 * @see I18n Accessing the translations
 * @since 0.4.1
 *
 * @author Chocohead
 */
public final class Translations {
	private Translations() {
	}

	/**
	 * Add a translation for the given {@link Block}.
	 *
	 * @param block The block to add the translation for
	 * @param translation The translated name for the block
	 *
	 * @throws IllegalArgumentException If the given block has not had {@link Block#setTranslationKey(String)}} called
	 */
	public static void addBlockTranslation(Block block, String translation) {
		if (block.getTranslationKey() == null) throw new IllegalArgumentException("Given block doesn't have a name: " + block);
		addTranslation(block.getTranslationKey().concat(".name"), translation);
	}

	/**
	 * Add translation for the given {@link Item}.
	 * 
	 * @param item The item to add the translation for
	 * @param translation The translated name for the item
	 *
	 * @throws IllegalArgumentException If the given item has not had {@link Item#setTranslationKey(String)}} called
	 */
	public static void addItemTranslation(Item item, String translation) {
		if (item.getTranslationKey() == null) throw new IllegalArgumentException("Given item doesn't have a name: " + item);
		addTranslation(item.getTranslationKey().concat(".name"), translation);
	}

	/**
	 * Add a translation for an {@link Achievement} with the given name.
	 *
	 * <p>Make sure to call this <i>before</i> the Achievement is constructed!
	 *
	 * @param key The name of the achievement as given to the constructor 
	 * @param translation The translated name for the achievement
	 *
	 * @see #addAchievementDescriptionTranslation(String, String)
	 */
	public static void addAchievementTranslation(String key, String translation) {
		addTranslation("achievement.".concat(key), translation);
	}

	/**
	 * Add a translation for an {@link Achievement}'s description with the given name.
	 *
	 * <p>Make sure to call this <i>before</i> the Achievement is constructed!
	 *
	 * @param key The name of the achievement as given to the constructor 
	 * @param translation The translated name for the achievement's description
	 *
	 * @see #addAchievementTranslation(String, String)
	 */
	public static void addAchievementDescriptionTranslation(String key, String translation) {
		addTranslation("achievement." + key + ".desc", translation);
	}

	/**
	 * Add a translation for the given key.
	 *
	 * @param key The raw translation key
	 * @param translation The translated text for the key
	 */
	public static void addTranslation(String key, String translation) {
		((TranslationStorageAccessor) TranslationStorage.getInstance()).getTranslations().put(key, translation);
	}

	/**
	 * Add all the translations from the given lang file.
	 *
	 * @param file The location of the lang file to load translations from
	 *
	 * @throws IOException If there is an error reading the file
	 */
	public static void loadLangFile(String file) throws IOException {
		try (InputStream in = Translations.class.getResourceAsStream(file)) {
			((TranslationStorageAccessor) TranslationStorage.getInstance()).getTranslations().load(in);
		}
	}

	/**
	 * Add all the translations from the given {@link Reader}.
	 *
	 * @param reader A reader containing translations to read from, not closed after use
	 *
	 * @throws IOException If there is an error reading the translations
	 */
	public static void loadLangFile(Reader reader) throws IOException {
		((TranslationStorageAccessor) TranslationStorage.getInstance()).getTranslations().load(reader);
	}
}