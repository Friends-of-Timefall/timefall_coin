package me.fzzyhmstrs.timefall_coin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TimefallCoin implements ModInitializer {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final String MOD_ID = "timefall_coin";
    private static Float CHEST_CHANCE = null;

    public static final Item TIMEFALL_COIN = Registry.register(Registries.ITEM,
            Identifier.of(MOD_ID, "coin"), new Item(new Item.Settings()));

    public static final Item TIMEFALL_COIN_PILE = Registry.register(Registries.ITEM,
            Identifier.of(MOD_ID, "coin_pile"), new Item(new Item.Settings()));

    @SuppressWarnings("ConstantValue")
    public static float getChestChance() {
        if (CHEST_CHANCE == null) {
            CHEST_CHANCE = readOrCreate(Config::new, Config.class).chestLootChance;
        }
        return CHEST_CHANCE != null ? CHEST_CHANCE : 0.02f;
    }

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.START_DATA_PACK_RELOAD.register((server, serverResourceManager) ->
                CHEST_CHANCE = null
        );

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (key.getValue().getPath().startsWith("chests")) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0F))
                        .conditionally(RandomChanceLootCondition.builder(getChestChance()).build())
                        .with(ItemEntry.builder(TIMEFALL_COIN));
                tableBuilder.pool(poolBuilder);
            }
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
            entries.add(new ItemStack(TIMEFALL_COIN));
            entries.add(new ItemStack(TIMEFALL_COIN_PILE));
        });
    }

    @SuppressWarnings("SameParameterValue")
    private static <T> T readOrCreate(ConfigSupplier<T> configSupplier, Class<T> classType) {
        File dir = FabricLoader.getInstance().getConfigDir().toFile();
        if (!dir.exists() && !dir.mkdirs()) {
            System.out.println("Could not create directory, using default configs.");
            return configSupplier.get();
        }
        File f = new File(dir, "timefall_coin_config.json");
        try {
            if (f.exists()) {
                try (Stream<String> lines = Files.lines(f.toPath())) {
                    String content = lines.collect(Collectors.joining(""));
                    return GSON.fromJson(content, classType);
                }
            } else if (!f.createNewFile()) {
                System.out.println("Failed to create default config file (timefall_coin_config.json), using default config.");
            } else {
                Files.writeString(f.toPath(), GSON.toJson(configSupplier.get()));
            }
            return configSupplier.get();
        } catch (IOException e) {
            System.out.println("Failed to read config file! Using default values: " + e.getMessage());
            return configSupplier.get();
        }
    }

    @FunctionalInterface
    private interface ConfigSupplier<T> {
        T get();
    }

    public static class Config {
        public float chestLootChance = 0.02f;
    }
}