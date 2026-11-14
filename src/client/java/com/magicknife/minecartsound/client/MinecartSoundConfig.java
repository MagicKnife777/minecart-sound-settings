package com.magicknife.minecartsound.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MinecartSoundConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir().resolve("minecart-sound-settings.json");

    public static float volume = 1.0f;

    public static void load() {
        if (!Files.exists(CONFIG_PATH)) return;
        try {
            String json = Files.readString(CONFIG_PATH);
            MinecartSoundConfig data = GSON.fromJson(json, MinecartSoundConfig.class);
            if (data != null) {
                volume = Math.clamp(data.volume, 0.0f, 1.0f);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            Files.writeString(CONFIG_PATH, GSON.toJson(new MinecartSoundConfig()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}