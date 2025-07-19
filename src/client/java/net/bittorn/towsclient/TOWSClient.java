package net.bittorn.towsclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

public class TOWSClient implements ClientModInitializer {

	public static final Logger LOG = LogManager.getLogger("TOWSClient");

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		KeyBinding interactBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.towsclient.interact", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "key.category.towsclient"));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (interactBinding.wasPressed()) {
				client.player.sendMessage(Text.literal("Interact was pressed!"), false); // placeholder logic
			}
		});
	}

	// Logs an info line.
	public static void info(String message) {
        LOG.info("[TOWSClient] {}", message);
	}

	// Logs a warning line if enabled in config.
	public static void logWarnLoading(String message) {
//		if (CITResewnConfig.INSTANCE.mute_warns)
//			return;
        LOG.error("[TOWSClient] {}", message);
	}

	// Logs an error line if enabled in config.
	public static void logErrorLoading(String message) {
//		if (CITResewnConfig.INSTANCE.mute_errors)
//			return;
        LOG.error("{TOWSClient} {}", message);
	}
}