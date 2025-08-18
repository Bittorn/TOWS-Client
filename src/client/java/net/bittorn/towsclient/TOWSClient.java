package net.bittorn.towsclient;

import net.bittorn.towsclient.config.TOWSConfig;
import net.bittorn.towsclient.data.story.StoryLoader;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TOWSClient implements ClientModInitializer {

	public static final String MOD_ID = "towsclient";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	public static final TOWSConfig CONFIG = TOWSConfig.createAndLoad();

	public static boolean enabled;

	@Override
	public void onInitializeClient() {
		KeybindManager.register();
        StoryLoader.register();
	}
}