import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.awt.Image;
import org.lwjgl.opengl.GL11;
import java.awt.image.BufferedImage;

// 
// Decompiled by Procyon v0.5.36
// 

public class ModTextureAnimation extends aw
{
    private final int tickRate;
    private final byte[][] images;
    private int index;
    private int ticks;
    
    public ModTextureAnimation(final int slot, final int dst, final BufferedImage source, final int rate) {
        this(slot, 1, dst, source, rate);
    }
    
    public ModTextureAnimation(final int slot, final int size, final int dst, BufferedImage source, final int rate) {
        super(slot);
        this.index = 0;
        this.ticks = 0;
        this.e = size;
        this.f = dst;
        this.tickRate = rate;
        this.ticks = rate;
        this.a(ModLoader.getMinecraftInstance().p);
        final int targetWidth = GL11.glGetTexLevelParameteri(3553, 0, 4096) / 16;
        final int targetHeight = GL11.glGetTexLevelParameteri(3553, 0, 4097) / 16;
        final int width = source.getWidth();
        final int height = source.getHeight();
        final int images = (int)Math.floor(height / width);
        if (images <= 0) {
            throw new IllegalArgumentException("source has no complete images");
        }
        this.images = new byte[images][];
        if (width != targetWidth) {
            final BufferedImage img = new BufferedImage(targetWidth, targetHeight * images, 6);
            final Graphics2D gfx = img.createGraphics();
            gfx.drawImage(source, 0, 0, targetWidth, targetHeight * images, 0, 0, width, height, null);
            gfx.dispose();
            source = img;
        }
        for (int i = 0; i < images; ++i) {
            final int[] temp = new int[targetWidth * targetHeight];
            source.getRGB(0, targetHeight * i, targetWidth, targetHeight, temp, 0, targetWidth);
            this.images[i] = new byte[targetWidth * targetHeight * 4];
            for (int j = 0; j < temp.length; ++j) {
                final int a = temp[j] >> 24 & 0xFF;
                final int r = temp[j] >> 16 & 0xFF;
                final int g = temp[j] >> 8 & 0xFF;
                final int b = temp[j] >> 0 & 0xFF;
                this.images[i][j * 4 + 0] = (byte)r;
                this.images[i][j * 4 + 1] = (byte)g;
                this.images[i][j * 4 + 2] = (byte)b;
                this.images[i][j * 4 + 3] = (byte)a;
            }
        }
    }
    
    public void a() {
        if (this.ticks >= this.tickRate) {
            ++this.index;
            if (this.index >= this.images.length) {
                this.index = 0;
            }
            this.a = this.images[this.index];
            this.ticks = 0;
        }
        ++this.ticks;
    }
}
