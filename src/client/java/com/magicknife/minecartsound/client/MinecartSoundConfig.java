package com.magicknife.minecartsound.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class MinecartSoundConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir().resolve("minecart-sound-settings.json");

    public static float volume = 1.0f;

    public static void load() {
        if (!Files.exists(CONFIG_PATH)) return;
        try {
            String json = Files.readString(CONFIG_PATH);
            // 使用 Map 来接收反序列化的结果
            Map<?, ?> data = GSON.fromJson(json, Map.class);
            if (data != null && data.containsKey("volume")) {
                Number vol = (Number) data.get("volume");
                volume = Math.clamp(vol.floatValue(), 0.0f, 1.0f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            // 使用 Map 把当前的静态变量值存起来
            Map<String, Float> data = new HashMap<>();
            data.put("volume", volume);
            Files.writeString(CONFIG_PATH, GSON.toJson(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}