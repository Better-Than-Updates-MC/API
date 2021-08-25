package paulevs.corelib.math;

import java.util.Arrays;

public class MHelper {
	public static final float PI = 3.1415926535897932384626433832795F;
	public static final float MAX_ANGLE = PI * 2.0F;
	public static final float HALF_PI = PI * 0.5F;
	private static final float RAD_TO_DEG = 180F / PI;

	public static float clamp(float x, float min, float max) {
		return x < min ? min : Math.min(x, max);
	}

	public static int clamp(int x, int min, int max) {
		return x < min ? min : Math.min(x, max);
	}

	public static int floor(float x) {
		return x < 0 ? (int) (x - 1) : (int) x;
	}

	public static float clampAngle(float a) {
		return wrap(a, MAX_ANGLE);
	}

	public static float clampAngleDegrees(float a) {
		return wrap(a, 360);
	}

	public static float wrap(float x, float side) {
		return x - floor(x / side) * side;
	}

	public static float[] makeArray(int size, float value) {
		float[] array = new float[size];
		Arrays.fill(array, value);
		return array;
	}

	public static float sign(float x) {
		return x < 0 ? -1 : 1;
	}

	public static int sign(int x) {
		return x < 0 ? -1 : 1;
	}

	public static float signZ(float x) {
		return x == 0 ? 0 : x < 0 ? -1 : 1;
	}

	public static int signZ(int x) {
		return Integer.compare(x, 0);
	}

	public static float max(float a, float b, float c) {
		return Math.max(Math.max(a, b), c);
	}

	public static Vec3f max(Vec3f a, Vec3f b, Vec3f result) {
		result.setX(Math.max(a.x, b.x));
		result.setY(Math.max(a.y, b.y));
		result.setZ(Math.max(a.z, b.z));
		return result;
	}

	public static Vec3f min(Vec3f a, Vec3f b, Vec3f result) {
		result.setX(Math.min(a.x, b.x));
		result.setY(Math.min(a.y, b.y));
		result.setZ(Math.min(a.z, b.z));
		return result;
	}

	public static float lerp(float a, float b, float mix) {
		return a - (a - b) * mix;
	}

	public static Vec3f lerp(Vec3f a, Vec3f b, Vec3f output, float mix) {
		output.setX(lerp(a.x, b.x, mix));
		output.setY(lerp(a.y, b.y, mix));
		output.setZ(lerp(a.z, b.z, mix));
		return output;
	}

	public static float getSpeedTicks(float speedMS) {
		return speedMS / 20F;
	}

	public static float radToDeg(float angle) {
		return angle * RAD_TO_DEG;
	}

	public static float min(float a, float b, float c) {
		return Math.min(Math.min(a, b), c);
	}
}