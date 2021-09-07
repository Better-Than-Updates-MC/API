/*
 * The FML Forge Mod Loader suite.
 * Copyright (C) 2012 cpw
 *
 * This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */

package net.minecraft.src;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;

/**
 * A texture override for animations, it takes a vertical image of 
 * texture frames and constantly rotates them in the texture.
 */
@Environment(EnvType.CLIENT)
public class ModTextureAnimation extends TextureFX
{
    private final int tickRate;
    private final byte[][] images;
    private int index = 0;
    private int ticks = 0;
    
    private String targetTex = null;
    
    public ModTextureAnimation(int icon, int target, BufferedImage image, int tickCount)
    {
        this(icon, 1, target, image, tickCount);
    }

    public ModTextureAnimation(int icon, int size, int target, BufferedImage image, int tickCount)    
    {
        this(icon, size, (target == 0 ? "/terrain.png" : "/gui/items.png"), image, tickCount);
    }
    
    public ModTextureAnimation(int icon, int size, String target, BufferedImage image, int tickCount)
    {
        super(icon);
        RenderEngine re = ModLoader.getMinecraftInstance().field_6315_n;
        
        targetTex = target;
        field_1129_e = size;
        field_1128_f = re.func_1070_a(target);        

        tickRate = tickCount;
        ticks = tickCount;
        
        func_782_a(re);
        
        int sWidth  = image.getWidth();
        int sHeight = image.getHeight();
        int tWidth  = GL11.glGetTexLevelParameteri(GL_TEXTURE_2D, 0, GL_TEXTURE_WIDTH) / 16;
        int tHeight = GL11.glGetTexLevelParameteri(GL_TEXTURE_2D, 0, GL_TEXTURE_HEIGHT) / 16;
        
        int frames = (int)Math.floor((double)(sHeight / sWidth));

        if (frames < 1)
        {
            throw new IllegalArgumentException(String.format("Attempted to create a TextureAnimation with no complete frames: %dx%d", sWidth, sHeight));
        }
        else
        {
            images = new byte[frames][];

            if (sWidth != tWidth)
            {
                BufferedImage b = new BufferedImage(tWidth, tHeight * frames, 6);
                Graphics2D g = b.createGraphics();
                g.drawImage(image, 0, 0, tWidth, tHeight * frames, 0, 0, sWidth, sHeight, (ImageObserver)null);
                g.dispose();
                image = b;
            }

            for (int frame = 0; frame < frames; frame++)
            {
                int[] pixels = new int[tWidth * tHeight];
                image.getRGB(0, tHeight * frame, tWidth, tHeight, pixels, 0, tWidth);
                images[frame] = new byte[tWidth * tHeight * 4];

                for (int i = 0; i < pixels.length; i++)
                {
                    int i4 = i * 4;
                    images[frame][i4 + 0] = (byte)(pixels[i] >> 16 & 255);
                    images[frame][i4 + 1] = (byte)(pixels[i] >> 8  & 255);
                    images[frame][i4 + 2] = (byte)(pixels[i] >> 0  & 255);
                    images[frame][i4 + 3] = (byte)(pixels[i] >> 24 & 255);
                }
            }
        }
    }
    
    public void func_783_a()
    {
        if (++ticks >= tickRate)
        {
            if (++index >= images.length)
            {
                index = 0;
            }

            field_1127_a = images[index];
            ticks = 0;
        }
    }
}
