/*
 * This software is provided under the terms of the Minecraft Forge Public License v1.0.
 */

package net.minecraft.src.forge;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

@Deprecated
public interface ICraftingHandler {

    /**
     * Called after an item is taken from crafting.
     */
    void onTakenFromCrafting(PlayerEntity player, ItemStack ist, Inventory craftMatrix);
}
