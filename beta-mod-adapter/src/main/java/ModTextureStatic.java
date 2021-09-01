import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.awt.Image;
import org.lwjgl.opengl.GL11;
import java.awt.image.BufferedImage;

// 
// Decompiled by Procyon v0.5.36
// 

public class ModTextureStatic extends aw
{
    private boolean oldanaglyph;
    private int[] pixels;
    
    public ModTextureStatic(final int slot, final int dst, final BufferedImage source) {
        this(slot, 1, dst, source);
    }
    
    public ModTextureStatic(final int slot, final int size, final int dst, final BufferedImage source) {
        super(slot);
        this.pixels = null;
        this.e = size;
        this.f = dst;
        this.a(ModLoader.getMinecraftInstance().p);
        final int targetWidth = GL11.glGetTexLevelParameteri(3553, 0, 4096) / 16;
        final int targetHeight = GL11.glGetTexLevelParameteri(3553, 0, 4097) / 16;
        final int width = source.getWidth();
        final int height = source.getHeight();
        this.pixels = new int[targetWidth * targetHeight];
        this.a = new byte[targetWidth * targetHeight * 4];
        if (width != height || width != targetWidth) {
            final BufferedImage img = new BufferedImage(targetWidth, targetHeight, 6);
            final Graphics2D gfx = img.createGraphics();
            gfx.drawImage(source, 0, 0, targetWidth, targetHeight, 0, 0, width, height, null);
            img.getRGB(0, 0, targetWidth, targetHeight, this.pixels, 0, targetWidth);
            gfx.dispose();
        }
        else {
            source.getRGB(0, 0, width, height, this.pixels, 0, width);
        }
        this.update();
    }
    
    public void update() {
        for (int i = 0; i < this.pixels.length; ++i) {
            final int a = this.pixels[i] >> 24 & 0xFF;
            int r = this.pixels[i] >> 16 & 0xFF;
            int g = this.pixels[i] >> 8 & 0xFF;
            int b = this.pixels[i] >> 0 & 0xFF;
            if (this.c) {
                final int grey = (r + g + b) / 3;
                g = (r = (b = grey));
            }
            this.a[i * 4 + 0] = (byte)r;
            this.a[i * 4 + 1] = (byte)g;
            this.a[i * 4 + 2] = (byte)b;
            this.a[i * 4 + 3] = (byte)a;
        }
        this.oldanaglyph = this.c;
    }
    
    public void a() {
        if (this.oldanaglyph != this.c) {
            this.update();
        }
    }
    
    public static BufferedImage scale2x(final BufferedImage in) {
        final int width = in.getWidth();
        final int height = in.getHeight();
        final BufferedImage out = new BufferedImage(width * 2, height * 2, 2);
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                final int E = in.getRGB(x, y);
                int B;
                if (y == 0) {
                    B = E;
                }
                else {
                    B = in.getRGB(x, y - 1);
                }
                int D;
                if (x == 0) {
                    D = E;
                }
                else {
                    D = in.getRGB(x - 1, y);
                }
                int F;
                if (x >= width - 1) {
                    F = E;
                }
                else {
                    F = in.getRGB(x + 1, y);
                }
                int H;
                if (y >= height - 1) {
                    H = E;
                }
                else {
                    H = in.getRGB(x, y + 1);
                }
                int E2;
                int E3;
                int E4;
                int E5;
                if (B != H && D != F) {
                    E2 = ((D == B) ? D : E);
                    E3 = ((B == F) ? F : E);
                    E4 = ((D == H) ? D : E);
                    E5 = ((H == F) ? F : E);
                }
                else {
                    E2 = E;
                    E3 = E;
                    E4 = E;
                    E5 = E;
                }
                out.setRGB(x * 2, y * 2, E2);
                out.setRGB(x * 2 + 1, y * 2, E3);
                out.setRGB(x * 2, y * 2 + 1, E4);
                out.setRGB(x * 2 + 1, y * 2 + 1, E5);
            }
        }
        return out;
    }
}
