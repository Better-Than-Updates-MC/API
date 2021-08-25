package paulevs.corelib.math;

import net.minecraft.util.Vec3i;

public class Vector3i extends Vec3i {
	public Vector3i set(Vector3i vec) {
		x = vec.x;
		y = vec.y;
		z = vec.z;
		return this;
	}

	public Vector3i setFloor(Vec3f vec) {
		x = MHelper.floor(vec.getX());
		y = MHelper.floor(vec.getY());
		z = MHelper.floor(vec.getZ());
		return this;
	}

	public Vector3i set(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Vector3i subtract(Vector3i vec) {
		return subtract(vec.x, vec.y, vec.z);
	}

	public Vector3i subtract(int x, int y, int z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

	public Vector3i subtract(int n) {
		return subtract(n, n, n);
	}
}
