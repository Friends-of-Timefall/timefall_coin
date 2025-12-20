package me.fzzyhmstrs.timefall_coin.item;

import me.fzzyhmstrs.timefall_coin.TimefallCoinNeoForge;
import net.minecraft.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TimefallCoinNeoForge.MOD_ID);

    public static final DeferredItem<Item> TIMEFALL_COIN = ITEMS.register("coin", () -> new Item(new Item.Settings()));
    @SuppressWarnings("unused")
    public static final DeferredItem<Item> TIMEFALL_COIN_PILE = ITEMS.register("coin_pile", () -> new Item(new Item.Settings()));

    public static void register (IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
