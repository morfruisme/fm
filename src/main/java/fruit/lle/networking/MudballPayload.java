package fruit.lle.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record MudballPayload() implements CustomPayload {
    public static final CustomPayload.Id<MudballPayload> ID = new CustomPayload.Id<MudballPayload>(FmNetworking.MUDBALL_PACKET);
    //public static final PacketCodec<RegistryByteBuf, MudballPayload> CODEC = PacketCodec.tuple(PacketCodecs.BOOL, MudballPayload::isRed, MudballPayload::new);
    public static final PacketCodec<RegistryByteBuf, MudballPayload> CODEC = FmNetworking.emptyPacket(MudballPayload::new);

    @Override
    public Id<MudballPayload> getId() {
        return ID;
    }
}
