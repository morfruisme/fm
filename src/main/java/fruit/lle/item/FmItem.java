package fruit.lle.item;

import fruit.lle.Fm;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class FmItem {
    public static final Item MUDBALL = register(new MudballItem(new Item.Settings()), "mudball");

    private static Item register(Item item, String str_id) {
        Identifier id = Identifier.of(Fm.MOD_ID, str_id);
        return Registry.register(Registries.ITEM, id, item);
    }

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(group -> group.add(MUDBALL));
    }
}