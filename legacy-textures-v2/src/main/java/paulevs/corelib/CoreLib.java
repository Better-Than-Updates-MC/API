package paulevs.corelib;

import net.fabricmc.api.ModInitializer;
import paulevs.corelib.texture.TextureAtlas;

public class CoreLib implements ModInitializer {
	public static final ItemView ITEM_VIEW = new ItemView();

	public static TextureAtlas blocksAtlas;
	public static TextureAtlas itemsAtlas;
	public static TextureAtlas selectedAtlas;

	@Override
	public void onInitialize() {
		System.out.println("CoreLib Initialized");
	}

	public static float getAtlasSize() {
		return selectedAtlas == null ? 256 : selectedAtlas.getSize();
	}
}
