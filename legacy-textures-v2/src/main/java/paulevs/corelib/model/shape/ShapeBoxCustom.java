package paulevs.corelib.model.shape;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.block.Block;
import paulevs.corelib.CoreLib;
import paulevs.corelib.math.BlockFacing;
import paulevs.corelib.math.Vec3f;

public class ShapeBoxCustom extends Shape {
	private static int field_83 = -1;
	private static boolean field_84 = false;
	private static boolean field_67 = true;
	private static int field_86 = 0;
	private static int field_87 = 0;
	private static int field_88 = 0;
	private static int field_89 = 0;
	private static int field_90 = 0;
	private static int field_91 = 0;
	private static boolean hasColor;
	private static float field_93;
	private static float field_94;
	private static float field_95;
	private static float field_96;
	private static float field_97;
	private static float field_98;
	private static float field_99;
	private static float field_100;
	private static float field_101;
	private static float field_102;
	private static float field_103;
	private static float field_104;
	private static float field_105;
	private static float field_41;
	private static float field_42;
	private static float field_43;
	private static float field_44;
	private static float field_45;
	private static float field_46;
	private static float field_47;
	private static float field_48;
	private static float field_49;
	private static float field_50;
	private static float field_51;
	private static float field_52;
	private static float field_53;
	private static float field_54;
	private static float red1;
	private static float red2;
	private static float red3;
	private static float red4;
	private static float green1;
	private static float green2;
	private static float green3;
	private static float green4;
	private static float blue1;
	private static float blue2;
	private static float blue3;
	private static float blue4;
	private static boolean field_69;
	private static boolean field_70;
	private static boolean field_71;
	private static boolean field_72;
	private static boolean field_73;
	private static boolean field_74;
	private static boolean field_75;
	private static boolean field_76;
	private static boolean field_77;
	private static boolean field_78;
	private static boolean field_79;
	private static boolean field_80;

	private float minX;
	private float maxX;
	private float minY;
	private float maxY;
	private float minZ;
	private float maxZ;

	private float size;

	private static final Vec3f POS_MIN = new Vec3f();
	private static final Vec3f POS_MAX = new Vec3f();

	public ShapeBoxCustom(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;

		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
	}

	public ShapeBoxCustom() {
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
		POS_MIN.set(OFFSET).add(minX, minY, minZ);
		POS_MAX.set(OFFSET).add(maxX, maxY, maxZ);

		float red = (float) ((color >> 16) & 255) / 255F;
		float green = (float) ((color >> 8) & 255) / 255F;
		float blue = (float) (color & 255) / 255F;

		if (GameRenderer.field_2340) {
			float newRed = (red * 30.0F + green * 59.0F + blue * 11.0F) / 100.0F;
			float newGreen = (red * 30.0F + green * 70.0F) / 100.0F;
			float newBlue = (red * 30.0F + blue * 70.0F) / 100.0F;
			red = newRed;
			green = newGreen;
			blue = newBlue;
		}

		size = CoreLib.getAtlasSize();

		if (Minecraft.isSmoothLightingEnabled()) {
			smoothBox(block, POS.x, POS.y, POS.z, red, green, blue);
		}
		else {
			flatBox(block, POS.x, POS.y, POS.z, red, green, blue);
		}
	}

	private void flatBox(Block block, int posX, int posY, int posZ, float red, float green, float blue) {
		hasColor = false;
		Tessellator tesselator = Tessellator.INSTANCE;
		float var10 = 0.5F;
		float var11 = 1.0F;
		float var12 = 0.8F;
		float var13 = 0.6F;
		float var14 = var11 * red;
		float var15 = var11 * green;
		float var16 = var11 * blue;
		float var17 = var10 * red;
		float var18 = var12 * red;
		float colorR = var13 * red;
		float var20 = var10 * green;
		float var21 = var12 * green;
		float colorG = var13 * green;
		float var23 = var10 * blue;
		float var24 = var12 * blue;
		float colorB = var13 * blue;

		float centralLight = block.getBrightness(blockView, posX, posY, posZ);
		if (shouldRenderFace(BlockFacing.NEG_Y)) {
			float var27 = block.getBrightness(blockView, posX, posY - 1, posZ);
			tesselator.color(var17 * var27, var20 * var27, var23 * var27);
			this.method_46(block, (double) posX, (double) posY, (double) posZ,
					block.getTextureForSide(blockView, posX, posY, posZ, 0));
		}

		if (shouldRenderFace(BlockFacing.POS_Y)) {
			float var27 = block.getBrightness(blockView, posX, posY + 1, posZ);
			if (POS_MAX.getY() != 1.0D && !block.material.isLiquid()) {
				var27 = centralLight;
			}

			tesselator.color(var14 * var27, var15 * var27, var16 * var27);
			this.method_55(block, (double) posX, (double) posY, (double) posZ,
					block.getTextureForSide(blockView, posX, posY, posZ, 1));
		}

		if (shouldRenderFace(BlockFacing.NEG_Z)) {
			float var27 = block.getBrightness(blockView, posX, posY, posZ - 1);
			if (POS_MIN.getZ() > 0.0D) {
				var27 = centralLight;
			}

			tesselator.color(var18 * var27, var21 * var27, var24 * var27);
			int var28 = block.getTextureForSide(blockView, posX, posY, posZ, 2);
			this.method_61(block, (double) posX, (double) posY, (double) posZ, var28);
			if (field_67 && var28 == 3 && field_83 < 0) {
				tesselator.color(var18 * var27 * red, var21 * var27 * green, var24 * var27 * blue);
				this.method_61(block, (double) posX, (double) posY, (double) posZ, 38);
			}
		}

		if (shouldRenderFace(BlockFacing.POS_Z)) {
			float var27 = block.getBrightness(blockView, posX, posY, posZ + 1);
			if (POS_MAX.getZ() < 1.0D) {
				var27 = centralLight;
			}

			tesselator.color(var18 * var27, var21 * var27, var24 * var27);
			int var28 = block.getTextureForSide(blockView, posX, posY, posZ, 3);
			this.method_65(block, (double) posX, (double) posY, (double) posZ, var28);
			if (field_67 && var28 == 3 && field_83 < 0) {
				tesselator.color(var18 * var27 * red, var21 * var27 * green, var24 * var27 * blue);
				this.method_65(block, (double) posX, (double) posY, (double) posZ, 38);
			}
		}

		if (shouldRenderFace(BlockFacing.NEG_X)) {
			float light = block.getBrightness(blockView, posX - 1, posY, posZ);
			if (POS_MIN.getX() > 0.0D) {
				light = centralLight;
			}

			tesselator.color(colorR * light, colorG * light, colorB * light);
			int var28 = block.getTextureForSide(blockView, posX, posY, posZ, 4);
			this.method_67(block, (double) posX, (double) posY, (double) posZ, var28);
			if (field_67 && var28 == 3 && field_83 < 0) {
				tesselator.color(colorR * light * red, colorG * light * green, colorB * light * blue);
				this.method_67(block, (double) posX, (double) posY, (double) posZ, 38);
			}
		}

		if (shouldRenderFace(BlockFacing.POS_X)) {
			float var27 = block.getBrightness(blockView, posX + 1, posY, posZ);
			if (POS_MAX.getX() < 1.0D) {
				var27 = centralLight;
			}

			tesselator.color(colorR * var27, colorG * var27, colorB * var27);
			int var28 = block.getTextureForSide(blockView, posX, posY, posZ, 5);
			this.method_69(block, (double) posX, (double) posY, (double) posZ, var28);
			if (field_67 && var28 == 3 && field_83 < 0) {
				tesselator.color(colorR * var27 * red, colorG * var27 * green, colorB * var27 * blue);
				this.method_69(block, (double) posX, (double) posY, (double) posZ, 38);
			}
		}
	}

	private void smoothBox(Block block, int posX, int posY, int posZ, float red, float green, float blue) {
		hasColor = true;
		float var9 = field_93;
		float var10 = field_93;
		float var11 = field_93;
		float var12 = field_93;
		int var13 = 1;
		int var14 = 1;
		int var15 = 1;
		int var16 = 1;
		int var17 = 1;
		int var18 = 1;
		field_93 = block.getBrightness(blockView, posX, posY, posZ);
		field_94 = block.getBrightness(blockView, posX - 1, posY, posZ);
		field_95 = block.getBrightness(blockView, posX, posY - 1, posZ);
		field_96 = block.getBrightness(blockView, posX, posY, posZ - 1);
		field_97 = block.getBrightness(blockView, posX + 1, posY, posZ);
		field_98 = block.getBrightness(blockView, posX, posY + 1, posZ);
		field_99 = block.getBrightness(blockView, posX, posY, posZ + 1);
		field_70 = Block.ALLOWS_GRASS_UNDER[blockView.getBlockId(posX + 1, posY + 1, posZ)];
		field_78 = Block.ALLOWS_GRASS_UNDER[blockView.getBlockId(posX + 1, posY - 1, posZ)];
		field_74 = Block.ALLOWS_GRASS_UNDER[blockView.getBlockId(posX + 1, posY, posZ + 1)];
		field_76 = Block.ALLOWS_GRASS_UNDER[blockView.getBlockId(posX + 1, posY, posZ - 1)];
		field_71 = Block.ALLOWS_GRASS_UNDER[blockView.getBlockId(posX - 1, posY + 1, posZ)];
		field_79 = Block.ALLOWS_GRASS_UNDER[blockView.getBlockId(posX - 1, posY - 1, posZ)];
		field_73 = Block.ALLOWS_GRASS_UNDER[blockView.getBlockId(posX - 1, posY, posZ - 1)];
		field_75 = Block.ALLOWS_GRASS_UNDER[blockView.getBlockId(posX - 1, posY, posZ + 1)];
		field_72 = Block.ALLOWS_GRASS_UNDER[blockView.getBlockId(posX, posY + 1, posZ + 1)];
		field_69 = Block.ALLOWS_GRASS_UNDER[blockView.getBlockId(posX, posY + 1, posZ - 1)];
		field_80 = Block.ALLOWS_GRASS_UNDER[blockView.getBlockId(posX, posY - 1, posZ + 1)];
		field_77 = Block.ALLOWS_GRASS_UNDER[blockView.getBlockId(posX, posY - 1, posZ - 1)];
		if (block.texture == 3) {
			var18 = 0;
			var17 = 0;
			var16 = 0;
			var15 = 0;
			var13 = 0;
		}

		if (field_83 >= 0) {
			var18 = 0;
			var17 = 0;
			var16 = 0;
			var15 = 0;
			var13 = 0;
		}

		if (shouldRenderFace(BlockFacing.NEG_Y)) {
			--posY;
			field_101 = block.getBrightness(blockView, posX - 1, posY, posZ);
			field_103 = block.getBrightness(blockView, posX, posY, posZ - 1);
			field_104 = block.getBrightness(blockView, posX, posY, posZ + 1);
			field_41 = block.getBrightness(blockView, posX + 1, posY, posZ);
			if (!field_77 && !field_79) {
				field_100 = field_101;
			}
			else {
				field_100 = block.getBrightness(blockView, posX - 1, posY, posZ - 1);
			}

			if (!field_80 && !field_79) {
				field_102 = field_101;
			}
			else {
				field_102 = block.getBrightness(blockView, posX - 1, posY, posZ + 1);
			}

			if (!field_77 && !field_78) {
				field_105 = field_41;
			}
			else {
				field_105 = block.getBrightness(blockView, posX + 1, posY, posZ - 1);
			}

			if (!field_80 && !field_78) {
				field_42 = field_41;
			}
			else {
				field_42 = block.getBrightness(blockView, posX + 1, posY, posZ + 1);
			}

			++posY;
			var9 = (field_102 + field_101 + field_104 + field_95) / 4.0F;
			var12 = (field_104 + field_95 + field_42 + field_41) / 4.0F;
			var11 = (field_95 + field_103 + field_41 + field_105) / 4.0F;
			var10 = (field_101 + field_100 + field_95 + field_103) / 4.0F;

			red1 = red2 = red3 = red4 = (var13 != 0 ? red : 1.0F) * 0.5F;
			green1 = green2 = green3 = green4 = (var13 != 0 ? green : 1.0F) * 0.5F;
			blue1 = blue2 = blue3 = blue4 = (var13 != 0 ? blue : 1.0F) * 0.5F;
			red1 *= var9;
			green1 *= var9;
			blue1 *= var9;
			red2 *= var10;
			green2 *= var10;
			blue2 *= var10;
			red3 *= var11;
			green3 *= var11;
			blue3 *= var11;
			red4 *= var12;
			green4 *= var12;
			blue4 *= var12;
			this.method_46(block, (double) posX, (double) posY, (double) posZ, block.getTextureForSide(blockView, posX, posY, posZ, 0));
		}

		if (shouldRenderFace(BlockFacing.POS_Y)) {
			++posY;
			field_44 = block.getBrightness(blockView, posX - 1, posY, posZ);
			field_48 = block.getBrightness(blockView, posX + 1, posY, posZ);
			field_46 = block.getBrightness(blockView, posX, posY, posZ - 1);
			field_49 = block.getBrightness(blockView, posX, posY, posZ + 1);
			if (!field_69 && !field_71) {
				field_43 = field_44;
			}
			else {
				field_43 = block.getBrightness(blockView, posX - 1, posY, posZ - 1);
			}

			if (!field_69 && !field_70) {
				field_47 = field_48;
			}
			else {
				field_47 = block.getBrightness(blockView, posX + 1, posY, posZ - 1);
			}

			if (!field_72 && !field_71) {
				field_45 = field_44;
			}
			else {
				field_45 = block.getBrightness(blockView, posX - 1, posY, posZ + 1);
			}

			if (!field_72 && !field_70) {
				field_50 = field_48;
			}
			else {
				field_50 = block.getBrightness(blockView, posX + 1, posY, posZ + 1);
			}

			--posY;
			var12 = (field_45 + field_44 + field_49 + field_98) / 4.0F;
			var9 = (field_49 + field_98 + field_50 + field_48) / 4.0F;
			var10 = (field_98 + field_46 + field_48 + field_47) / 4.0F;
			var11 = (field_44 + field_43 + field_98 + field_46) / 4.0F;

			red1 = red2 = red3 = red4 = var14 != 0 ? red : 1.0F;
			green1 = green2 = green3 = green4 = var14 != 0 ? green : 1.0F;
			blue1 = blue2 = blue3 = blue4 = var14 != 0 ? blue : 1.0F;
			red1 *= var9;
			green1 *= var9;
			blue1 *= var9;
			red2 *= var10;
			green2 *= var10;
			blue2 *= var10;
			red3 *= var11;
			green3 *= var11;
			blue3 *= var11;
			red4 *= var12;
			green4 *= var12;
			blue4 *= var12;
			this.method_55(block, (double) posX, (double) posY, (double) posZ, block.getTextureForSide(blockView, posX, posY, posZ, 1));
		}

		if (shouldRenderFace(BlockFacing.NEG_Z)) {
			--posZ;
			field_51 = block.getBrightness(blockView, posX - 1, posY, posZ);
			field_103 = block.getBrightness(blockView, posX, posY - 1, posZ);
			field_46 = block.getBrightness(blockView, posX, posY + 1, posZ);
			field_52 = block.getBrightness(blockView, posX + 1, posY, posZ);
			if (!field_73 && !field_77) {
				field_100 = field_51;
			}
			else {
				field_100 = block.getBrightness(blockView, posX - 1, posY - 1, posZ);
			}

			if (!field_73 && !field_69) {
				field_43 = field_51;
			}
			else {
				field_43 = block.getBrightness(blockView, posX - 1, posY + 1, posZ);
			}

			if (!field_76 && !field_77) {
				field_105 = field_52;
			}
			else {
				field_105 = block.getBrightness(blockView, posX + 1, posY - 1, posZ);
			}

			if (!field_76 && !field_69) {
				field_47 = field_52;
			}
			else {
				field_47 = block.getBrightness(blockView, posX + 1, posY + 1, posZ);
			}

			++posZ;
			var9 = (field_51 + field_43 + field_96 + field_46) / 4.0F;
			var10 = (field_96 + field_46 + field_52 + field_47) / 4.0F;
			var11 = (field_103 + field_96 + field_105 + field_52) / 4.0F;
			var12 = (field_100 + field_51 + field_103 + field_96) / 4.0F;

			red1 = red2 = red3 = red4 = (var15 != 0 ? red : 1.0F) * 0.8F;
			green1 = green2 = green3 = green4 = (var15 != 0 ? green : 1.0F) * 0.8F;
			blue1 = blue2 = blue3 = blue4 = (var15 != 0 ? blue : 1.0F) * 0.8F;
			red1 *= var9;
			green1 *= var9;
			blue1 *= var9;
			red2 *= var10;
			green2 *= var10;
			blue2 *= var10;
			red3 *= var11;
			green3 *= var11;
			blue3 *= var11;
			red4 *= var12;
			green4 *= var12;
			blue4 *= var12;
			int var19 = block.getTextureForSide(blockView, posX, posY, posZ, 2);
			this.method_61(block, (double) posX, (double) posY, (double) posZ, var19);
			if (field_67 && var19 == 3 && field_83 < 0) {
				red1 *= red;
				red2 *= red;
				red3 *= red;
				red4 *= red;
				green1 *= green;
				green2 *= green;
				green3 *= green;
				green4 *= green;
				blue1 *= blue;
				blue2 *= blue;
				blue3 *= blue;
				blue4 *= blue;
				this.method_61(block, (double) posX, (double) posY, (double) posZ, 38);
			}
		}

		if (shouldRenderFace(BlockFacing.POS_Z)) {
			++posZ;
			field_53 = block.getBrightness(blockView, posX - 1, posY, posZ);
			field_54 = block.getBrightness(blockView, posX + 1, posY, posZ);
			field_104 = block.getBrightness(blockView, posX, posY - 1, posZ);
			field_49 = block.getBrightness(blockView, posX, posY + 1, posZ);
			if (!field_75 && !field_80) {
				field_102 = field_53;
			}
			else {
				field_102 = block.getBrightness(blockView, posX - 1, posY - 1, posZ);
			}

			if (!field_75 && !field_72) {
				field_45 = field_53;
			}
			else {
				field_45 = block.getBrightness(blockView, posX - 1, posY + 1, posZ);
			}

			if (!field_74 && !field_80) {
				field_42 = field_54;
			}
			else {
				field_42 = block.getBrightness(blockView, posX + 1, posY - 1, posZ);
			}

			if (!field_74 && !field_72) {
				field_50 = field_54;
			}
			else {
				field_50 = block.getBrightness(blockView, posX + 1, posY + 1, posZ);
			}

			--posZ;
			var9 = (field_53 + field_45 + field_99 + field_49) / 4.0F;
			var12 = (field_99 + field_49 + field_54 + field_50) / 4.0F;
			var11 = (field_104 + field_99 + field_42 + field_54) / 4.0F;
			var10 = (field_102 + field_53 + field_104 + field_99) / 4.0F;

			red1 = red2 = red3 = red4 = (var16 != 0 ? red : 1.0F) * 0.8F;
			green1 = green2 = green3 = green4 = (var16 != 0 ? green : 1.0F) * 0.8F;
			blue1 = blue2 = blue3 = blue4 = (var16 != 0 ? blue : 1.0F) * 0.8F;
			red1 *= var9;
			green1 *= var9;
			blue1 *= var9;
			red2 *= var10;
			green2 *= var10;
			blue2 *= var10;
			red3 *= var11;
			green3 *= var11;
			blue3 *= var11;
			red4 *= var12;
			green4 *= var12;
			blue4 *= var12;
			int var19 = block.getTextureForSide(blockView, posX, posY, posZ, 3);
			this.method_65(block, (double) posX, (double) posY, (double) posZ,
					block.getTextureForSide(blockView, posX, posY, posZ, 3));
			if (field_67 && var19 == 3 && field_83 < 0) {
				red1 *= red;
				red2 *= red;
				red3 *= red;
				red4 *= red;
				green1 *= green;
				green2 *= green;
				green3 *= green;
				green4 *= green;
				blue1 *= blue;
				blue2 *= blue;
				blue3 *= blue;
				blue4 *= blue;
				this.method_65(block, (double) posX, (double) posY, (double) posZ, 38);
			}
		}

		if (shouldRenderFace(BlockFacing.NEG_X)) {
			--posX;
			field_101 = block.getBrightness(blockView, posX, posY - 1, posZ);
			field_51 = block.getBrightness(blockView, posX, posY, posZ - 1);
			field_53 = block.getBrightness(blockView, posX, posY, posZ + 1);
			field_44 = block.getBrightness(blockView, posX, posY + 1, posZ);
			if (!field_73 && !field_79) {
				field_100 = field_51;
			}
			else {
				field_100 = block.getBrightness(blockView, posX, posY - 1, posZ - 1);
			}

			if (!field_75 && !field_79) {
				field_102 = field_53;
			}
			else {
				field_102 = block.getBrightness(blockView, posX, posY - 1, posZ + 1);
			}

			if (!field_73 && !field_71) {
				field_43 = field_51;
			}
			else {
				field_43 = block.getBrightness(blockView, posX, posY + 1, posZ - 1);
			}

			if (!field_75 && !field_71) {
				field_45 = field_53;
			}
			else {
				field_45 = block.getBrightness(blockView, posX, posY + 1, posZ + 1);
			}

			++posX;
			var12 = (field_101 + field_102 + field_94 + field_53) / 4.0F;
			var9 = (field_94 + field_53 + field_44 + field_45) / 4.0F;
			var10 = (field_51 + field_94 + field_43 + field_44) / 4.0F;
			var11 = (field_100 + field_101 + field_51 + field_94) / 4.0F;

			red1 = red2 = red3 = red4 = (var17 != 0 ? red : 1.0F) * 0.6F;
			green1 = green2 = green3 = green4 = (var17 != 0 ? green : 1.0F) * 0.6F;
			blue1 = blue2 = blue3 = blue4 = (var17 != 0 ? blue : 1.0F) * 0.6F;
			red1 *= var9;
			green1 *= var9;
			blue1 *= var9;
			red2 *= var10;
			green2 *= var10;
			blue2 *= var10;
			red3 *= var11;
			green3 *= var11;
			blue3 *= var11;
			red4 *= var12;
			green4 *= var12;
			blue4 *= var12;
			int var19 = block.getTextureForSide(blockView, posX, posY, posZ, 4);
			this.method_67(block, (double) posX, (double) posY, (double) posZ, var19);
			if (field_67 && var19 == 3 && field_83 < 0) {
				red1 *= red;
				red2 *= red;
				red3 *= red;
				red4 *= red;
				green1 *= green;
				green2 *= green;
				green3 *= green;
				green4 *= green;
				blue1 *= blue;
				blue2 *= blue;
				blue3 *= blue;
				blue4 *= blue;
				this.method_67(block, (double) posX, (double) posY, (double) posZ, 38);
			}
		}

		if (shouldRenderFace(BlockFacing.POS_X)) {
			++posX;
			field_41 = block.getBrightness(blockView, posX, posY - 1, posZ);
			field_52 = block.getBrightness(blockView, posX, posY, posZ - 1);
			field_54 = block.getBrightness(blockView, posX, posY, posZ + 1);
			field_48 = block.getBrightness(blockView, posX, posY + 1, posZ);
			if (!field_78 && !field_76) {
				field_105 = field_52;
			}
			else {
				field_105 = block.getBrightness(blockView, posX, posY - 1, posZ - 1);
			}

			if (!field_78 && !field_74) {
				field_42 = field_54;
			}
			else {
				field_42 = block.getBrightness(blockView, posX, posY - 1, posZ + 1);
			}

			if (!field_70 && !field_76) {
				field_47 = field_52;
			}
			else {
				field_47 = block.getBrightness(blockView, posX, posY + 1, posZ - 1);
			}

			if (!field_70 && !field_74) {
				field_50 = field_54;
			}
			else {
				field_50 = block.getBrightness(blockView, posX, posY + 1, posZ + 1);
			}

			--posX;
			var9 = (field_41 + field_42 + field_97 + field_54) / 4.0F;
			var12 = (field_97 + field_54 + field_48 + field_50) / 4.0F;
			var11 = (field_52 + field_97 + field_47 + field_48) / 4.0F;
			var10 = (field_105 + field_41 + field_52 + field_97) / 4.0F;

			red1 = red2 = red3 = red4 = (var18 != 0 ? red : 1.0F) * 0.6F;
			green1 = green2 = green3 = green4 = (var18 != 0 ? green : 1.0F) * 0.6F;
			blue1 = blue2 = blue3 = blue4 = (var18 != 0 ? blue : 1.0F) * 0.6F;
			red1 *= var9;
			green1 *= var9;
			blue1 *= var9;
			red2 *= var10;
			green2 *= var10;
			blue2 *= var10;
			red3 *= var11;
			green3 *= var11;
			blue3 *= var11;
			red4 *= var12;
			green4 *= var12;
			blue4 *= var12;
			int var19 = block.getTextureForSide(blockView, posX, posY, posZ, 5);
			this.method_69(block, (double) posX, (double) posY, (double) posZ, var19);
			if (field_67 && var19 == 3 && field_83 < 0) {
				red1 *= red;
				red2 *= red;
				red3 *= red;
				red4 *= red;
				green1 *= green;
				green2 *= green;
				green3 *= green;
				green4 *= green;
				blue1 *= blue;
				blue2 *= blue;
				blue3 *= blue;
				blue4 *= blue;
				this.method_69(block, (double) posX, (double) posY, (double) posZ, 38);
			}
		}

		hasColor = false;
	}

	public void method_69(Block block, double d, double d1, double d2, int i) {
		Tessellator tesselator = Tessellator.INSTANCE;
		if (field_83 >= 0) {
			i = field_83;
		}

		float var10 = uv.getStart().getX();
		float var11 = uv.getStart().getY();
		float dx = uv.getEnd().getX() - uv.getStart().getX();
		float dy = uv.getEnd().getY() - uv.getStart().getY();
		double var12 = var10 + POS_MIN.getZ() * dx;
		double var14 = var10 + POS_MAX.getZ() * dx;
		double var16 = (var11 + dy) - POS_MAX.getY() * dy;
		double var18 = (var11 + dy) - POS_MIN.getY() * dy;
		if (field_84) {
			double var20 = var12;
			var12 = var14;
			var14 = var20;
		}

		if (POS_MIN.getZ() < 0.0D || POS_MAX.getZ() > 1.0D) {
			var12 = var10;
			var14 = var10 + dx;
		}

		if (POS_MIN.getY() < 0.0D || POS_MAX.getY() > 1.0D) {
			var16 = var11;
			var18 = var11 + dy;
		}

		double var42 = var14;
		double var22 = var12;
		double var24 = var16;
		double var26 = var18;
		if (field_88 == 2) {
			var12 = var10 + POS_MIN.getY() * dx;
			var16 = (var11 + dy) - POS_MIN.getZ() * dy;
			var14 = var10 + POS_MAX.getY() * dx;
			var18 = (var11 + 16) - POS_MAX.getZ() * dy;
			var24 = var16;
			var26 = var18;
			var42 = var12;
			var22 = var14;
			var16 = var18;
			var18 = var16;
		}
		else if (field_88 == 1) {
			var12 = (var10 + dx) - POS_MAX.getY() * dx;
			var16 = var11 + POS_MAX.getZ() * dy;
			var14 = (var10 + dx) - POS_MIN.getY() * dx;
			var18 = var11 + POS_MIN.getZ() * dy;
			var42 = var14;
			var22 = var12;
			var12 = var14;
			var14 = var12;
			var24 = var18;
			var26 = var16;
		}
		else if (field_88 == 3) {
			var12 = (var10 + dx) - POS_MIN.getZ() * dx;
			var14 = (var10 + dx) - POS_MAX.getZ() * dx;
			var16 = var11 + POS_MAX.getY() * dy;
			var18 = var11 + POS_MIN.getY() * dy;
			var42 = var14;
			var22 = var12;
			var24 = var16;
			var26 = var18;
		}

		double var28 = d + POS_MAX.getX();
		double var30 = d1 + POS_MIN.getY();
		double var32 = d1 + POS_MAX.getY();
		double var34 = d2 + POS_MIN.getZ();
		double var36 = d2 + POS_MAX.getZ();
		if (hasColor) {
			tesselator.color(red1, green1, blue1);
			tesselator.vertex(var28, var30, var36, var22, var26);
			tesselator.color(red2, green2, blue2);
			tesselator.vertex(var28, var30, var34, var14, var18);
			tesselator.color(red3, green3, blue3);
			tesselator.vertex(var28, var32, var34, var42, var24);
			tesselator.color(red4, green4, blue4);
			tesselator.vertex(var28, var32, var36, var12, var16);
		}
		else {
			tesselator.vertex(var28, var30, var36, var22, var26);
			tesselator.vertex(var28, var30, var34, var14, var18);
			tesselator.vertex(var28, var32, var34, var42, var24);
			tesselator.vertex(var28, var32, var36, var12, var16);
		}
	}

	public void method_46(Block block, double d, double d1, double d2, int i) {
		Tessellator tesselator = Tessellator.INSTANCE;
		if (field_83 >= 0) {
			i = field_83;
		}

		float var10 = uv.getStart().getX();
		float var11 = uv.getStart().getY();
		float dx = uv.getEnd().getX() - uv.getStart().getX();
		float dy = uv.getEnd().getY() - uv.getStart().getY();
		double u2 = var10 + POS_MIN.getX() * dx;
		double u4 = var10 + POS_MAX.getX() * dx;
		double v2 = var11 + POS_MIN.getZ() * dy;
		double v4 = var11 + POS_MAX.getZ() * dy;
		if (POS_MIN.getX() < 0.0D || POS_MAX.getX() > 1.0D) {
			u2 = var10;
			u4 = var10 + dx;
		}

		if (POS_MIN.getZ() < 0.0D || POS_MAX.getZ() > 1.0D) {
			v2 = var11;
			v4 = var11 + dy;
		}

		double u3 = u4;
		double u1 = u2;
		double v3 = v2;
		double v1 = v4;
		if (field_91 == 2) {
			u2 = var10 + POS_MIN.getZ() * dx;
			v2 = (var11 + dy) - POS_MAX.getX() * dy;
			u4 = var10 + POS_MAX.getZ() * dx;
			v4 = (var11 + dy) - POS_MIN.getX() * dy;
			v3 = v2;
			v1 = v4;
			u3 = u2;
			u1 = u4;
			v2 = v4;
			v4 = v2;
		}
		else if (field_91 == 1) {
			u2 = (var10 + dx) - POS_MAX.getZ() * dx;
			v2 = var11 + POS_MIN.getX() * dy;
			u4 = (var10 + dx) - POS_MIN.getZ() * dx;
			v4 = var11 + POS_MAX.getX() * dy;
			u3 = u4;
			u1 = u2;
			u2 = u4;
			u4 = u2;
			v3 = v4;
			v1 = v2;
		}
		else if (field_91 == 3) {
			u2 = (var10 + dx) - POS_MIN.getX() * dx;
			u4 = (var10 + dx) - POS_MAX.getX() * dx;
			v2 = (var11 + dy) - POS_MIN.getZ() * dy;
			v4 = (var11 + dy) - POS_MAX.getZ() * dy;
			u3 = u4;
			u1 = u2;
			v3 = v2;
			v1 = v4;
		}

		double x1 = d + POS_MIN.getX();
		double x2 = d + POS_MAX.getX();
		double y1 = d1 + POS_MIN.getY();
		double z1 = d2 + POS_MIN.getZ();
		double z2 = d2 + POS_MAX.getZ();
		if (hasColor) {
			tesselator.color(red1, green1, blue1);
			tesselator.vertex(x1, y1, z2, u1, v1);
			tesselator.color(red2, green2, blue2);
			tesselator.vertex(x1, y1, z1, u2, v2);
			tesselator.color(red3, green3, blue3);
			tesselator.vertex(x2, y1, z1, u3, v3);
			tesselator.color(red4, green4, blue4);
			tesselator.vertex(x2, y1, z2, u4, v4);
		}
		else {
			tesselator.vertex(x1, y1, z2, u1, v1);
			tesselator.vertex(x1, y1, z1, u2, v2);
			tesselator.vertex(x2, y1, z1, u3, v3);
			tesselator.vertex(x2, y1, z2, u4, v4);
		}
	}

	public void method_55(Block block, double d, double d1, double d2, int i) {
		Tessellator tesselator = Tessellator.INSTANCE;
		if (field_83 >= 0) {
			i = field_83;
		}

		float var10 = uv.getStart().getX();
		float var11 = uv.getStart().getY();
		float dx = uv.getEnd().getX() - uv.getStart().getX();
		float dy = uv.getEnd().getY() - uv.getStart().getY();
		double u3 = var10 + POS_MIN.getX() * dx;
		double u1 = var10 + POS_MAX.getX() * dx;
		double v3 = var11 + POS_MIN.getZ() * dy;
		double v1 = var11 + POS_MAX.getZ() * dy;
		if (POS_MIN.getX() < 0.0D || POS_MAX.getX() > 1.0D) {
			u3 = var10;
			u1 = var10 + dx;
		}

		if (POS_MIN.getZ() < 0.0D || POS_MAX.getZ() > 1.0D) {
			v3 = var11;
			v1 = var11 + dy;
		}

		double u2 = u1;
		double u4 = u3;
		double v2 = v3;
		double v4 = v1;
		if (field_90 == 1) {
			u3 = var10 + POS_MIN.getZ() * dx;
			v3 = (var11 + dy) - POS_MAX.getX() * dy;
			u1 = var10 + POS_MAX.getZ() * dx;
			v1 = (var11 + dy) - POS_MIN.getX() * dy;
			v2 = v3;
			v4 = v1;
			u2 = u3;
			u4 = u1;
			v3 = v1;
			v1 = v3;
		}
		else if (field_90 == 2) {
			u3 = (var10 + dx) - POS_MAX.getZ() * dx;
			v3 = var11 + POS_MIN.getX() * dy;
			u1 = (var10 + dx) - POS_MIN.getZ() * dx;
			v1 = var11 + POS_MAX.getX() * dy;
			u2 = u1;
			u4 = u3;
			u3 = u1;
			u1 = u3;
			v2 = v1;
			v4 = v3;
		}
		else if (field_90 == 3) {
			u3 = (var10 + dx) - POS_MIN.getX() * dx;
			u1 = (var10 + dx) - POS_MAX.getX() * dx;
			v3 = (var11 + dy) - POS_MIN.getZ() * dy;
			v1 = (var11 + dy) - POS_MAX.getZ() * dy;
			u2 = u1;
			u4 = u3;
			v2 = v3;
			v4 = v1;
		}

		double x1 = d + POS_MIN.getX();
		double x2 = d + POS_MAX.getX();
		double y2 = d1 + POS_MAX.getY();
		double z1 = d2 + POS_MIN.getZ();
		double z2 = d2 + POS_MAX.getZ();
		if (hasColor) {
			tesselator.color(red1, green1, blue1);
			tesselator.vertex(x2, y2, z2, u1, v1);
			tesselator.color(red2, green2, blue2);
			tesselator.vertex(x2, y2, z1, u2, v2);
			tesselator.color(red3, green3, blue3);
			tesselator.vertex(x1, y2, z1, u3, v3);
			tesselator.color(red4, green4, blue4);
			tesselator.vertex(x1, y2, z2, u4, v4);
		}
		else {
			tesselator.vertex(x2, y2, z2, u1, v1);
			tesselator.vertex(x2, y2, z1, u2, v2);
			tesselator.vertex(x1, y2, z1, u3, v3);
			tesselator.vertex(x1, y2, z2, u4, v4);
		}
	}

	public void method_61(Block block, double d, double d1, double d2, int i) {
		Tessellator var9 = Tessellator.INSTANCE;
		if (field_83 >= 0) {
			i = field_83;
		}

		float var10 = uv.getStart().getX();
		float var11 = uv.getStart().getY();
		float dx = uv.getEnd().getX() - uv.getStart().getX();
		float dy = uv.getEnd().getY() - uv.getStart().getY();
		double var12 = var10 + POS_MIN.getX() * dx;
		double var14 = var10 + POS_MAX.getX() * dx;
		double var16 = (var11 + dy) - POS_MAX.getY() * dy;
		double var18 = (var11 + dy) - POS_MIN.getY() * dy;
		if (field_84) {
			double var20 = var12;
			var12 = var14;
			var14 = var20;
		}

		if (POS_MIN.getX() < 0.0D || POS_MAX.getX() > 1.0D) {
			var12 = var10;
			var14 = var10 + dx;
		}

		if (POS_MIN.getY() < 0.0D || POS_MAX.getY() > 1.0D) {
			var16 = var11;
			var18 = var11 + dy;
		}

		double var42 = var14;
		double var22 = var12;
		double var24 = var16;
		double var26 = var18;
		if (field_86 == 2) {
			var12 = var10 + POS_MIN.getY() * dx;
			var16 = (var11 + dy) - POS_MIN.getX() * dy;
			var14 = var10 + POS_MAX.getY() * dx;
			var18 = (var11 + dy) - POS_MAX.getX() * dy;
			var24 = var16;
			var26 = var18;
			var42 = var12;
			var22 = var14;
			var16 = var18;
			var18 = var16;
		}
		else if (field_86 == 1) {
			var12 = (var10 + dx) - POS_MAX.getY() * dx;
			var16 = var11 + POS_MAX.getX() * dy;
			var14 = (var10 + dx) - POS_MIN.getY() * dx;
			var18 = var11 + POS_MIN.getX() * dy;
			var42 = var14;
			var22 = var12;
			var12 = var14;
			var14 = var12;
			var24 = var18;
			var26 = var16;
		}
		else if (field_86 == 3) {
			var12 = (var10 + dx) - POS_MIN.getX() * dx;
			var14 = (var10 + dx) - POS_MAX.getX() * dx;
			var16 = var11 + POS_MAX.getY() * dy;
			var18 = var11 + POS_MIN.getY() * dy;
			var42 = var14;
			var22 = var12;
			var24 = var16;
			var26 = var18;
		}

		double var28 = d + POS_MIN.getX();
		double var30 = d + POS_MAX.getX();
		double var32 = d1 + POS_MIN.getY();
		double var34 = d1 + POS_MAX.getY();
		double var36 = d2 + POS_MIN.getZ();
		if (hasColor) {
			var9.color(red1, green1, blue1);
			var9.vertex(var28, var34, var36, var42, var24);
			var9.color(red2, green2, blue2);
			var9.vertex(var30, var34, var36, var12, var16);
			var9.color(red3, green3, blue3);
			var9.vertex(var30, var32, var36, var22, var26);
			var9.color(red4, green4, blue4);
			var9.vertex(var28, var32, var36, var14, var18);
		}
		else {
			var9.vertex(var28, var34, var36, var42, var24);
			var9.vertex(var30, var34, var36, var12, var16);
			var9.vertex(var30, var32, var36, var22, var26);
			var9.vertex(var28, var32, var36, var14, var18);
		}
	}

	public void method_65(Block block, double d, double d1, double d2, int i) {
		Tessellator var9 = Tessellator.INSTANCE;
		if (field_83 >= 0) {
			i = field_83;
		}

		float var10 = uv.getStart().getX();
		float var11 = uv.getStart().getY();
		float dx = uv.getEnd().getX() - uv.getStart().getX();
		float dy = uv.getEnd().getY() - uv.getStart().getY();
		double var12 = var10 + POS_MIN.getX() * dx;
		double var14 = var10 + POS_MAX.getX() * dx;
		double var16 = (var11 + dy) - POS_MAX.getY() * dy;
		double var18 = (var11 + dy) - POS_MIN.getY() * dy;
		if (field_84) {
			double var20 = var12;
			var12 = var14;
			var14 = var20;
		}

		if (POS_MIN.getX() < 0.0D || POS_MAX.getX() > 1.0D) {
			var12 = var10;
			var14 = var10 + dx;
		}

		if (POS_MIN.getY() < 0.0D || POS_MAX.getY() > 1.0D) {
			var16 = var11;
			var18 = var11 + dy;
		}

		double var42 = var14;
		double var22 = var12;
		double var24 = var16;
		double var26 = var18;
		if (field_87 == 1) {
			var12 = ((double) var10 + POS_MIN.getY() * 16.0D) / size;
			var18 = ((double) (var11 + 16) - POS_MIN.getX() * 16.0D) / size;
			var14 = ((double) var10 + POS_MAX.getY() * 16.0D) / size;
			var16 = ((double) (var11 + 16) - POS_MAX.getX() * 16.0D) / size;
			var24 = var16;
			var26 = var18;
			var42 = var12;
			var22 = var14;
			var16 = var18;
			var18 = var16;
		}
		else if (field_87 == 2) {
			var12 = ((double) (var10 + 16) - POS_MAX.getY() * 16.0D) / size;
			var16 = ((double) var11 + POS_MIN.getX() * 16.0D) / size;
			var14 = ((double) (var10 + 16) - POS_MIN.getY() * 16.0D) / size;
			var18 = ((double) var11 + POS_MAX.getX() * 16.0D) / size;
			var42 = var14;
			var22 = var12;
			var12 = var14;
			var14 = var12;
			var24 = var18;
			var26 = var16;
		}
		else if (field_87 == 3) {
			var12 = ((double) (var10 + 16) - POS_MIN.getX() * 16.0D) / size;
			var14 = ((double) (var10 + 16) - POS_MAX.getX() * 16.0D - 0.01D) / size;
			var16 = ((double) var11 + POS_MAX.getY() * 16.0D) / size;
			var18 = ((double) var11 + POS_MIN.getY() * 16.0D - 0.01D) / size;
			var42 = var14;
			var22 = var12;
			var24 = var16;
			var26 = var18;
		}

		double var28 = d + POS_MIN.getX();
		double var30 = d + POS_MAX.getX();
		double var32 = d1 + POS_MIN.getY();
		double var34 = d1 + POS_MAX.getY();
		double var36 = d2 + POS_MAX.getZ();
		if (hasColor) {
			var9.color(red1, green1, blue1);
			var9.vertex(var28, var34, var36, var12, var16);
			var9.color(red2, green2, blue2);
			var9.vertex(var28, var32, var36, var22, var26);
			var9.color(red3, green3, blue3);
			var9.vertex(var30, var32, var36, var14, var18);
			var9.color(red4, green4, blue4);
			var9.vertex(var30, var34, var36, var42, var24);
		}
		else {
			var9.vertex(var28, var34, var36, var12, var16);
			var9.vertex(var28, var32, var36, var22, var26);
			var9.vertex(var30, var32, var36, var14, var18);
			var9.vertex(var30, var34, var36, var42, var24);
		}
	}

	public void method_67(Block block, double d, double d1, double d2, int i) {
		Tessellator var9 = Tessellator.INSTANCE;
		if (field_83 >= 0) {
			i = field_83;
		}

		float u = uv.getStart().getX();
		float v = uv.getStart().getY();
		float dx = uv.getEnd().getX() - uv.getStart().getX();
		float dy = uv.getEnd().getY() - uv.getStart().getY();
		double u1 = u + POS_MIN.getZ() * dx;
		double u2 = u + POS_MAX.getZ() * dx;
		double v1 = (v + dy) - POS_MAX.getY() * dy;
		double v2 = (v + dy) - POS_MIN.getY() * dy;
		if (field_84) {
			double var20 = u1;
			u1 = u2;
			u2 = var20;
		}

		if (POS_MIN.getZ() < 0.0D || POS_MAX.getZ() > 1.0D) {
			u1 = u;
			u2 = u + dx;
		}

		if (POS_MIN.getY() < 0.0D || POS_MAX.getY() > 1.0D) {
			v1 = v;
			v2 = v + dy;
		}

		double u3 = u2;
		double u4 = u1;
		double v3 = v1;
		double v4 = v2;
		if (field_89 == 1) {
			u1 = u + POS_MIN.getY() * dx;
			v1 = (v + dy) - POS_MAX.getZ() * dy;
			u2 = u + POS_MAX.getY() * dx;
			v2 = (v + dy) - POS_MIN.getZ() * dy;
			v3 = v1;
			v4 = v2;
			u3 = u1;
			u4 = u2;
			v1 = v2;
			v2 = v1;
		}
		else if (field_89 == 2) {
			u1 = (u + dx) - POS_MAX.getY() * dx;
			v1 = v + POS_MIN.getZ() * dy;
			u2 = (u + dx) - POS_MIN.getY() * dx;
			v2 = v + POS_MAX.getZ() * dy;
			u3 = u2;
			u4 = u1;
			u1 = u2;
			u2 = u1;
			v3 = v2;
			v4 = v1;
		}
		else if (field_89 == 3) {
			u1 = (u + 16) - POS_MIN.getZ() * dx;
			u2 = (u + 16) - POS_MAX.getZ() * dx;
			v1 = v + POS_MAX.getY() * dy;
			v2 = v + POS_MIN.getY() * dy;
			u3 = u2;
			u4 = u1;
			v3 = v1;
			v4 = v2;
		}

		double var28 = d + POS_MIN.getX();
		double var30 = d1 + POS_MIN.getY();
		double var32 = d1 + POS_MAX.getY();
		double var34 = d2 + POS_MIN.getZ();
		double var36 = d2 + POS_MAX.getZ();
		if (hasColor) {
			var9.color(red1, green1, blue1);
			var9.vertex(var28, var32, var36, u3, v3);
			var9.color(red2, green2, blue2);
			var9.vertex(var28, var32, var34, u1, v1);
			var9.color(red3, green3, blue3);
			var9.vertex(var28, var30, var34, u4, v4);
			var9.color(red4, green4, blue4);
			var9.vertex(var28, var30, var36, u2, v2);
		}
		else {
			var9.vertex(var28, var32, var36, u3, v3);
			var9.vertex(var28, var32, var34, u1, v1);
			var9.vertex(var28, var30, var34, u4, v4);
			var9.vertex(var28, var30, var36, u2, v2);
		}
	}
}