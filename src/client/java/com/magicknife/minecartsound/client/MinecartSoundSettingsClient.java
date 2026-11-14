package com.magicknife.minecartsound.client;

import com.mojang.serialization.Codec;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.screens.options.SoundOptionsScreen;
import net.minecraft.network.chat.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MinecartSoundSettingsClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		MinecartSoundConfig.load();

		ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
			if (!(screen instanceof SoundOptionsScreen soundScreen)) return;

			OptionInstance<Float> minecartOption = new OptionInstance<>(
					"options.minecartSound",
					OptionInstance.noTooltip(),
					(component, value) -> Component.translatable("options.minecartSound")
							.append(": ").append(String.format("%.0f%%", value * 100)),
					new OptionInstance.IntRange(0, 100).xmap(
							i -> i / 100.0f,
							f -> (int) (f * 100),
							false
					),
					Codec.floatRange(0.0f, 1.0f),
					MinecartSoundConfig.volume,
					value -> {
						MinecartSoundConfig.volume = value;
						MinecartSoundConfig.save();
					}
			);

			// 反射寻找原版界面的滚动列表，并将滑块直接添加到列表末尾
			Object listObj = findListObject(soundScreen);
			if (listObj != null) {
				try {
					Method addSmallMethod = listObj.getClass().getMethod("addSmall", OptionInstance[].class);
					addSmallMethod.invoke(listObj, (Object) new OptionInstance[]{minecartOption});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 反射遍历字段，寻找原版的 OptionsList / ButtonListWidget
	 */
	private Object findListObject(Object screen) {
		Class<?> clazz = screen.getClass();
		while (clazz != null && clazz != Object.class) {
			for (Field field : clazz.getDeclaredFields()) {
				String typeName = field.getType().getName();
				if (typeName.contains("OptionsList") || typeName.contains("ButtonListWidget") || typeName.endsWith("ListWidget")) {
					field.setAccessible(true);
					try {
						return field.get(screen);
					} catch (Exception ignored) {}
				}
			}
			clazz = clazz.getSuperclass();
		}
		return null;
	}
}