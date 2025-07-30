package net.bittorn.towsclient;

import net.bittorn.towsclient.data.ResourceManager;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.SocketAddress;

public class TOWSClient implements ClientModInitializer {

	public static final String MOD_ID = "towsclient";
	public static final Logger LOG = LogManager.getLogger(MOD_ID);

	public static SocketAddress serverAddress = null;

	@Override
	public void onInitializeClient() {
		KeybindManager.register();
		ResourceManager.register();
	}
}