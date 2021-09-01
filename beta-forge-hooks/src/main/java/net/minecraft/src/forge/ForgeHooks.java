/*
 * This software is provided under the terms of the Minecraft Forge Public 
 * License v1.0.
 */

package net.minecraft.src.forge;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SleepStatus;

import java.util.*;

/**
 * @deprecated use Fabric Beta Essentials instead.
 */
@Deprecated
@SuppressWarnings("unused")
public class ForgeHooks {
	public static void onTakenFromCrafting(PlayerEntity player, ItemStack stack, Inventory craftMatrix) {
		for (ICraftingHandler handler : craftingHandlers) {
			handler.onTakenFromCrafting(player, stack, craftMatrix);
		}
	}

	static LinkedList<ICraftingHandler> craftingHandlers = new LinkedList<>();

	public static void onDestroyCurrentItem(PlayerEntity player, ItemStack stack) {
		for (IDestroyToolHandler handler : destroyToolHandlers) {
			handler.onDestroyCurrentItem(player, stack);
		}
	}

	static LinkedList<IDestroyToolHandler> destroyToolHandlers = new LinkedList<>();

	public static SleepStatus sleepInBedAt(PlayerEntity player, int i, int j, int k) {
		for (ISleepHandler handler : sleepHandlers) {
			SleepStatus status = handler.sleepInBedAt(player, i, j, k);
			if (status != null)
				return status;
		}
		return null;
	}

	static LinkedList<ISleepHandler> sleepHandlers = new LinkedList<>();

	public static boolean canHarvestBlock(Block block,
	                                      PlayerEntity player, int md) {
		if(block.material.doesRequireTool())
			return true;
		ItemStack itemstack = player.inventory.getHeldItem();
		if(itemstack == null) return false;

		List<?> tc= toolClasses.get(itemstack.itemId);
		if(tc==null) return itemstack.isEffectiveOn(block);
		Object[] ta=tc.toArray();
		String cls = (String)ta[0]; int hvl=(Integer)ta[1];

		Integer bhl = toolHarvestLevels.get(Arrays.asList(block.id, md, cls));
		if (bhl == null) return itemstack.isEffectiveOn(block);
		if (bhl > hvl) return false;
		return itemstack.isEffectiveOn(block);
	}

	public static float blockStrength(Block bl, PlayerEntity player, int md) {
		float bh = bl.getHardness(player);
		if(bh < 0.0F) return 0.0F;

		if(!canHarvestBlock(bl,player,md)) {
			return 1.0F / bh / 100F;
		} else {
			return player.getStrengh(bl) / bh / 30F;
		}
	}

	public static boolean isToolEffective(ItemStack ist, Block bl, int md) {
		List<?> tc = toolClasses.get(ist.itemId);
		if (tc == null) return false;
		Object[] ta=tc.toArray();
		String cls=(String)ta[0];

		return toolEffectiveness.contains(Arrays.asList(bl.id, md, cls));
	}

	static void initTools() {
		if(toolInit) return;
		toolInit=true;

		MinecraftForge.setToolClass(Item.WOOD_PICKAXE,"pickaxe",0);
		MinecraftForge.setToolClass(Item.STONE_PICKAXE,"pickaxe",1);
		MinecraftForge.setToolClass(Item.IRON_PICKAXE,"pickaxe",2);
		MinecraftForge.setToolClass(Item.GOLD_PICKAXE,"pickaxe",0);
		MinecraftForge.setToolClass(Item.DIAMOND_PICKAXE,"pickaxe",3);

		MinecraftForge.setToolClass(Item.WOOD_AXE,"axe",0);
		MinecraftForge.setToolClass(Item.STONE_AXE,"axe",1);
		MinecraftForge.setToolClass(Item.IRON_AXE,"axe",2);
		MinecraftForge.setToolClass(Item.GOLD_AXE,"axe",0);
		MinecraftForge.setToolClass(Item.DIAMOND_AXE,"axe",3);

		MinecraftForge.setToolClass(Item.WOOD_SHOVEL,"shovel",0);
		MinecraftForge.setToolClass(Item.STONE_SHOVEL,"shovel",1);
		MinecraftForge.setToolClass(Item.IRON_SHOVEL,"shovel",2);
		MinecraftForge.setToolClass(Item.GOLD_SHOVEL,"shovel",0);
		MinecraftForge.setToolClass(Item.DIAMOND_SHOVEL,"shovel",3);

		MinecraftForge.setBlockHarvestLevel(Block.OBSIDIAN,"pickaxe",3);
		MinecraftForge.setBlockHarvestLevel(Block.DIAMOND_ORE,"pickaxe",2);
		MinecraftForge.setBlockHarvestLevel(Block.DIAMOND_BLOCK,"pickaxe",2);
		MinecraftForge.setBlockHarvestLevel(Block.GOLD_ORE,"pickaxe",2);
		MinecraftForge.setBlockHarvestLevel(Block.GOLD_ORE,"pickaxe",2);
		MinecraftForge.setBlockHarvestLevel(Block.IRON_ORE,"pickaxe",1);
		MinecraftForge.setBlockHarvestLevel(Block.IRON_BLOCK,"pickaxe",1);
		MinecraftForge.setBlockHarvestLevel(Block.LAPIS_LAZULI_BLOCK,"pickaxe",1);
		MinecraftForge.setBlockHarvestLevel(Block.LAPIS_LAZULI_ORE,"pickaxe",1);
		MinecraftForge.setBlockHarvestLevel(Block.REDSTONE_ORE,"pickaxe",2);
		MinecraftForge.setBlockHarvestLevel(Block.REDSTONE_ORE_LIT,"pickaxe",2);
		MinecraftForge.removeBlockEffectiveness(Block.REDSTONE_ORE,"pickaxe");
		MinecraftForge.removeBlockEffectiveness(Block.REDSTONE_ORE_LIT,"pickaxe");

		Block[] pickeff ={
			Block.COBBLESTONE, Block.WOOD_STAIRS,
			Block.COBBLESTONE_STAIRS, Block.STONE, Block.SANDSTONE,
			Block.MOSSY_COBBLESTONE, Block.IRON_ORE,
			Block.IRON_BLOCK, Block.COAL_ORE, Block.GOLD_BLOCK,
			Block.GOLD_ORE, Block.DIAMOND_ORE, Block.DIAMOND_BLOCK,
			Block.ICE, Block.NETHERRACK, Block.LAPIS_LAZULI_ORE,
			Block.LAPIS_LAZULI_BLOCK
				};
		for(Block bl : pickeff) {
			MinecraftForge.setBlockHarvestLevel(bl,"pickaxe",0);
		}

		// TODO: add other tool tables.
	}

	public static final int majorVersion=1;
	public static final int minorVersion=0;
	public static final int revisionVersion=6;

	static {
		System.out.printf("MinecraftForge V%d.%d.%d Initialized\n",majorVersion,minorVersion,revisionVersion);
	}

	static boolean toolInit=false;
	static HashMap<Integer, List<?>> toolClasses=new HashMap<>();
	static HashMap<List<?>, Integer> toolHarvestLevels=new HashMap<>();
	static HashSet<List<?>> toolEffectiveness = new HashSet<>();
}
