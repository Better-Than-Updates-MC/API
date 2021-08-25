package paulevs.corelib.math;

import java.nio.FloatBuffer;
import java.util.Locale;

public class Matrix3F {
	private static final float[][] RESULT = new float[3][3];
	private static final Matrix3F ROTATION = new Matrix3F();
	float[][] data = new float[3][3];

	public static Matrix3F identity() {
		Matrix3F matrix = new Matrix3F();
		for (int i = 0; i < 3; i++)
			matrix.data[i][i] = 1;
		return matrix;
	}

	public static Matrix3F makeRotation(Matrix3F matrix, Vec3f axis, float angle) {
		float cosT = (float) Math.cos(angle);
		float nCosT = 1 - cosT;
		float sinT = (float) Math.sin(angle);
		float xy = axis.getX() * axis.getY();
		float xz = axis.getX() * axis.getZ();
		float yz = axis.getY() * axis.getZ();

		// Row 1
		matrix.data[0][0] = cosT + nCosT * axis.getX() * axis.getX();
		matrix.data[1][0] = nCosT * xy - sinT * axis.getZ();
		matrix.data[2][0] = nCosT * xz + sinT * axis.getY();

		// Row 2
		matrix.data[0][1] = nCosT * xy + sinT * axis.getZ();
		matrix.data[1][1] = cosT + nCosT * axis.getY() * axis.getY();
		matrix.data[2][1] = nCosT * yz - sinT * axis.getX();

		// Row 3
		matrix.data[0][2] = nCosT * xz - sinT * axis.getY();
		matrix.data[1][2] = nCosT * yz + sinT * axis.getX();
		matrix.data[2][2] = cosT + nCosT * axis.getZ() * axis.getZ();

		return matrix;
	}

	public Matrix3F makeRotation(Vec3f axis, float angle) {
		if (angle == 0) return identity();

		float cosT = (float) Math.cos(angle);
		float nCosT = 1 - cosT;
		float sinT = (float) Math.sin(angle);
		float xy = axis.getX() * axis.getY();
		float xz = axis.getX() * axis.getZ();
		float yz = axis.getY() * axis.getZ();

		// Row 1
		data[0][0] = cosT + nCosT * axis.getX() * axis.getX();
		data[1][0] = nCosT * xy - sinT * axis.getZ();
		data[2][0] = nCosT * xz + sinT * axis.getY();

		// Row 2
		data[0][1] = nCosT * xy + sinT * axis.getZ();
		data[1][1] = cosT + nCosT * axis.getY() * axis.getY();
		data[2][1] = nCosT * yz - sinT * axis.getX();

		// Row 3
		data[0][2] = nCosT * xz - sinT * axis.getY();
		data[1][2] = nCosT * yz + sinT * axis.getX();
		data[2][2] = cosT + nCosT * axis.getZ() * axis.getZ();

		return this;
	}

	public Matrix3F setIdentity() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				data[i][j] = i == j ? 1 : 0;
		return this;
	}

	public Matrix3F scale(float scale) {
		for (int x = 0; x < 3; x++)
			for (int y = 0; y < 3; y++)
				data[x][y] *= scale;
		return this;
	}

	public Matrix3F rotate(Vec3f axis, float angle) {
		if (angle == 0) return this;
		makeRotation(ROTATION, axis, angle);
		return this.multiple(ROTATION);
	}

	public Vec3f multiple(Vec3f input) {
		float x = input.getX();
		float y = input.getY();
		float z = input.getZ();

		input.setX(data[0][0] * x + data[1][0] * y + data[2][0] * z);
		input.setY(data[0][1] * x + data[1][1] * y + data[2][1] * z);
		input.setZ(data[0][2] * x + data[1][2] * y + data[2][2] * z);

		return input;
	}

	public Matrix3F multiple(Matrix3F matrix) {
		for (int x = 0; x < 3; x++)
			for (int y = 0; y < 3; y++) {
				RESULT[x][y] = 0;
				for (int i = 0; i < 3; i++)
					RESULT[x][y] += this.data[i][y] * matrix.data[x][i];
			}
		for (int x = 0; x < 3; x++)
			for (int y = 0; y < 3; y++)
				data[x][y] = RESULT[x][y];
		return this;
	}

	@Override
	public String toString() {
		return String.format(Locale.ROOT, "[%f, %f, %f]\n[%f, %f, %f]\n[%f, %f, %f]", data[0][0], data[1][0],
				data[2][0], data[0][1], data[1][1], data[2][1], data[0][2], data[1][2], data[2][2]);
	}

	public float getValue(int x, int y) {
		return data[x][y];
	}

	public void setValue(int x, int y, float value) {
		data[x][y] = value;
	}

	public void putToBuffer(FloatBuffer buffer) {
		for (int i = 0; i < 3; i++) {
			buffer.put(data[0][i]);
			buffer.put(data[1][i]);
			buffer.put(data[2][i]);
			buffer.put(0);
		}
		for (int i = 0; i < 3; i++)
			buffer.put(0);
		buffer.put(1);
		buffer.flip();
	}

	public Matrix3F set(Matrix3F worldTransform) {
		for (int x = 0; x < 3; x++)
			for (int y = 0; y < 3; y++)
				data[x][y] = worldTransform.data[x][y];
		return this;
	}
}