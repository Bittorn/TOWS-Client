package net.bittorn.towsclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;

public class TOWSClient implements ClientModInitializer {

	public static final String MOD_ID = "towsclient";
	public static final Logger LOG = LogManager.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		TOWSClientKeybinds.register();

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
						logError("Error occurred while loading resource JSON " + id.toString());
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
	public static void logWarn(String message) {
        LOG.warn("[TOWSClient] {}", message);
	}

	// Logs an error line if enabled in config.
	public static void logError(String message) {
        LOG.error("{TOWSClient} {}", message);
	}
}