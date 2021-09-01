import net.minecraft.client.Minecraft;

// 
// Decompiled by Procyon v0.5.36
// 

public class EntityRendererProxy extends px
{
    private Minecraft game;
    
    public EntityRendererProxy(final Minecraft minecraft) {
        super(minecraft);
        this.game = minecraft;
    }
    
    public void b(final float f1) {
        super.b(f1);
        ModLoader.OnTick(this.game);
    }
}
