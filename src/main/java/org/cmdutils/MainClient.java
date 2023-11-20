package org.cmdutils;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.cmdutils.terminal.gui.InGameTerminalGui;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("cmd-utils");

	public static KeyBinding terminalKey;

	@Override
	public void onInitializeClient() {
		try {
			System.setProperty("java.awt.headless", "false");
			System.setProperty("apple.awt.contentScaleFactor", "1");
			System.setProperty("apple.awt.graphics.UseQuartz", "false");
		} catch (Exception e) {
			LOGGER.warn("Failed to initialize Swing compatibility options on startup.", e);
		}

		terminalKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.terminal",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_Y,
				"key.categories.cmdutils"
		));

		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			while (terminalKey.wasPressed()) {
				client.setScreen(new InGameTerminalGui(null, null));
			}
		});
	}
}