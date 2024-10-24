package fruit.lle;

import fruit.lle.networking.MudballPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class FmClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		FmRenderer.init();

		ClientPlayNetworking.registerGlobalReceiver(MudballPayload.ID, (payload, context) -> {
            context.client().execute(() -> {
                MudEffect.add();
            });
        });
	}
}