package paulevs.corelib.model.shape;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.GameRenderer;

@Deprecated
public class ShapeBoxVanilla extends Shape {
	private float minX;
	private float maxX;
	private float minY;
	private float maxY;
	private float minZ;
	private float maxZ;

	public ShapeBoxVanilla(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;

		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
	}

	public ShapeBoxVanilla() {
		this(0, 0, 0, 1, 1, 1);
	}

	public void reshape(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;

		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
	}

	@Override
	public void render() {
		block.setBoundingBox(minX + OFFSET.getX(), minY + OFFSET.getY(), minZ + OFFSET.getZ(), maxX + OFFSET.getX(),
				maxY + OFFSET.getY(), maxZ + OFFSET.getZ());

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

		if (Minecraft.isSmoothLightingEnabled())
			renderer.method_50(block, POS.x, POS.y, POS.z, red, green, blue);
		else
			renderer.method_58(block, POS.x, POS.y, POS.z, red, green, blue);
	}
}