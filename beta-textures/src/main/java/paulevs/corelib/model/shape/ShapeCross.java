package paulevs.corelib.model.shape;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;

public class ShapeCross extends Shape {
	private float scaleWidth;
	private float scaleHeight;

	public ShapeCross(float scaleWidth, float scaleHeight) {
		this.scaleWidth = scaleWidth * 0.5F;
		this.scaleHeight = scaleHeight * 0.5F;
	}

	public ShapeCross() {
		this(0.9F, 1);
	}

	@Override
	public void render() {
		double u1, u2, v1, v2;

		u1 = uv.getStart().getX();
		u2 = uv.getEnd().getX();

		v1 = uv.getStart().getY();
		v2 = uv.getEnd().getY();

		double x1 = 0.5 - scaleWidth + RENDER_POS.getX();
		double x2 = 0.5 + scaleWidth + RENDER_POS.getX();

		double y1 = 0.5 - scaleHeight + RENDER_POS.getY();
		double y2 = 0.5 + scaleHeight + RENDER_POS.getY();

		double z1 = 0.5 - scaleWidth + RENDER_POS.getZ();
		double z2 = 0.5 + scaleWidth + RENDER_POS.getZ();

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

		Tessellator.INSTANCE.color(light * red, light * green, light * blue);

		Tessellator.INSTANCE.vertex(x1, y2, z1, u1, v1);
		Tessellator.INSTANCE.vertex(x1, y1, z1, u1, v2);
		Tessellator.INSTANCE.vertex(x2, y1, z2, u2, v2);
		Tessellator.INSTANCE.vertex(x2, y2, z2, u2, v1);
		Tessellator.INSTANCE.vertex(x2, y2, z2, u1, v1);
		Tessellator.INSTANCE.vertex(x2, y1, z2, u1, v2);
		Tessellator.INSTANCE.vertex(x1, y1, z1, u2, v2);
		Tessellator.INSTANCE.vertex(x1, y2, z1, u2, v1);
		Tessellator.INSTANCE.vertex(x1, y2, z2, u1, v1);
		Tessellator.INSTANCE.vertex(x1, y1, z2, u1, v2);
		Tessellator.INSTANCE.vertex(x2, y1, z1, u2, v2);
		Tessellator.INSTANCE.vertex(x2, y2, z1, u2, v1);
		Tessellator.INSTANCE.vertex(x2, y2, z1, u1, v1);
		Tessellator.INSTANCE.vertex(x2, y1, z1, u1, v2);
		Tessellator.INSTANCE.vertex(x1, y1, z2, u2, v2);
		Tessellator.INSTANCE.vertex(x1, y2, z2, u2, v1);
	}
}