package fruit.lle;

import fruit.lle.entity.FmEntity;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class FmRenderer {
    static void init() {
        EntityRendererRegistry.register(FmEntity.MUDBALL, FlyingItemEntityRenderer::new);

        HudRenderCallback.EVENT.register((context, delta) -> {
			MudEffect.render(context, delta);
		});
    }
}
