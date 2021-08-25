package paulevs.corelib.texture;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class Texture2D {
	private final int texture;

	public Texture2D(int id) {
		texture = id;
	}

	public Texture2D(BufferedImage image) {
		texture = GL11.glGenTextures();

		int[] data = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), data, 0, image.getWidth());

		ByteBuffer buffer = BufferUtils.createByteBuffer(data.length * 4);
		buffer.order(ByteOrder.BIG_ENDIAN);

		if (buffer == null || buffer.capacity() != data.length * 4) buffer = ByteBuffer.allocateDirect(data.length * 4);

		for (int i = 0; i < data.length; i++) {
			int rgba = Integer.rotateLeft(data[i], 8);
			buffer.putInt(rgba);
		}
		buffer.flip();

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA,
				GL11.GL_UNSIGNED_BYTE, buffer);
	}

	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
	}

	public Integer getID() {
		return texture;
	}
}
