package org.cmdutils.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.cmdutils.MainClient;
import org.cmdutils.terminal.gui.InGameTerminalGui;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Shadow
    public static boolean hasControlDown() {
        return false;
    }

    @Shadow
    @Nullable
    protected MinecraftClient client;

    @Inject(at = @At("TAIL"), method = "keyPressed")
    public void keyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (hasControlDown() && keyCode == MainClient.terminalKey.boundKey.getCode()) {
            if (this.client != null && !(this.client.currentScreen instanceof InGameTerminalGui)) {
                this.client.setScreen(new InGameTerminalGui(this.client.currentScreen, this.client.player == null ? null : this.client.player.currentScreenHandler));
            }
        }
    }
}
