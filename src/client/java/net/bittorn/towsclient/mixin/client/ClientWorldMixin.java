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

        String regexConvert = TOWSClient.CONFIG.serverIP()
                .replaceAll("\\.", "\\.")
                .replace("*", "\\S+");
        String REGEX = regexConvert + "\\S*";

        boolean isMatch = Pattern.matches(REGEX, newAddress.toString());
        boolean isLocal = Pattern.matches("local\\S*", newAddress.toString());

        String ipMatch = isLocal ? "Server is local" : "IP " + ((isMatch) ? "matches" : "does not match");

        TOWSClient.LOGGER.info(ipMatch);

        if (isMatch || (isLocal && TOWSClient.CONFIG.enableLocally())) TOWSClient.enabled = true;
        if (!TOWSClient.CONFIG.enabled()) TOWSClient.enabled = false;
    }
}