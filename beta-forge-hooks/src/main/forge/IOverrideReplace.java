/**
 * This software is provided under the terms of the Minecraft Forge Public 
 * License v1.0.
 */

package forge;

import net.minecraft.src.Block;
import net.minecraft.src.World;

/**
 * This interface is to be implemented by block classes. It will allow a block
 * to control how it can be replaced
 *
 * @see Block
 */
public interface IOverrideReplace {
    
    /**
     * Return true if this block has to take control over replacement, for
     * the intended replacement given by the parameter bid. If false, then
     * the block replacement will be prevented.
     */
	public boolean canReplaceBlock(World world, int i, int j, int k, int bid);
	
	/**
	 * Return the status of the actual replacement. 
	 */
	public boolean getReplacedSuccess();
}
