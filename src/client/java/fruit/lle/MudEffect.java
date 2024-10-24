package fruit.lle;

import java.util.ArrayList;
import java.util.Random;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class MudEffect {
	private static final Identifier TEXTURE = Identifier.of(Fm.MOD_ID, "textures/hud/mud_effect.png");
    private static final Identifier TEXTURE_SINGLE = Identifier.of(Fm.MOD_ID, "textures/hud/mud_effect_single.png");
    private static final float FRAME_DURATION = 800; // ms
    private static final int FRAME_COUNT = 9;
    private static final int SCALE = 6;
    private static final int SIZE = 32;

    // list of instances affecting the client
    private static ArrayList<MudEffect> effects = new ArrayList<MudEffect>();
    private static long last;

    public static void add() {
        if (effects.isEmpty())
            last = System.currentTimeMillis();
        
        effects.add(new MudEffect());
    }

    public static void render(DrawContext context, RenderTickCounter delta) {
        effects.forEach(effect -> effect.render(context));

        if (!effects.isEmpty()) {
            long now = System.currentTimeMillis();
            long elapsed = now - last;

            if (elapsed >= FRAME_DURATION) {
                last = now;

                // update
                effects.removeIf(effect -> {
                    effect.frame++;
                    return effect.frame == FRAME_COUNT;
                });
            }
        }
    }

    private int x;
    private int y;
    private int angle;
    private int frame;
    
    public MudEffect() {
        int w = MinecraftClient.getInstance().getWindow().getWidth();
        int h = MinecraftClient.getInstance().getWindow().getHeight();
        int dw = 0;
        int dh = 0;

        Random random = new Random();
        this.x = random.nextInt(w/2 + 2*dw) - dw;
        this.y = random.nextInt(h/2 + 2*dh) - dh;
        this.angle = random.nextInt(4)*90 + random.nextInt(10) - 5; // bof
        this.frame = 0;
    }

    private void render(DrawContext context) {
        boolean DEMO = false;

        
        if (!DEMO) {
            // select sprite
            int u = (this.frame%3)*SIZE;
            int v = (this.frame/3)*SIZE;
            
            // fade
            float t = 1 - (float)(this.frame%(FRAME_COUNT+1)) / (float)FRAME_COUNT;
            
            RenderSystem.enableBlend();
            RenderSystem.setShaderColor(1, 1, 1, t);
            context.drawTexture(TEXTURE, this.x, this.y, SCALE*SIZE, SCALE*SIZE, u, v, SIZE, SIZE, 3*SIZE, 3*SIZE);
            RenderSystem.disableBlend();
        }
        else {
            MatrixStack m = context.getMatrices();
            m.push();
            m.translate(this.x, this.y, 0);
            m.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(this.angle));

            // TODO: custom shader to blur/deform
            RenderSystem.enableBlend();
            context.drawTexture(TEXTURE_SINGLE, -SCALE*SIZE/2, -SCALE*SIZE/2, SCALE*SIZE, SCALE*SIZE, 0, 0, SIZE, SIZE, SIZE, SIZE);
            RenderSystem.disableBlend();

            m.pop();
        }
    }
}
