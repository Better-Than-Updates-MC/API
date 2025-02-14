/*
 * This software is provided under the terms of the Minecraft Forge Public License v1.0.
 */

package forge;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Deprecated
public interface IBucketHandler {

    /**
     * This is called before Minecraft tries to fill a bucket with water or
     * lava. If it returns a non-null result, then the filling process will
     * be stopped and the empty bucket will be changed to the result of this
     * subprogram.
     * 
     * @see MinecraftForge#registerCustomBucketHander(IBucketHandler)
     */
    ItemStack fillCustomBucket(World w, int i, int j, int k);

}
