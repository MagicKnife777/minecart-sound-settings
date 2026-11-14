package com.magicknife.minecartsound.client.mixin;

import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Screen.class)
public interface ScreenAccessor {
    // 将 @Invoker("addDrawableChild") 改为 @Invoker("addRenderableWidget")
    @Invoker("addRenderableWidget")
    <T extends GuiEventListener & NarratableEntry> T invokeAddRenderableWidget(T widget);
}