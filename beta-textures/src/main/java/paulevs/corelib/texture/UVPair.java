package paulevs.corelib.texture;

import net.minecraft.block.Block;
import paulevs.corelib.CoreLib;
import paulevs.corelib.math.Vec2F;

public class UVPair {
	private final Vec2F start;
	private final Vec2F end;

	public UVPair() {
		this(new Vec2F(0, 0), new Vec2F(1, 1));
	}

	public UVPair(Vec2F start, Vec2F end) {
		this.start = start;
		this.end = end;
	}

	public Vec2F getStart() {
		return start;
	}

	public Vec2F getEnd() {
		return end;
	}

	public static UVPair getVanillaUV(Block block, int side, int meta) {
		int pos = block.getTextureForSide(side, meta);
		int x = (pos & 15) << 4;
		int y = pos & 240;
		Vec2F start = new Vec2F(x / CoreLib.blocksAtlas.getSize(), y / CoreLib.blocksAtlas.getSize());
		Vec2F end = new Vec2F().set(start).add(16F / CoreLib.blocksAtlas.getSize());
		return new UVPair(start, end);
	}
	
	public static UVPair getVanillaUV(int index) {
		int x = (index & 15) << 4;
		int y = index & 240;
		Vec2F start = new Vec2F(x / CoreLib.blocksAtlas.getSize(), y / CoreLib.blocksAtlas.getSize());
		Vec2F end = new Vec2F().set(start).add(16F / CoreLib.blocksAtlas.getSize());
		return new UVPair(start, end);
	}
}
