/*
 * This software is provided under the terms of the Minecraft Forge Public License v1.0.
 */

package forge;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.world.World;

/**
 * This interface is to be implemented by item classes. It will allow an item
 * to perform a use before the block is activated.
 *
 * @see Item
 */
@Deprecated
public interface IUseItemFirst {
    
    /**
     * This is called when the item is used, before the block is activated.
     * Stop the computation when return true.
     */
	boolean onItemUseFirst(ItemStack ist, PlayerEntity player, World world, int i, int j, int k, int l);
}
