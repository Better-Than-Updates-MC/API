/*
 * This software is provided under the terms of the Minecraft Forge Public License v1.0.
 */

package forge;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SleepStatus;
import net.minecraft.world.World;

@Deprecated
public interface ISleepHandler {
    /**
     * This is called before a player sleeps in a bed.  If it returns a
     * non-null result, then the normal sleeping process will be skipped, and
     * the value returned by this method will be returned to
     * {@link net.minecraft.block.BedBlock#activate(World, int, int, int, PlayerEntity)}.
     *
     * @see MinecraftForge#registerSleepHandler(ISleepHandler)
     */
    SleepStatus sleepInBedAt(PlayerEntity player, int x, int y, int z);
}

