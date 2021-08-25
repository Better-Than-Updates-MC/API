package paulevs.corelib.math;

public class Vec3i extends net.minecraft.util.Vec3i {
	public Vec3i set(Vec3i vec) {
		x = vec.x;
		y = vec.y;
		z = vec.z;
		return this;
	}

	public Vec3i setFloor(Vec3f vec) {
		x = MHelper.floor(vec.getX());
		y = MHelper.floor(vec.getY());
		z = MHelper.floor(vec.getZ());
		return this;
	}

	public Vec3i set(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Vec3i subtract(Vec3i vec) {
		return subtract(vec.x, vec.y, vec.z);
	}

	public Vec3i subtract(int x, int y, int z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

	public Vec3i subtract(int n) {
		return subtract(n, n, n);
	}
}
