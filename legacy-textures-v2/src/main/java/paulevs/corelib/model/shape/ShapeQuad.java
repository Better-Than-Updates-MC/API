package paulevs.corelib.model.shape;

import net.minecraft.client.render.Tessellator;
import paulevs.corelib.math.BlockFacing;
import paulevs.corelib.math.Vec3f;
import paulevs.corelib.texture.UVPair;

@SuppressWarnings("unused")
public class ShapeQuad extends Shape {
	final Vec3f[] positions = new Vec3f[4];
	UVPair pair = new UVPair();
	float colorRed = 1;
	float colorGreen = 1;
	float colorBlue = 1;
	boolean flat = false;
	BlockFacing facing;

	public ShapeQuad() {
	}
	
	public ShapeQuad setFacing(BlockFacing facing) {
		this.facing = facing;
		return this;
	}

	public ShapeQuad setFlat(boolean flat) {
		this.flat = flat;
		return this;
	}

	public ShapeQuad setPoint(int index, float x, float y, float z) {
		positions[index] = new Vec3f(x, y, z);
		return this;
	}

	@Override
	public void render() {
		if (flat) {
			renderFlat();
		}
		else {
			renderSmooth();
		}
	}

	private void renderFlat() {
		int x = facing == null ? POS.x : facing.offsetX(POS.x);
		int y = facing == null ? POS.y : facing.offsetY(POS.y);
		int z = facing == null ? POS.z : facing.offsetZ(POS.z);
		float light = block.getBrightness(blockView, x, y, z);
		float r = light * colorRed;
		float g = light * colorGreen;
		float b = light * colorBlue;
		
		Tessellator.INSTANCE.color(r, g, b);
		for (Vec3f pos: positions) {
			float u = pair.getStart().getX();
			float v = pair.getStart().getY();
			Tessellator.INSTANCE.vertex(pos.getX(), pos.getY(), pos.getZ(), u, v);
		}
	}

	private void renderSmooth() {
		
	}
	
	@Override
	protected boolean shouldRenderFace(BlockFacing facing) {
		if (facing == null) {
			return true;
		}
		return super.shouldRenderFace(facing);
	}
}