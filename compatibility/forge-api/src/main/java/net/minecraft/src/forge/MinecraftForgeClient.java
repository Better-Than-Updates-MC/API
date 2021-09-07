/*
 * This software is provided under the terms of the Minecraft Forge Public 
 * License v1.0.
 */
package net.minecraft.src.forge;

import com.halotroop.fabric.accessor.forge.client.MinecraftAccessor;
import com.halotroop.fabric.api.event.interaction.BlockHighlightEvents;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.BlockRenderer;
import com.halotroop.fabric.api.event.render.ClientRenderEvents;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.texture.TextureManager;

/**
 * Provided to ease the transition from Forge / Risugami's ModLoader to Fabric.
 *
 * @deprecated use {@link com.halotroop.fabric Fabric Beta Essentials} directly instead!
 */
@Deprecated
public class MinecraftForgeClient {
	private static final Minecraft CLIENT = MinecraftAccessor.getInstance();
	private static final TextureManager TEXTURE_MANAGER = ((MinecraftAccessor)CLIENT).getTextureManager();
	private static final Tessellator TESSELLATOR = Tessellator.INSTANCE;

	/**
	 * Registers a new block highlight handler.
	 * @deprecated use {@link BlockHighlightEvents#BLOCK_HIGHLIGHT}
	 * @see Event#register
	 */
	public static void registerHighlightHandler(BlockHighlightEvents.BlockHighlight handler) {
		BlockHighlightEvents.BLOCK_HIGHLIGHT.register(handler);
	}

	/**
	 * Bind a texture.  This is used to bind a texture file when
	 * performing your own rendering, rather than using ITextureProvider.
	 *
	 * This variation is reserved for future expansion.
	 *
	 * @param path The path to the texture, including the extension (.png).
	 * @param sub used to be for binding a custom Tesselator. Unused.
	 */
	public static void bindTexture(String path, int sub) {
		if (!path.startsWith("/")) path = '/' + path;
		TEXTURE_MANAGER.bindTexture(TEXTURE_MANAGER.getTextureId(path));
	}

	/**
	 * Bind a texture.  This is used to bind a texture file when
	 * performing your own rendering, rather than using {@link ITextureProvider}.
	 *
	 * @param path The path to the texture, including the extension (.png).
	 */
	public static void bindTexture(String path) {
		bindTexture(path, 0);
	}

	/**
	 * Unbind a texture.
	 *
	 * <p>This binds the default texture, when you are
	 * finished performing custom rendering.</p>
	 *
	 * @deprecated this is usually not needed. When it is...
	 * @see
	 */
	@Deprecated
	public static void unbindTexture() {
		bindTexture("/terrain.png");
	}

	/**
	 * Preload a texture.
	 *
	 * <p>Textures must be preloaded before the first use,
	 * or they will cause visual anomalies.</p>
	 */
	public static void preloadTexture(String texture) {
		TEXTURE_MANAGER.getTextureId(texture);
	}

	/**
	 * Render a block.  Render a block which may have a custom texture.
	 * @deprecated use {@linkplain BlockRenderer#render(Block, int, int, int) BlockRenderer directly} and subscribe to
	 * the Fabric alternatives, {@link ClientRenderEvents#START_BLOCK_RENDER} and {@link ClientRenderEvents#END_BLOCK_RENDER}
	 */
	public static void renderBlock(BlockRenderer blockRenderer, Block block, int x, int y, int z) {
		blockRenderer.render(block, x, y, z);
	}

	/**
	 * Get the current render pass.
	 */
	public static int getRenderPass() {
		return ForgeHooksClient.getRenderPass();
	}
	
	public static void registerCustomItemRenderer (int itemID, ICustomItemRenderer renderer) {
		// TODO
	}
	
	public static ICustomItemRenderer getCustomItemRenderer (int itemID) {
		return null; // TODO
	}
}
