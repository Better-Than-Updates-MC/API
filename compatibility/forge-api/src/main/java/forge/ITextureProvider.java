/*
 * This software is provided under the terms of the Minecraft Forge Public License v1.0.
 */

package forge;

/**
 * This interface has to be implemented either by an instance of Block or Item.
 * It allows using texture files different from terrain.png or items.png.
 */
@Deprecated
public interface ITextureProvider {

    /**
     * This interface has to return the path to a file that is the same size as
     * terrain.png, but not named terrain.png. It will be used instead of the
     * regular terrain file to render blocks and items.
     */
    String getTextureFile();

}
