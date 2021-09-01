import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.render.block.BlockRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import java.util.Random;
import java.util.Map;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class BaseMod
{
    public int AddFuel(final int id) {
        return 0;
    }
    
    public void AddRenderer(final Map renderers) {
    }
    
    public boolean DispenseEntity(final World world, final double x, final double y, final double z, final int xVel, final int zVel, final Item item) {
        return false;
    }
    
    public void GenerateNether(final World world, final Random random, final int chunkX, final int chunkZ) {
    }
    
    public void GenerateSurface(final World world, final Random random, final int chunkX, final int chunkZ) {
    }
    
    public void KeyboardEvent(final KeyBinding keyBinding) {
    }
    
    public void ModsLoaded() {
    }
    
    public boolean OnTickInGame(final Minecraft game) {
        return false;
    }
    
    public boolean OnTickInGUI(final Minecraft game, final Screen gui) {
        return false;
    }
    
    public void RegisterAnimation(final Minecraft game) {
    }
    
    public void RenderInvBlock(final BlockRenderer renderer, final Block block, final int metadata, final int modelID) {
    }
    
    public boolean RenderWorldBlock(final BlockRenderer renderer, final World world, final int x, final int y, final int z, final Block block, final int modelID) {
        return false;
    }
    
    public void TakenFromCrafting(final PlayerEntity player, final Item item) {
    }
    
    public void TakenFromFurnace(final PlayerEntity player, final Item item) {
    }
    
    public void OnItemPickup(final PlayerEntity player, final Item item) {
    }
    
    @Override
    public String toString() {
        return this.getClass().getName() + " " + this.Version();
    }
    
    public abstract String Version();
}
