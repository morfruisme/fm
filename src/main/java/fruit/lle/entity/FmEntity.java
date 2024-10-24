package fruit.lle.entity;

import fruit.lle.Fm;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class FmEntity {
    public static final EntityType<MudballEntity> MUDBALL = register(
        EntityType.Builder.<MudballEntity>create(MudballEntity::new, SpawnGroup.MISC)
            .dimensions(0.25F, 0.25F)
            .maxTrackingRange(4)
            .trackingTickInterval(10)
            .build(), 
        "mudball");

    private static <E extends Entity> EntityType<E> register(EntityType<E> type, String str_id) {
        Identifier id = Identifier.of(Fm.MOD_ID, str_id);
        return Registry.register(Registries.ENTITY_TYPE, id, type);
    }

    public static void init() {} 
}