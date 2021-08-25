package paulevs.corelib.texture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.imageio.ImageIO;

import paulevs.corelib.math.Vec2F;

public class TextureAtlas {
	private static final BufferedImage EMPTY = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);

	static {
		int magenta = (255 << 24) | (255 << 16) | 255;
		int black = (255 << 24);

		EMPTY.setRGB(0, 0, magenta);
		EMPTY.setRGB(1, 1, magenta);
		EMPTY.setRGB(0, 1, black);
		EMPTY.setRGB(1, 0, black);
	}

	private final HashMap<String, UVPair> uvs = new HashMap<>();
	private final Texture2D atlas;
	private final float size;

	public TextureAtlas(String primal, HashSet<String> textures) {
		List<ImageInfo> blocks = loadBlocks(textures);
		blocks.add(new ImageInfo(EMPTY, "noblock"));

		blocks.sort((img1, img2) -> {
			int a = Math.max(img1.img.getWidth(), img1.img.getHeight());
			int b = Math.max(img2.img.getWidth(), img2.img.getHeight());
			return b - a;
		});
		if (primal != null) blocks.add(0, new ImageInfo(loadImage(primal), primal));
		
		Vec2F[] poses = new Vec2F[blocks.size()];
		int side = 512;
		while (!buildAtlas(side, poses, blocks)) {
			side <<= 1;
			if (side > 16384) {
				throw new RuntimeException("Texture atlas " + primal + " reached maximum possible size!");
			}
		}
		size = side;

		BufferedImage atlasIMG = new BufferedImage((int) size, (int) size, BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < blocks.size(); i++) {
			ImageInfo info = blocks.get(i);
			drawImage(info.img, atlasIMG, (int) poses[i].getX(), (int) poses[i].getY());
			Vec2F start = new Vec2F(poses[i].getX() / size, poses[i].getY() / size);
			Vec2F end = new Vec2F((poses[i].getX() + info.img.getWidth()) / size, (poses[i].getY() + info.img.getHeight()) / size);
			uvs.put(info.name, new UVPair(start, end));
		}

		atlas = new Texture2D(atlasIMG);
	}
	
	private boolean buildAtlas(int side, Vec2F[] poses, List<ImageInfo> blocks) {
		List<Rect> rects = new ArrayList<>();
		rects.add(new Rect(0, 0, side, side));
		for (int i = 0; i < blocks.size(); i++) {
			Rect possible = null;
			ImageInfo info = blocks.get(i);
			for (Rect r : rects) {
				if (r.canFit(info.img.getWidth(), info.img.getHeight())) {
					possible = r;
					break;
				}
			}
			if (possible == null) {
				return false;
			}
			poses[i] = new Vec2F(possible.x, possible.y);
			possible.split(info.img.getWidth(), info.img.getHeight(), rects);
			Collections.sort(rects);
		}
		return true;
	}

	private void drawImage(BufferedImage img, BufferedImage buffer, int x, int y) {
		for (int px = 0; px < img.getWidth(); px++) {
			int bx = x + px;
			for (int py = 0; py < img.getHeight(); py++) {
				int by = y + py;
				buffer.setRGB(bx, by, img.getRGB(px, py));
			}
		}
	}

	public void bind() {
		atlas.bind();
	}

	public UVPair getUV(String texture) {
		UVPair uv = uvs.get(texture);
		return uv != null ? uv : uvs.get("noblock");
	}

	private List<ImageInfo> loadBlocks(HashSet<String> textures) {
		List<ImageInfo> result = new ArrayList<ImageInfo>();
		for (String name : textures) {
			BufferedImage img = loadImage(name);
			if (img != EMPTY) result.add(new ImageInfo(img, name));
		}
		return result;
	}

	private BufferedImage loadImage(String filename) {
		BufferedImage image = EMPTY;
		if (filename != null && !filename.isEmpty()) {
			try {
				InputStream in = getClass().getResourceAsStream(filename);
				if (in != null) {
					image = ImageIO.read(in);
					in.close();
				}
			}
			catch (IOException ignored) {
			}
		}
		if (image == EMPTY) {
			try {
				image = ImageIO.read(new File(filename));
			}
			catch (IOException ignored) {}
		}
		return image;
	}

	private static class Rect implements Comparable<Rect> {
		int x;
		int y;
		int w;
		int h;

		Rect(int x, int y, int w, int h) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
		}

		public boolean isZero() {
			return w == 0 || h == 0;
		}

		public boolean canFit(int width, int height) {
			return width <= w && height <= h;
		}

		private List<Rect> splitX(int dx, int dy) {
			List<Rect> res = new ArrayList<Rect>();
			Rect a = new Rect(x, y + dy, dx, h - dy);
			Rect b = new Rect(x + dx, y, w - dx, h);
			if (!a.isZero()) res.add(a);
			if (!b.isZero()) res.add(b);
			return res;
		}

		private List<Rect> splitY(int dx, int dy) {
			List<Rect> res = new ArrayList<Rect>();
			Rect a = new Rect(x + dx, y, w - dx, dy);
			Rect b = new Rect(x, y + dy, w, h - dy);
			if (!a.isZero()) res.add(a);
			if (!b.isZero()) res.add(b);
			return res;
		}

		public void split(int width, int height, List<Rect> list) {
			list.remove(this);
			if (w - width > h - height) {
				List<Rect> rects = splitX(width, height);
				list.addAll(rects);
			}
			else {
				List<Rect> rects = splitY(width, height);
				list.addAll(rects);
			}
		}

		@Override
		public int compareTo(Rect r) {
			int a = Math.max(w, h);
			int b = Math.max(r.w, r.h);
			return a - b;
		}
	}

	private class ImageInfo {
		public BufferedImage img;
		public String name;

		public ImageInfo(BufferedImage img, String name) {
			this.img = img;
			this.name = name;
		}
	}

	public Texture2D getTexture() {
		return atlas;
	}

	public float getSize() {
		return size;
	}
}
