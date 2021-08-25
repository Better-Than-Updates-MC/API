package paulevs.corelib.math;

import java.util.Locale;

public class Vec2F {
	private float x;
	private float y;

	public Vec2F() {
		this.x = 0;
		this.y = 0;
	}

	public Vec2F(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public Vec2F setX(float x) {
		this.x = x;
		return this;
	}

	public float getY() {
		return y;
	}

	public Vec2F setY(float y) {
		this.y = y;
		return this;
	}

	@SuppressWarnings("MethodDoesntCallSuperMethod")
	public Vec2F clone() {
		return new Vec2F(x, y);
	}

	public Vec2F invert() {
		x = -x;
		y = -y;
		return this;
	}

	public float getLengthSqared() {
		return x * x + y * y;
	}

	public float getLength() {
		return (float) Math.sqrt(getLengthSqared());
	}

	public Vec2F normalize() {
		float l = getLengthSqared();
		if (l > 0) {
			l = (float) Math.sqrt(l);
			x /= l;
			y /= l;
		}
		return this;
	}

	public String toString() {
		return String.format(Locale.ROOT, "[%f, %f]", x, y);
	}

	public Vec2F multiple(float n) {
		x *= n;
		y *= n;
		return this;
	}

	public Vec2F add(Vec2F vec) {
		x += vec.x;
		y += vec.y;
		return this;
	}

	public Vec2F add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}

	public Vec2F set(Vec2F vec) {
		x = vec.x;
		y = vec.y;
		return this;
	}

	public Vec2F set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Vec2F subtract(Vec2F vec) {
		x -= vec.x;
		y -= vec.y;
		return this;
	}

	public float dot(Vec2F vec) {
		return x * vec.x + y * vec.y;
	}

	public Vec2F rotateCW() {
		float nx = y;
		float ny = -x;
		x = nx;
		y = ny;
		return this;
	}

	public float distance(Vec2F vec) {
		float x = this.x - vec.x;
		float y = this.y - vec.y;
		return (float) Math.sqrt(x * x + y * y);
	}

	public float distanceSquared(Vec2F vec) {
		float x = this.x - vec.x;
		float y = this.y - vec.y;
		return x * x + y * y;
	}

	public float angle(Vec2F vec) {
		return (float) Math.acos(this.dot(vec));
	}

	public float signAngle(Vec2F vec) {
		float angle = angle(vec);
		boolean sign = this.clone().rotateCW().angle(vec) * 2 > Math.PI;
		return sign ? -angle : angle;
	}

	@Override
	public int hashCode() {
		return ((Float.hashCode(x) & 65535) << 16) | (Float.hashCode(y) & 65535);
	}

	public Vec2F add(float n) {
		return add(n, n);
	}
}
