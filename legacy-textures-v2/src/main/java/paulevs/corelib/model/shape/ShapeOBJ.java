package paulevs.corelib.model.shape;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import paulevs.corelib.math.MHelper;
import paulevs.corelib.math.Vec3f;
import paulevs.corelib.texture.UVPair;

public class ShapeOBJ extends Shape {
	private final float[] data;
	private final float[] light;

	public ShapeOBJ(String model) {
		List<List<Integer>> groups = new ArrayList<>();
		List<Float> vertex = new ArrayList<>();
		List<Float> uvs = new ArrayList<>();

		try {
			InputStream input = ShapeOBJ.class.getResourceAsStream(model);
			if (input != null) {
				InputStreamReader streamReader = new InputStreamReader(input);
				BufferedReader reader = new BufferedReader(streamReader);
				String string;

				while ((string = reader.readLine()) != null) {
					if (string.startsWith("vt")) {
						String[] uv = string.split(" ");
						uvs.add(Float.parseFloat(uv[1]));
						uvs.add(Float.parseFloat(uv[2]));
					}
					else if (string.startsWith("v")) {
						String[] vert = string.split(" ");
						for (int i = 1; i < 4; i++)
							vertex.add(Float.parseFloat(vert[i]));
					}
					else if (string.startsWith("f")) {
						List<Integer> list = new ArrayList<>();
						List<Integer> uvList = new ArrayList<>();
						String[] members = string.split(" ");
						if (members.length < 5) {
							System.out.println("Only qads in OBJ are supported!");
						}
						else {
							for (int i = 1; i < 5; i++) {
								String member = members[i];

								if (member.contains("/")) {
									String[] sub = member.split("/");
									list.add(Integer.parseInt(sub[0]) - 1); // Vertex
									uvList.add(Integer.parseInt(sub[1]) - 1); // UV
								}
								else {
									list.add(Integer.parseInt(member) - 1); // Vertex
								}
							}
						}
						list.addAll(uvList);
						groups.add(list);
					}
				}

				reader.close();
				streamReader.close();
				input.close();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		boolean hasUV = !uvs.isEmpty();
		data = new float[groups.size() * 20];
		light = new float[groups.size()];

		int i = 0;
		int n = 0;
		Vec3f up = new Vec3f(0, 1, 0);
		for (List<Integer> quad : groups) {
			Vec3f p0 = null;
			Vec3f p1 = null;
			Vec3f p2 = null;

			for (int j = 0; j < 4; j++) {
				int vi = quad.get(j) * 3;
				data[i] = vertex.get(vi);
				data[i + 1] = vertex.get(vi + 1);
				data[i + 2] = vertex.get(vi + 2);

				if (j == 0) {
					p1 = new Vec3f(data[i], data[i + 1], data[i + 2]);
				}
				else if (j == 1) {
					p0 = new Vec3f(data[i], data[i + 1], data[i + 2]);
				}
				else if (j == 2) {
					p2 = new Vec3f(data[i], data[i + 1], data[i + 2]);
				}

				if (hasUV) {
					vi = quad.get(j + 4) * 2;
					data[i + 3] = uvs.get(vi);
					data[i + 4] = uvs.get(vi + 1);
				}
				else {
					data[i + 3] = 0;
					data[i + 4] = 0;
				}

				i += 5;
			}

			final String error = "Illegal null found";

			Vec3f norm = Objects.requireNonNull(p2, error).subtract(
					Objects.requireNonNull(p0, error))
					.crossNew(p1.subtract(p0));
			float angle = up.angle(norm) / MHelper.PI;
			float light = 1 - angle * 0.5F;

			this.light[n++] = light;
		}
	}

	@Override
	public void render() {
		float red = (color >> 16 & 255) / 255F;
		float green = (color >> 8 & 255) / 255F;
		float blue = (color & 255) / 255F;

		if (GameRenderer.field_2340) {
			float newRed = (red * 30.0F + green * 59.0F + blue * 11.0F) / 100.0F;
			float newGreen = (red * 30.0F + green * 70.0F) / 100.0F;
			float newBlue = (red * 30.0F + blue * 70.0F) / 100.0F;

			red = newRed;
			green = newGreen;
			blue = newBlue;
		}

		red *= Shape.light;
		green *= Shape.light;
		blue *= Shape.light;

		for (int i = 0; i < data.length; i += 5) {
			float vx = data[i];
			float vy = data[i + 1];
			float vz = data[i + 2];
			float u = data[i + 3];
			float v = data[i + 4];
			if (i % 20 == 0) {
				float l = this.light[i / 20];
				Tessellator.INSTANCE.color(l * red, l * green, l * blue);
			}
			Tessellator.INSTANCE.vertex(vx + RENDER_POS.getX(), vy + RENDER_POS.getY(), vz + RENDER_POS.getZ(), u, v);
		}
	}

	public void changeUV(UVPair pair) {
		float du = pair.getEnd().getX() - pair.getStart().getX();
		float dv = pair.getEnd().getY() - pair.getStart().getY();
		for (int i = 0; i < data.length; i += 5) {
			data[i + 3] = data[i + 3] * du + pair.getStart().getX();
			data[i + 4] = data[i + 4] * dv + pair.getStart().getY();
		}
	}
}
