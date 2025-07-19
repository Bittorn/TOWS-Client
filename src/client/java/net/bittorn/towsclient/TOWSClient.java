package net.bittorn.towsclient;

import net.bittorn.towsclient.dialog.TOWSClientDialogScreen;
import net.bittorn.towsclient.dialog.TOWSClientDialogScreenHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.io.InputStream;

public class TOWSClient implements ClientModInitializer {

	public static final Logger LOG = LogManager.getLogger("TOWSClient");

	@Override
	public void onInitializeClient() {
		// Keybinds
		KeyBinding interactBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.towsclient.interact", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "key.category.towsclient"));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (interactBinding.wasPressed()) {
				//MinecraftClient.getInstance().setScreen(new TOWSClientDialogScreen());
				client.player.sendMessage(Text.literal("Interact was pressed!"), false); // placeholder logic
			}
		});

		// Resources
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
			@Override
			public Identifier getFabricId() {
				return Identifier.of("towsclient", "resources");
			}

			@Override
			public void reload(ResourceManager manager) {
				// Clear caches here

				for (Identifier id : manager.findResources("resource_folder", path -> path.toString().endsWith(".json")).keySet()) {
					try (InputStream stream = manager.getResourceOrThrow(id).getInputStream()) {
						// Consume the stream
						// Remember to close resource stream after processing.
						// Forgetting to do this is an easy way to end up with a resource leak.
					} catch (Exception e) {
						logErrorLoading("Error occurred while loading resource JSON " + id.toString());
					}
				}
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