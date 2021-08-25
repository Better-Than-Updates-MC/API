package paulevs.corelib.model.shape;

import java.util.Arrays;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.render.block.BlockRenderer;
import net.minecraft.world.BlockView;
import paulevs.corelib.CoreLib;
import paulevs.corelib.math.LocationRandom;
import paulevs.corelib.math.Vec3f;
import paulevs.corelib.math.Vec3i;
import paulevs.corelib.texture.UVPair;

public abstract class ShapeItem {
	protected static final boolean[] RENDER_FACE = new boolean[6];

	private static final LocationRandom RANDOM = new LocationRandom();
	protected static final Vec3f RENDER_POS = new Vec3f();
	protected static final Vec3f OFFSET = new Vec3f();
	protected static final Vec3i POS = new Vec3i();
	protected static BlockRenderer renderer;
	protected static BlockView blockView = CoreLib.ITEM_VIEW;
	protected static float light = 1;
	protected static int color = 0xFFFFFFFF;
	protected static Block block = Block.STONE;
	protected static UVPair uv = new UVPair();
	protected static int meta = 0;

	public static void setPos(int x, int y, int z) {
		POS.set(x, y, z);
	}

	public static void setOffset(float x, float y, float z) {
		OFFSET.set(x, y, z);
		updateRenderPos();
	}

	public static void setOffsetItem() {
		setOffset(-0.5F, -0.5F, -0.5F);
	}

	public static void resetOffset() {
		setOffset(0, 0, 0);
	}

	public static void setRenderer(BlockRenderer renderer) {
		ShapeItem.renderer = renderer;
	}

	public static void setBlockView(BlockView blockView) {
		ShapeItem.blockView = blockView;
	}

	public static void setColor(int color) {
		ShapeItem.color = color;
	}

	public static void setColorWhite() {
		ShapeItem.color = 0xFFFFFFFF;
	}

	public static void setColorFromWorld() {
		ShapeItem.color = block.getColorMultiplier(blockView, POS.x, POS.y, POS.z);
	}

	public static void setUV(UVPair uv) {
		ShapeItem.uv = uv;
	}

	public static void setLight(float light) {
		ShapeItem.light = light;
	}

	public static void setLightFromWorld() {
		ShapeItem.light = block.getBrightness(blockView, POS.x, POS.y, POS.z);
	}

	public static void setBlock(Block block) {
		ShapeItem.block = block;
	}

	public static Random getRandomForLocation() {
		RANDOM.setSeed(POS.x, POS.y, POS.z);
		return RANDOM;
	}

	protected static void updateRenderPos() {
		RENDER_POS.set(POS);
		RENDER_POS.add(OFFSET);
	}

	public abstract void render();

	public static int getX() {
		return POS.x;
	}

	public static int getY() {
		return POS.y;
	}

	public static int getZ() {
		return POS.z;
	}

	public static BlockView getBlockView() {
		return blockView;
	}

	public static void setMeta(int meta) {
		ShapeItem.meta = meta;
	}

	public static int getMeta() {
		return meta;
	}

	public static void setFaceRendering(int index, boolean renderFace) {
		RENDER_FACE[index] = renderFace;
	}

	public static void drawAll() {
		Arrays.fill(RENDER_FACE, true);
	}

	protected static boolean shouldRenderFace(int index) {
		return RENDER_FACE[index];
	}
}