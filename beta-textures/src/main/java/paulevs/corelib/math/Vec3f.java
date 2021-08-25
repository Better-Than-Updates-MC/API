package paulevs.corelib.math;

import net.minecraft.util.math.Vec3d;
import org.lwjgl.util.vector.Vector3f;
import paulscode.sound.Vector3D;

import java.util.Locale;

public class Vec3f extends Vector3f {
	public Vec3f(float x, float y, float z) {
		super();
		set(x, y, z);
	}

	public Vec3f() {
		this(0, 0, 0);
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public Vec3f setFloor(Vec3f vec) {
		x = MHelper.floor(vec.getX());
		y = MHelper.floor(vec.getY());
		z = MHelper.floor(vec.getZ());
		return this;
	}

	public Vec3f clone() {
		return new Vec3f(x, y, z);
	}

	public Vec3f invert() {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}

	public float getLengthSqared() {
		return x * x + y * y + z * z;
	}

	public float getLength() {
		return (float) Math.sqrt(getLengthSqared());
	}

	public Vec3f normalize() {
		float l = getLengthSqared();
		if (l > 0) {
			l = (float) Math.sqrt(l);
			x /= l;
			y /= l;
			z /= l;
		}
		return this;
	}

	public Vec3f cross(Vec3f vec) {
		float cx = y * vec.z - z * vec.y;
		float cy = z * vec.x - x * vec.z;
		float cz = x * vec.y - y * vec.x;
		this.set(cx, cy, cz);
		return this;
	}

	public Vec3f crossNew(Vec3f vec) {
		float cx = y * vec.z - z * vec.y;
		float cy = z * vec.x - x * vec.z;
		float cz = x * vec.y - y * vec.x;
		return new Vec3f(cx, cy, cz);
	}

	public String toString() {
		return String.format(Locale.ROOT, "[%f, %f, %f]", x, y, z);
	}

	public Vec3f multiple(float n) {
		x *= n;
		y *= n;
		z *= n;
		return this;
	}

	public Vec3f add(Vec3f vec) {
		return add(vec.x, vec.y, vec.z);
	}

	public Vec3f add(Vec3f vec, float power) {
		return add(vec.x * power, vec.y * power, vec.z * power);
	}

	public Vec3f add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	public Vec3f add(float n) {
		return add(n, n, n);
	}

	public Vec3f set(Vec3f vec) {
		x = vec.x;
		y = vec.y;
		z = vec.z;
		return this;
	}

	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vec3f subtract(Vec3f vec) {
		return subtract(vec.x, vec.y, vec.z);
	}

	public Vec3f subtract(Vec3f vec, float power) {
		return subtract(vec.x * power, vec.y * power, vec.z * power);
	}

	public Vec3f subtract(float n) {
		return subtract(n, n, n);
	}

	public Vec3f subtract(float x, float y, float z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

	public float dot(Vec3f vec) {
		return x * vec.x + y * vec.y + z * vec.z;
	}

	public float angle(Vec3f vec) {
		return (float) Math.acos(dot(vec) / Math.sqrt(getLengthSqared() * vec.getLengthSqared()));
	}

	public boolean equals(Object obj) {
		if (obj instanceof Vector3f) {
			Vector3f vec = (Vector3f) obj;
			return x == vec.x && y == vec.y && z == vec.z;
		} else if (obj instanceof Vector3D) {
			Vector3D vec = (Vector3D) obj;
			return x == vec.x && y == vec.y && z == vec.z;
		} else if (obj instanceof Vec3i) {
			Vec3i vec = (Vec3i) obj;
			return x == vec.x && y == vec.y && z == vec.z;
		} else if (obj instanceof Vec3d) {
			Vec3d vec = (Vec3d) obj;
			return x == vec.x && y == vec.y && z == vec.z;
		} else if (obj instanceof net.minecraft.util.Vec3i) {
			net.minecraft.util.Vec3i vec = (net.minecraft.util.Vec3i) obj;
			return x == vec.x && y == vec.y && z == vec.z;
		} else if (obj instanceof Integer || obj instanceof Float || obj instanceof Double) {
			double d = (double) obj;
			return ((x == y && y == z) && (x == d));
		} else {
			return false;
		}
	}

	public boolean isZero() {
		return x == 0 && y == 0 && z == 0;
	}

	public Vec3f divide(Vec3f vec) {
		x /= vec.x;
		y /= vec.y;
		z /= vec.z;
		return this;
	}

	public void set(Vec3i vec) {
		set(vec.x, vec.y, vec.z);
	}
}
