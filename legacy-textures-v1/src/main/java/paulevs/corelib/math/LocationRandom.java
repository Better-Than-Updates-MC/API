package paulevs.corelib.math;

import java.util.Random;

public class LocationRandom extends Random {
	public void setSeed(int x, int y, int z, int offset) {
		int h = offset * 271 + x * 374761393 + y * 668265263 + z;
		h = (h ^ (h >> 13)) * 1274126177;
		setSeed(h ^ (h >> 16));
	}

	public void setSeed(int x, int y, int z) {
		int h = x * 374761393 + y * 668265263 + z;
		h = (h ^ (h >> 13)) * 1274126177;
		setSeed(h ^ (h >> 16));
	}
}