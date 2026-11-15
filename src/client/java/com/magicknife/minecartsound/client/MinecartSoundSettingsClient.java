package com.magicknife.minecartsound.client;

import com.mojang.serialization.Codec;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.screens.options.SoundOptionsScreen;
import net.minecraft.network.chat.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

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

			Object listObj = findListObject(soundScreen);
			if (listObj == null) return;

			try {
				Method addSmallMethod = listObj.getClass().getMethod("addSmall", OptionInstance[].class);
				addSmallMethod.invoke(listObj, (Object) new OptionInstance[]{minecartOption});

				// 注入成功后，调整位置
				moveWidgetToVolumeSection(listObj);

				// 【新增】物理魔法：假装滚动一下，强制触发原版的尺寸重算
				forceUpdateByFakeScroll(listObj);

			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

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

	@SuppressWarnings("unchecked")
	private void moveWidgetToVolumeSection(Object listObj) {
		try {
			Class<?> listClazz = listObj.getClass();
			Field childrenField = null;

			while (listClazz != null && listClazz != Object.class) {
				for (Field f : listClazz.getDeclaredFields()) {
					if (List.class.isAssignableFrom(f.getType())) {
						childrenField = f;
						break;
					}
				}
				if (childrenField != null) break;
				listClazz = listClazz.getSuperclass();
			}

			if (childrenField != null) {
				childrenField.setAccessible(true);
				List<Object> children = (List<Object>) childrenField.get(listObj);

				if (children.size() < 5) return;

				Object ourWidget = children.remove(children.size() - 1);
				int insertIndex = children.size() - 3;
				if (insertIndex < 0) insertIndex = 0;

				children.add(insertIndex, ourWidget);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 假装滚动一下列表，触发底层的 clampScrollAmount 和 高度重算
	 */
	private void forceUpdateByFakeScroll(Object listObj) {
		try {
			for (Method method : listObj.getClass().getMethods()) {
				if (method.getName().equals("setScrollAmount") && method.getParameterCount() == 1 && method.getParameterTypes()[0] == double.class) {
					method.setAccessible(true);
					// 先滚下去 0.001，再滚回 0，原版就会被骗到，重新计算并渲染所有元素
					method.invoke(listObj, 0.001);
					method.invoke(listObj, 0.0);
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}