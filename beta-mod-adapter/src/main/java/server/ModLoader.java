/*
 * The FML Forge Mod Loader suite. Copyright (C) 2012 cpw
 *
 * This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */
package net.minecraft.src;

import java.util.EnumSet;
import java.util.List;
import java.util.logging.Logger;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer.TickType;
import cpw.mods.fml.common.ReflectionHelper;
import cpw.mods.fml.common.modloader.ModLoaderHelper;
import cpw.mods.fml.common.modloader.ModLoaderModContainer;
import cpw.mods.fml.server.FMLServerHandler;

@Environment(EnvType.SERVER)
public class ModLoader {
    /**
     * Not used on the server.
     *
     * @param achievement
     * @param name
     * @param description
     */
    public static void addAchievementDesc(Achievement achievement, String name, String description)
    {
        
    }

    /**
     * This method is a call in hook from modified external code. Implemented elsewhere.
     *
     * {@link FMLServerHandler#fuelLookup(int, int)}
     * @param id
     * @param metadata
     * @return
     */
    @Deprecated
    public static int addAllFuel(int id, int metadata)
    {
        return 0;
    }

    /**
     * This method is unimplemented in server versions to date.
     *
     * @param armor
     * @return
     */
    @Deprecated
    public static int addArmor(String armor)
    {
        return 0;
    }

    /**
     * This method adds the supplied biome to the set of candidate biomes for the default world generator type.
     *
     * @param biome
     */
    public static void addBiome(BiomeGenBase biome)
    {
        FMLServerHandler.instance().addBiomeToDefaultWorldGenerator(biome);
    }

    /**
     * Unimplemented on the server as it does not generate names
     * @param key
     * @param value
     */
    @Deprecated
    public static void addLocalization(String key, String value)
    {
    }

    /**
     * Unimplemented on the server as it does not generate names
     * @param key
     * @param lang
     * @param value
     */
    @Deprecated
    public static void addLocalization(String key, String lang, String value)
    {
    }

    /**
     * Unimplemented on the server as it does not generate names
     * @param instance
     * @param name
     */
    @Deprecated
    public static void addName(Object instance, String name)
    {
    }

    /**
     * Unimplemented on the server as it does not generate names
     * @param instance
     * @param lang
     * @param name
     */
    @Deprecated
    public static void addName(Object instance, String lang, String name)
    {
    }

    /**
     * Unimplemented on the server as it does not render textures
     * @param fileToOverride
     * @param fileToAdd
     * @return
     */
    @Deprecated
    public static int addOverride(String fileToOverride, String fileToAdd)
    {
        return 0;
    }

    /**
     * Unimplemented on the server as it does not render textures
     * @param path
     * @param overlayPath
     * @param index
     */
    @Deprecated
    public static void addOverride(String path, String overlayPath, int index)
    {
    }

    /**
     * Add a Shaped Recipe
     * @param output
     * @param params
     */
    public static void addRecipe(ItemStack output, Object... params)
    {
        CommonRegistry.addRecipe(output, params);
    }

    /**
     * Add a shapeless recipe
     * @param output
     * @param params
     */
    public static void addShapelessRecipe(ItemStack output, Object... params)
    {
        CommonRegistry.addShapelessRecipe(output, params);
    }

    /**
     * Add a new product to be smelted
     * @param input
     * @param output
     */
    public static void addSmelting(int input, ItemStack output)
    {
        CommonRegistry.addSmelting(input, output);
    }

    /**
     * Add a mob to the spawn list
     * @param entityClass
     * @param weightedProb
     * @param min
     * @param max
     * @param spawnList
     */
    public static void addSpawn(Class <? extends EntityLiving > entityClass, int weightedProb, int min, int max, EnumCreatureType spawnList)
    {
        CommonRegistry.addSpawn(entityClass, weightedProb, min, max, spawnList, FMLServerHandler.instance().getDefaultOverworldBiomes());
    }

    /**
     * Add a mob to the spawn list
     * @param entityClass
     * @param weightedProb
     * @param min
     * @param max
     * @param spawnList
     * @param biomes
     */
    public static void addSpawn(Class <? extends EntityLiving > entityClass, int weightedProb, int min, int max, EnumCreatureType spawnList, BiomeGenBase... biomes)
    {
        CommonRegistry.addSpawn(entityClass, weightedProb, min, max, spawnList, biomes);
    }

    /**
     * Add a mob to the spawn list
     * @param entityName
     * @param weightedProb
     * @param min
     * @param max
     * @param spawnList
     */
    public static void addSpawn(String entityName, int weightedProb, int min, int max, EnumCreatureType spawnList)
    {
        CommonRegistry.addSpawn(entityName, weightedProb, min, max, spawnList, FMLServerHandler.instance().getDefaultOverworldBiomes());
    }

    /**
     * Add a mob to the spawn list
     * @param entityName
     * @param weightedProb
     * @param min
     * @param max
     * @param spawnList
     * @param biomes
     */
    public static void addSpawn(String entityName, int weightedProb, int min, int max, EnumCreatureType spawnList, BiomeGenBase... biomes)
    {
        CommonRegistry.addSpawn(entityName, weightedProb, min, max, spawnList, biomes);
    }

    /**
     * This method is a call in hook from modified external code. Implemented elsewhere.
     * {@link FMLServerHandler#tryDispensingEntity(World, double, double, double, byte, byte, ItemStack)}
     * @param world
     * @param x
     * @param y
     * @param z
     * @param xVel
     * @param zVel
     * @param item
     * @return
     */
    @Deprecated
    public static boolean dispenseEntity(World world, double x, double y, double z, int xVel, int zVel, ItemStack item)
    {
        return false;
    }

    /**
     * Remove a container and drop all the items in it on the ground around
     * @param world
     * @param x
     * @param y
     * @param z
     */
    public static void genericContainerRemoval(World world, int x, int y, int z)
    {
        TileEntity te = world.func_451_k(x, y, z);

        if (!(te instanceof IInventory))
        {
            return;
        }

        IInventory inv = (IInventory)te;

        for (int l = 0; l < inv.func_83_a(); l++)
        {
            ItemStack itemstack = inv.func_82_a(l);

            if (itemstack == null)
            {
                continue;
            }

            float f = world.field_803_m.nextFloat() * 0.8F + 0.1F;
            float f1 = world.field_803_m.nextFloat() * 0.8F + 0.1F;
            float f2 = world.field_803_m.nextFloat() * 0.8F + 0.1F;

            while (itemstack.field_853_a > 0)
            {
                int i1 = world.field_803_m.nextInt(21) + 10;

                if (i1 > itemstack.field_853_a)
                {
                    i1 = itemstack.field_853_a ;
                }

                itemstack.field_853_a  -= i1;
                EntityItem entityitem = new EntityItem(world, (float)te.field_478_b + f, (float)te.field_483_c + f1, (float)te.field_482_d + f2, new ItemStack(itemstack.field_855_c, i1, itemstack.func_21125_h()));
                float f3 = 0.05F;
                entityitem.field_319_o = (float)world.field_803_m.nextGaussian() * f3;
                entityitem.field_318_p = (float)world.field_803_m.nextGaussian() * f3 + 0.2F;
                entityitem.field_317_q = (float)world.field_803_m.nextGaussian() * f3;

                if (itemstack.func_40608_n())
                {
                    entityitem.field_429_a.func_40604_d((NBTTagCompound)itemstack.func_40607_o().func_40468_b());
                }

                world.func_526_a(entityitem);
            }
        }
    }

    /**
     * Get a list of all BaseMod loaded into the system
     * {@link ModLoaderModContainer#findAll}
     * @return
     */
    public static List<BaseMod> getLoadedMods()
    {
        return ModLoaderModContainer.findAll();
    }

    /**
     * Get a logger instance
     * {@link FMLServerHandler#getFMLLogger()}
     * @return
     */
    public static Logger getLogger()
    {
        return FMLCommonHandler.instance().getFMLLogger();
    }

    /**
     * Get a value from a field using reflection
     * {@link ReflectionHelper#getPrivateValue(Class, Object, int)}
     * @param instanceclass
     * @param instance
     * @param fieldindex
     * @return
     */
    public static <T, E> T getPrivateValue(Class <? super E > instanceclass, E instance, int fieldindex)
    {
        return ReflectionHelper.getPrivateValue(instanceclass, instance, fieldindex);
    }

    /**
     * Get a value from a field using reflection
     * {@link ReflectionHelper#getPrivateValue(Class, Object, String)}
     * @param instanceclass
     * @param instance
     * @param field
     * @return
     */
    public static <T, E> T getPrivateValue(Class <? super E > instanceclass, E instance, String field)
    {
        return ReflectionHelper.getPrivateValue(instanceclass, instance, field);
    }

    /**
     * Get a new unique entity id
     * {@link Entity#getNextId()}
     * @return
     */
    public static int getUniqueEntityId()
    {
        return FMLCommonHandler.instance().nextUniqueEntityListId();
    }

    /**
     * Is the named mod loaded?
     * {@link Loader#isModLoaded(String)}
     * @param modname
     * @return
     */
    public static boolean isModLoaded(String modname)
    {
        return Loader.isModLoaded(modname);
    }

    /**
     * This method is a call in hook from modified external code. Implemented elsewhere.
     * {@link FMLServerHandler#handlePacket250(Packet250CustomPayload, EntityPlayer)}
     * @param packet
     */
    @Deprecated
    public static void receivePacket(Packet250CustomPayload packet)
    {
    }

    /**
     * Register a new block
     * @param block
     */
    public static void registerBlock(Block block)
    {
        CommonRegistry.registerBlock(block);
    }

    /**
     * Register a new block
     * @param block
     * @param itemclass
     */
    public static void registerBlock(Block block, Class <? extends ItemBlock > itemclass)
    {
        CommonRegistry.registerBlock(block, itemclass);
    }

    /**
     * Register a new entity ID
     * @param entityClass
     * @param entityName
     * @param id
     */
    public static void registerEntityID(Class <? extends Entity > entityClass, String entityName, int id)
    {
        CommonRegistry.registerEntityID(entityClass, entityName, id);
    }

    /**
     * Register a new entity ID
     * @param entityClass
     * @param entityName
     * @param id
     * @param background
     * @param foreground
     */
    public static void registerEntityID(Class <? extends Entity > entityClass, String entityName, int id, int background, int foreground)
    {
        CommonRegistry.registerEntityID(entityClass, entityName, id, background, foreground);
    }

    /**
     * Register the mod for packets on this channel. This only registers the channel with Forge Mod Loader, not
     * with clients connecting- use BaseMod.onClientLogin to tell them about your custom channel
     * {@link FMLCommonHandler#registerChannel(cpw.mods.fml.common.ModContainer, String)}
     * @param mod
     * @param channel
     */
    public static void registerPacketChannel(BaseMod mod, String channel)
    {
        FMLCommonHandler.instance().registerChannel(ModLoaderModContainer.findContainerFor(mod), channel);
    }

    /**
     * Register a new tile entity class
     * @param tileEntityClass
     * @param id
     */
    public static void registerTileEntity(Class <? extends TileEntity > tileEntityClass, String id)
    {
        CommonRegistry.registerTileEntity(tileEntityClass, id);
    }

    /**
     * Remove a biome. This code will probably not work correctly and will likely corrupt worlds.
     * @param biome
     */
    @Deprecated
    public static void removeBiome(BiomeGenBase biome)
    {
        CommonRegistry.removeBiome(biome);
    }

    /**
     * Remove a spawn
     * @param entityClass
     * @param spawnList
     */
    public static void removeSpawn(Class <? extends EntityLiving > entityClass, EnumCreatureType spawnList)
    {
        CommonRegistry.removeSpawn(entityClass, spawnList, FMLServerHandler.instance().getDefaultOverworldBiomes());
    }

    /**
     * Remove a spawn
     * @param entityClass
     * @param spawnList
     * @param biomes
     */
    public static void removeSpawn(Class <? extends EntityLiving > entityClass, EnumCreatureType spawnList, BiomeGenBase... biomes)
    {
        CommonRegistry.removeSpawn(entityClass, spawnList, biomes);
    }

    /**
     * Remove a spawn
     * @param entityName
     * @param spawnList
     */
    public static void removeSpawn(String entityName, EnumCreatureType spawnList)
    {
        CommonRegistry.removeSpawn(entityName, spawnList, FMLServerHandler.instance().getDefaultOverworldBiomes());
    }

    /**
     * Remove a spawn
     * @param entityName
     * @param spawnList
     * @param biomes
     */
    public static void removeSpawn(String entityName, EnumCreatureType spawnList, BiomeGenBase... biomes)
    {
        CommonRegistry.removeSpawn(entityName, spawnList, biomes);
    }

    /**
     * Configuration is handled elsewhere
     * {@link ModLoaderModContainer}
     */
    @Deprecated
    public static void saveConfig()
    {
    }

    /**
     * This method is unimplemented on the server: it is meant for clients to send chat to the server
     * {@link FMLServerHandler#handleChatPacket(Packet3Chat, EntityPlayer)}
     * @param text
     */
    @Deprecated
    public static void serverChat(String text)
    {
    }

    /**
     * Indicate that you want to receive ticks
     *
     * @param mod
     *          receiving the events
     * @param enable
     *          indicates whether you want to recieve them or not
     * @param useClock
     *          Not used in server side: all ticks are sent on the server side (no render subticks)
     */
    public static void setInGameHook(BaseMod mod, boolean enable, boolean useClock)
    {
        ModLoaderHelper.updateStandardTicks(mod, enable, useClock);
    }

    /**
     * Set a private field to a value using reflection
     * {@link ReflectionHelper#setPrivateValue(Class, Object, int, Object)}
     * @param instanceclass
     * @param instance
     * @param fieldindex
     * @param value
     */
    public static <T, E> void setPrivateValue(Class <? super T > instanceclass, T instance, int fieldindex, E value)
    {
        ReflectionHelper.setPrivateValue(instanceclass, instance, fieldindex, value);
    }

    /**
     * Set a private field to a value using reflection
     * {@link ReflectionHelper#setPrivateValue(Class, Object, String, Object)}
     * @param instanceclass
     * @param instance
     * @param field
     * @param value
     */
    public static <T, E> void setPrivateValue(Class <? super T > instanceclass, T instance, String field, E value)
    {
        ReflectionHelper.setPrivateValue(instanceclass, instance, field, value);
    }

    /**
     * This method is a call in hook from modified external code. Implemented elsewhere.
     * {@link FMLServerHandler#onItemCrafted(EntityPlayer, ItemStack, IInventory)}
     * @param player
     * @param item
     * @param matrix
     */
    @Deprecated
    public static void takenFromCrafting(EntityPlayer player, ItemStack item, IInventory matrix)
    {
    }

    /**
     * This method is a call in hook from modified external code. Implemented elsewhere.
     * {@link FMLServerHandler#onItemSmelted(EntityPlayer, ItemStack)}
     * @param player
     * @param item
     */
    @Deprecated
    public static void takenFromFurnace(EntityPlayer player, ItemStack item)
    {
    }

    /**
     * Throw the offered exception. Likely will stop the game.
     * {@link FMLServerHandler#raiseException(Throwable, String, boolean)}
     * @param message
     * @param e
     */
    public static void throwException(String message, Throwable e)
    {
        FMLServerHandler.instance().raiseException(e, message, true);
    }

    /**
     * Get the minecraft server instance
     * {@link FMLServerHandler#getServer()}
     * @return
     */
    public static MinecraftServer getMinecraftServerInstance()
    {
        return FMLServerHandler.instance().getServer();
    }

    /**
     * To properly implement packet 250 protocol you should always check your channel
     * is active prior to sending the packet
     *
     * @param player
     * @param channel
     * @return
     */
    public static boolean isChannelActive(EntityPlayer player, String channel)
    {
        return FMLCommonHandler.instance().isChannelActive(channel, player);
    }
    
    /**
     * Stubbed method on the server to return a unique model id
     * 
     */
    @Deprecated
    public static int getUniqueBlockModelID(BaseMod mod, boolean flag) {
        return 0;
    }
}