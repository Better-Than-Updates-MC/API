/*
 * This software is provided under the terms of the Minecraft Forge Public 
 * License v1.0.
 */

package net.minecraft.src.forge;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

@Deprecated
public interface IDestroyToolHandler {
	/** Called when the user's currently equipped item is destroyed.
	 */
	void onDestroyCurrentItem(PlayerEntity player, ItemStack orig);
}

