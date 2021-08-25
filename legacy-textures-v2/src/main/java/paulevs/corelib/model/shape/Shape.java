package paulevs.corelib.model.shape;

import java.util.Arrays;
import java.util.Random;

import net.minecraft.client.render.block.BlockRenderer;
import net.minecraft.block.Block;
import net.minecraft.world.BlockView;
import paulevs.corelib.math.*;
import paulevs.corelib.math.BlockFacing;
import paulevs.corelib.texture.UVPair;

public abstract class Shape {
	protected static final boolean[] RENDER_WORLD = new boolean[6];
	protected static final boolean[] RENDER_FACE = new boolean[6];
	protected static final UVPair[] DESTRUCTION = new UVPair[10];

	private static final LocationRandom RANDOM = new LocationRandom();
	protected static final Vec3f RENDER_POS = new Vec3f();
	protected static final Vec3f OFFSET = new Vec3f();
	protected static final Vec3i POS = new Vec3i();
	protected static BlockRenderer renderer;
	protected static BlockView blockView;
	protected static float light = 1;
	protected static int color = 0xFFFFFF;
	protected static Block block = Block.STONE;
	protected static UVPair uv = new UVPair();
	protected static int meta = 0;
	protected static int destruction = -1;

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
		Shape.renderer = renderer;
	}

	public static void setBlockView(BlockView blockView) {
		Shape.blockView = blockView;
	}

	public static void setColor(int color) {
		Shape.color = color;
	}

	public static void setColorWhite() {
		Shape.color = 0xFFFFFF;
	}

	public static void setColorFromWorld() {
		Shape.color = block.getColorMultiplier(blockView, POS.x, POS.y, POS.z);
	}

	public static void setUV(UVPair uv) {
		Shape.uv = destruction < 0 ? uv : getDestruction();
	}

	public static void setLight(float light) {
		Shape.light = light;
	}

	public static void setLightFromWorld() {
		Shape.light = block.getColorMultiplier(blockView, POS.x, POS.y, POS.z);
	}

	public static void setBlock(Block block) {
		Shape.block = block;
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
		Shape.meta = meta;
	}

	public static int getMeta() {
		return meta;
	}

	public static void setWorldCulling(boolean ignore) {
		if (ignore) {
			Arrays.fill(RENDER_WORLD, true);
		}
		else {
			for (BlockFacing facing: BlockFacing.getValues()) {
				int id = facing.getID();
				int x = facing.offsetX(POS.x);
				int y = facing.offsetY(POS.y);
				int z = facing.offsetZ(POS.z);
				RENDER_WORLD[id] = block.isSideRendered(blockView, x, y, z, id);
			}
		}
	}

	public static void setFaceRendering(BlockFacing facing, boolean renderFace) {
		RENDER_FACE[facing.getID()] = renderFace;
	}

	public static void drawAll() {
		Arrays.fill(RENDER_FACE, true);
	}

	protected boolean shouldRenderFace(BlockFacing facing) {
		int index = facing.getID();
		return RENDER_WORLD[index] && RENDER_FACE[index];
	}

	public static Block getBlock() {
		return block;
	}
	
	public static void setDestruction(int destruction) {
		Shape.destruction = destruction;
	}
	
	private static UVPair getDestruction() {
		if (DESTRUCTION[0] == null) {
			for (int i = 0; i < 10; i++) {
				DESTRUCTION[i] = UVPair.getVanillaUV(240 + i);
			}
		}
		return DESTRUCTION[MHelper.clamp(destruction, 0, 9)];
	}
}