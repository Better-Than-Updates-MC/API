package ambos.vanillafixes.mixin;

import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AxeItem.class)
final class HatchetMixin {
    @Shadow
    private static Block[] effectiveBlocks = new Block[]{
            Block.WOOD, Block.BOOKSHELF, Block.LOG, Block.CHEST, Block.WORKBENCH,
            Block.WOOD_DOOR, Block.WOOD_STAIRS, Block.WOODEN_PRESSURE_PLATE, Block.FENCE,
            Block.TRAPDOOR, Block.JUKEBOX, Block.PUMPKIN, Block.JACK_O_LANTERN, Block.STANDING_SIGN,
            Block.WALL_SIGN, Block.NOTEBLOCK, Block.LADDER
    };
}
