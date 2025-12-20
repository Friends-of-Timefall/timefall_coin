package me.fzzyhmstrs.timefall_coin.event;

import me.fzzyhmstrs.timefall_coin.TimefallCoinNeoForge;
import me.fzzyhmstrs.timefall_coin.item.ModItems;
import net.minecraft.item.ItemGroups;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@EventBusSubscriber(modid = TimefallCoinNeoForge.MOD_ID)
public class ModCreativeModTableContentsEvent {
    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == ItemGroups.FUNCTIONAL) {
            event.add(ModItems.TIMEFALL_COIN.get());
            event.add(ModItems.TIMEFALL_COIN_PILE.get());
        }
    }
}
