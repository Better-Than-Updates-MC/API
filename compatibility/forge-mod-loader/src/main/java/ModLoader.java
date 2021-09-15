import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import net.minecraft.achievement.Achievement;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.particle.Particle;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.render.block.BlockRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.block.BlockEntityRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.ClientPlayPacketHandler;
import net.minecraft.packet.login.LoginRequestPacket;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.source.WorldSource;

/**
 * Compatibility layer for Risugami's ModLoader, ModLoaderMP, heavily based on cpw's Forge Mod Loader.
 * @deprecated use {@link net.fabricmc.loader.api.FabricLoader Fabric Loader} instead!
 * @since Risugami's ModLoader
 * @author halotroop2288
 */
@Deprecated
@SuppressWarnings("unused")
public class ModLoader {
	static void addAchievementDesc(Achievement achievement, String name, String description) {
	}

	static int addAllFuel(int id, int metadata) {
		return 0;
	}

	static void addAllRenderers(Map<Class<? extends Entity>, EntityRenderer> renderers) {
	}

	static void addAnimation(Particle anim) {
	}

	static int addArmor(String armor) {
		return 0;
	}

	static void addBiome(Biome biome) {
	}

	static void addLocalization(String key, String value) {
	}

	static void addLocalization(String key, String lang, String value) {
	}

	static void addName(Object instance, String name) {
	}

	static void addName(Object instance, String lang, String name) {
	}

	static int addOverride(String fileToOverride, String fileToAdd) {
		return 0;
	}

	static void addOverride(String path, String overlayPath, int index) {
	}

	static void addRecipe(ItemStack output, Object... params) {
	}

	static void addShapelessRecipe(ItemStack output, Object... params) {
	}

	static void addSmelting(int input, ItemStack output) {
	}

	static void addSpawn(Class<? extends LivingEntity> entityClass, int weightedProb, int min, int max, SpawnGroup spawnList) {
	}

	static void addSpawn(Class<? extends LivingEntity> entityClass, int weightedProb, int min, int max, SpawnGroup spawnList, Biome... biomes) {
	}

	static void addSpawn(String entityName, int weightedProb, int min, int max, SpawnGroup spawnList) {
	}

	static void addSpawn(String entityName, int weightedProb, int min, int max, SpawnGroup spawnList, Biome... biomes) {
	}

	static boolean dispenseEntity(World world, double x, double y, double z, int xVel, int zVel, ItemStack item) {
		return false;
	}

	static void genericContainerRemoval(World world, int x, int y, int z) {
	}

	static List<BaseMod> getLoadedMods() {
		return null;
	}

	static Logger getLogger() {
		return null;
	}

	static Minecraft getMinecraftInstance() {
		return null;
	}

	static <T, E> T getPrivateValue(Class<? super E> instanceClass, E instance, int fieldIndex) {
		return null;
	}

	static <T, E> T getPrivateValue(Class<? super E> instanceClass, E instance, String field) {
		return null;
	}

	static int getUniqueBlockModelID(BaseMod mod, boolean full3DItem) {
		return 0;
	}

	static int getUniqueEntityId() {
		return 0;
	}

	static int getUniqueSpriteIndex(String path) {
		return 0;
	}

	static boolean isGUIOpen(Class<? extends Screen> screen) {
		return false;
	}

	static boolean isModLoaded(String modName) {
		return false;
	}

	static void loadConfig() {
	}

	static BufferedImage loadImage(TextureManager texCache, String path) {
		return null;
	}

	static void onItemPickup(PlayerEntity player, ItemStack item) {
	}

	static void onTick(float tick, net.minecraft.client.Minecraft game) {
	}

	static void openGUI(PlayerEntity player, Screen screen) {
	}

	static void populateChunk(WorldSource generator, int chunkX, int chunkZ, World world) {
	}

	static KeyBinding[] registerAllKeys(KeyBinding[] keys) {
		return keys;
	}

	static void registerAllTextureOverrides(TextureManager cache) {
	}

	static void registerBlock(Block block) {
	}

	static void registerBlock(Block block, Class<? extends BlockItem> itemClass) {
	}

	static void registerEntityID(Class<? extends Entity> entityClass, String entityName, int id) {
	}

	static void registerEntityID(Class<? extends Entity> entityClass, String entityName, int id, int background, int foreground) {
	}

	static void registerKey(BaseMod mod, KeyBinding keyHandler, boolean allowRepeat) {
	}

	static void registerPacketChannel(BaseMod mod, String channel) {
	}

	static void registerTileEntity(Class<? extends BlockEntity> blockEntityClass, String id) {
	}

	static void registerTileEntity(Class<? extends BlockEntity> blockEntityClass, String id, BlockEntityRenderer renderer) {
	}

	static void removeBiome(Biome biome) {
	}

	static void removeSpawn(Class<? extends LivingEntity> entityClass, SpawnGroup spawnList) {
	}

	static void removeSpawn(Class<? extends LivingEntity> entityClass, SpawnGroup spawnList, Biome... biomes) {
	}

	static void removeSpawn(String entityName, SpawnGroup spawnList) {
	}

	static void removeSpawn(String entityName, SpawnGroup spawnList, Biome... biomes) {
	}

	static boolean renderBlockIsItemFull3D(int modelID) {
		return false;
	}

	static void renderInvBlock(BlockRenderer renderer, Block block, int metadata, int modelID) {
	}

	static boolean renderWorldBlock(BlockRenderer renderer, BlockView world, int x, int y, int z, Block block, int modelID) {
		return false;
	}

	static void saveConfig() {
	}

	static void serverChat(String text) {
	}

	static void serverLogin(ClientPlayPacketHandler handler, LoginRequestPacket loginPacket) {
	}

	static void setInGameHook(BaseMod mod, boolean enable, boolean useClock) {
	}

	static void setInGUIHook(BaseMod mod, boolean enable, boolean useClock) {
	}

	static <T, E> void setPrivateValue(Class<? super T> instanceClass, T instance, int fieldIndex, E value) {
	}

	static <T, E> void setPrivateValue(Class<? super T> instanceClass, T instance, String field, E value) {
	}

	static void takenFromCrafting(PlayerEntity player, ItemStack item, Inventory matrix) {
	}

	static void takenFromFurnace(PlayerEntity player, ItemStack item) {
	}

	static void throwException(String message, Throwable e) {
	}
}