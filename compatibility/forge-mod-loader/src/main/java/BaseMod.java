import java.util.Map;
import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.render.block.BlockRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

/**
 * Abstract mod class extended by pre-Fabric mods.
 * @deprecated use Fabric's {@link net.fabricmc.api.ModInitializer Common,}
 * {@link net.fabricmc.api.ClientModInitializer Client,}
 * or {@link net.fabricmc.api.DedicatedServerModInitializer Server}
 * entrypoints instead.
 * @since Risugami's ModLoader
 * @author Risugami
 */
@Deprecated
@SuppressWarnings("unused")
public abstract class BaseMod {
	public BaseMod() {
	}

	public int AddFuel(int id) {
		return 0;
	}

	@Environment(EnvType.CLIENT)
	public void AddRenderer(Map<Class<? extends Entity>, EntityRenderer> renderers) {
	}

	public boolean DispenseEntity(World world, double x, double y, double z, int xVelocity, int zVelocity, ItemStack stack) {
		return false;
	}

	public void GenerateNether(World world, Random random, int chunkX, int chunkZ) {
	}

	public void GenerateSurface(World world, Random random, int chunkX, int chunkZ) {
	}

	@Environment(EnvType.CLIENT)
	public void KeyboardEvent(KeyBinding event) {
	}

	public void ModsLoaded() {
	}

	@Environment(EnvType.CLIENT)
	public boolean OnTickInGame(float tick, Minecraft game) {
		return false;
	}

	@Environment(EnvType.CLIENT)
	public boolean OnTickInGUI(float tick, Minecraft game, Screen screen) {
		return false;
	}

	@Environment(EnvType.CLIENT)
	public void RegisterAnimation(Minecraft game) {
	}

	@Environment(EnvType.CLIENT)
	public void RenderInvBlock(BlockRenderer renderer, Block block, int metadata, int modelId) {
	}

	@Environment(EnvType.CLIENT)
	public boolean RenderWorldBlock(BlockRenderer renderer, BlockView world, int x, int y, int z, Block block, int modelId) {
		return false;
	}

	public void TakenFromCrafting(PlayerEntity player, ItemStack item, Inventory matrix) {
	}

	public void TakenFromFurnace(PlayerEntity player, ItemStack item) {
	}

	@Override
	public String toString() {
		return this.getClass().getName() + " " + this.Version();
	}

	public abstract String Version();
}