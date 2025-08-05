package net.bittorn.towsclient.mixin.client;

import net.bittorn.towsclient.TOWSClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.SocketAddress;
import java.util.regex.Pattern;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {

    @Inject(at = @At("RETURN"), method = "<init>")
    private void constructor(
            ClientPlayNetworkHandler networkHandler,
            ClientWorld.Properties properties,
            RegistryKey registryRef,
            RegistryEntry dimensionType,
            int loadDistance,
            int simulationDistance,
            WorldRenderer worldRenderer,
            boolean debugWorld,
            long seed,
            int seaLevel,
            CallbackInfo ci
    ) {
        SocketAddress newAddress = networkHandler.getConnection().getAddress();

        TOWSClient.LOGGER.info("Setting IP address to {}", newAddress);
        TOWSClient.SERVER_ADDRESS = newAddress;

        String regexConvert = TOWSClient.CONFIG.serverIP()
                .replaceAll("\\.", "\\.")
                .replace("*", "\\S+");
        String REGEX = regexConvert + "\\S*";

        String ipMatch = "IP " + (Pattern.matches(REGEX, TOWSClient.SERVER_ADDRESS.toString()) ? "matches" : "does not match");
        boolean isLocal = Pattern.matches("local\\S*", TOWSClient.SERVER_ADDRESS.toString());

        String dialog = isLocal ? "Server is local" : ipMatch;

        TOWSClient.LOGGER.info(dialog);
    }
}