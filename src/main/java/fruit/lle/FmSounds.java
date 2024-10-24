package fruit.lle;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class FmSounds {
    public static final SoundEvent MUDBALL_SPLASH = register("mudball_splash");

    private static SoundEvent register(String str_id) {
        Identifier id = Identifier.of(Fm.MOD_ID, str_id);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void init() {}
}
