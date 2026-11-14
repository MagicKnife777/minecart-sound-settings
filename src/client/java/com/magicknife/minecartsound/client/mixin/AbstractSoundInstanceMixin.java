package com.magicknife.minecartsound.client.mixin;

import com.magicknife.minecartsound.client.MinecartSoundConfig;
import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractSoundInstance.class)
public abstract class AbstractSoundInstanceMixin {

    // 拦截所有声音获取音量（getVolume）的方法
    @Inject(method = "getVolume", at = @At("RETURN"), cancellable = true)
    private void onGetVolume(CallbackInfoReturnable<Float> cir) {
        // 获取当前声音实例的类名，如果名字里包含 "minecart"，说明它是矿车声音
        String className = this.getClass().getSimpleName().toLowerCase();
        if (className.contains("minecart")) {
            // 把原版音量 乘以 你设置的百分比，然后返回给游戏
            cir.setReturnValue(cir.getReturnValue() * MinecartSoundConfig.volume);
        }
    }
}