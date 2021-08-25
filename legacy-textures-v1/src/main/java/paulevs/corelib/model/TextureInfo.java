package paulevs.corelib.model;

import paulevs.corelib.texture.TextureAtlas;
import paulevs.corelib.texture.UVPair;

public class TextureInfo {
	public final String name;
	private UVPair uv;

	public TextureInfo(String name) {
		this.name = name;
	}

	public void init(TextureAtlas atlas) {
		uv = atlas.getUV(name);
	}

	public UVPair getUV() {
		return uv;
	}

	public String getName() {
		return name;
	}
}
