package net.bittorn.towsclient;

import net.bittorn.towsclient.data.ResourceManager;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TOWSClient implements ClientModInitializer {

	public static final String MOD_ID = "towsclient";
	public static final Logger LOG = LogManager.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		KeybindManager.register();
		ResourceManager.register();
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