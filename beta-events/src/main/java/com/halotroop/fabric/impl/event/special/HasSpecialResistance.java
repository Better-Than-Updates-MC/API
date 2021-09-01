package com.halotroop.fabric.impl.event.special;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * This interface is to be implemented by block classes. It will allow a block
 * to control how it resists to explosion
 *
 * @see net.minecraft.block.Block
 */
public interface HasSpecialResistance {
	/**
	 * @return the explosion resistance of the block
	 * located at position x, y, z,
	 * from an exploder explosion on sourceX, sourceY, sourceZ.
	 */
	float getSpecialExplosionResistance(World world, int x, int y, int z,
	                                    double sourceX, double sourceY, double sourceZ,
	                                    Entity exploder);
}
