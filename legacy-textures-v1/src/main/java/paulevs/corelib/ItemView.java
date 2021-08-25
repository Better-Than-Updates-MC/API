package paulevs.corelib;

import net.minecraft.block.material.Material;
import net.minecraft.entity.BlockEntity;
import net.minecraft.world.BlockView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.BiomeSource;
import net.minecraft.world.gen.FixedBiomeSource;

public class ItemView implements BlockView {
	private static final FixedBiomeSource BIOME_SRC = new FixedBiomeSource(Biome.PLAINS, 0.5D, 0.0D);
	private int blockId;
	private int meta;

	public void setBlockMeta(int meta) {
		this.meta = meta;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}

	public int getBlockId(int x, int y, int z) {
		return x == 0 && y == 0 && z == 0 ? blockId : 0;
	}

	public BlockEntity getBlockEntity(int i, int j, int k) {
		return null;
	}

	@Override
	public float getNaturalBrightness(int i, int j, int k, int i1) {
		return 15;
	}

	@Override
	public float getSyntheticBrightness(int i, int j, int k) {
		return 1;
	}

	@Override
	public int getBlockMeta(int x, int y, int z) {
		return meta;
	}

	@Override
	public Material getMaterial(int x, int y, int z) {
		return Material.AIR;
	}

	@Override
	public boolean isFullOpaque(int i, int j, int k) {
		return false;
	}

	@Override
	public boolean canSuffocate(int i, int j, int k) {
		return false;
	}

	@Override
	public BiomeSource getBiomeSource() {
		return BIOME_SRC;
	}
}