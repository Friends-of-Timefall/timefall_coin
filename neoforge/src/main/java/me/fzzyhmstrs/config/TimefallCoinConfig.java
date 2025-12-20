package me.fzzyhmstrs.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.neoforged.fml.loading.FMLPaths;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TimefallCoinConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static Float CHEST_CHANCE = null;


    @SuppressWarnings("SameParameterValue")
    private static <T> T readOrCreate(ConfigSupplier<T> configSupplier, Class<T> classType) {
        File dir = FMLPaths.CONFIGDIR.get().toFile();
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

    @SuppressWarnings("ConstantValue")
    public static float getChestChance() {
        if (CHEST_CHANCE == null) {
            CHEST_CHANCE = readOrCreate(TimefallCoinConfig.Config::new, TimefallCoinConfig.Config.class).chestLootChance;
        }
        return CHEST_CHANCE != null ? CHEST_CHANCE : 0.02f;
    }
}
