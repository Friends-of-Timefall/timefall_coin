package me.fzzyhmstrs.timefall_coin.event;

import me.fzzyhmstrs.config.TimefallCoinConfig;
import me.fzzyhmstrs.timefall_coin.TimefallCoinNeoForge;
import me.fzzyhmstrs.timefall_coin.item.ModItems;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.LootTableLoadEvent;

@EventBusSubscriber(modid = TimefallCoinNeoForge.MOD_ID)
public class ModLootTableEvents {
    @SubscribeEvent
    public static void addCoinsToChestsLootTableLoadEvent(LootTableLoadEvent event) {
        if(event.getName().getPath().contains("chests")){
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1.0F))
                    .conditionally(RandomChanceLootCondition.builder(TimefallCoinConfig.getChestChance()))
                    .with(ItemEntry.builder(ModItems.TIMEFALL_COIN.get()));
            event.getTable().addPool(poolBuilder.build());

        }
    }
}
