package paulevs.corelib.model.prefab;

import paulevs.corelib.model.Model;
import paulevs.corelib.model.shape.Shape;
import paulevs.corelib.texture.UVPair;

public class FullCubeModel extends Model {
	public FullCubeModel(String texture) {
		this.addTexture("texture", texture);
	}

	@Override
	public void renderBlock() {
		render();
	}

	@Override
	public void renderItem() {
		render();
	}
	
	private void render() {
		Shape.setUV(getBlockUV());
		FULL_CUBE.render();
	}
	
	@Override
	public boolean hasItem() {
		return true;
	}

	@Override
	public UVPair particleUV() {
		return getBlockUV();
	}

	private UVPair getBlockUV() {
		return this.getUV("texture");
	}
}
