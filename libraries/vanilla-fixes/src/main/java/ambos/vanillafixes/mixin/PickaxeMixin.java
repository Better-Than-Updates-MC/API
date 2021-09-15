package ambos.vanillafixes.mixin;

import net.minecraft.block.Block;
import net.minecraft.item.PickaxeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PickaxeItem.class)
final class PickaxeMixin {
    @Shadow
    private static Block[] effectiveBlocks = new Block[]{
            Block.BRICKS, Block.DOUBLE_STONE_SLAB, Block.STONE_SLAB,
            Block.STONE, Block.SANDSTONE, Block.MOSSY_COBBLESTONE, Block.IRON_ORE, Block.IRON_BLOCK,
            Block.COAL_ORE, Block.GOLD_BLOCK, Block.GOLD_ORE, Block.DIAMOND_ORE, Block.DIAMOND_BLOCK,
            Block.ICE, Block.NETHERRACK, Block.LAPIS_LAZULI_ORE, Block.LAPIS_LAZULI_BLOCK,
            Block.REDSTONE_ORE, Block.REDSTONE_ORE_LIT, Block.COBBLESTONE_STAIRS, Block.IRON_DOOR,
            Block.COBBLESTONE, Block.FURNACE, Block.FURNACE_LIT, Block.DISPENSER,
            Block.STONE_PRESSURE_PLATE, Block.RAIL, Block.DETECTOR_RAIL, Block.GOLDEN_RAIL
    };
}
