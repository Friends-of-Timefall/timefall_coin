package me.fzzyhmstrs.timefall_coin;

import me.fzzyhmstrs.timefall_coin.item.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(TimefallCoinNeoForge.MOD_ID)
public class TimefallCoinNeoForge {
    public static final String MOD_ID = "timefall_coin";

    public TimefallCoinNeoForge(IEventBus modEventBus) {
        ModItems.register(modEventBus);
    }
}
