/*
 * This software is provided under the terms of the Minecraft Forge Public License v1.0.
 */

package forge;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;

/**
 * This interface is to be implemented by ItemArmor classes.
 * It will allow modifying computation of damage and health loss.
 * Computation will be called before the actual armor computation,
 * which can then be cancelled.
 *
 * @see ArmorItem
 */
@Deprecated
public interface ISpecialArmor {
    /**
     * @return extra properties for the armor.
     */
	ArmorProperties getProperties(PlayerEntity player, int initialDamage, int currentDamage);
}
