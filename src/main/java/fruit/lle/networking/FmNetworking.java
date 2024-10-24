package fruit.lle.networking;

import fruit.lle.Fm;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.Identifier;

public class FmNetworking {
    public static final Identifier MUDBALL_PACKET = Identifier.of(Fm.MOD_ID, "mudball_packet");

    public static void init() {
        PayloadTypeRegistry.playS2C().register(MudballPayload.ID, MudballPayload.CODEC);
    } 

    // suspicious empty packet, seems to work
    public static <B, V extends Object> PacketCodec<B, V> emptyPacket(Callable<V> to) {
        return new PacketCodec<B, V>() {
            @Override
            public V decode(B buf) {
                return to.call();
            }

            @Override
            public void encode(B buf, V value) {}
        };
    }

    public interface Callable<T> {
        T call();
    }
}
