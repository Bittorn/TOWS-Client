package com.windtempos.tows.mixin;

import com.windtempos.tows.TalesOfWaywardStars;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.SocketAddress;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {

    @Inject(at = @At("RETURN"), method = "<init>")
    private void constructor(
            ClientPacketListener clientPacketListener,
            ClientLevel.ClientLevelData clientLevelData,
            ResourceKey resourceKey,
            Holder holder,
            int i,
            int j,
            LevelRenderer levelRenderer,
            boolean bl,
            long l,
            int k,
            CallbackInfo ci
    ) {
        SocketAddress newAddress = clientPacketListener.getConnection().getRemoteAddress();

//        String regexConvert = TOWSClient.CONFIG.serverIP()
//                .replaceAll("\\.", "\\.")
//                .replace("*", "\\S+");
//        String REGEX = regexConvert + "\\S*";
        String REGEX = "\\S+\\.callmecarson\\.live\\S*";

        boolean isMatch = newAddress.toString().matches(REGEX);
        boolean isLocal = newAddress.toString().matches("local\\S*");

        String ipMatch = isLocal ? "Server is local" : "IP " + ((isMatch) ? "matches" : "does not match");

        TalesOfWaywardStars.LOGGER.info(ipMatch);

        //if (isMatch || (isLocal && TOWSClient.CONFIG.enableLocally())) TOWSClient.enabled = true;
        //if (!TOWSClient.CONFIG.enabled()) TOWSClient.enabled = false;

        if (isMatch) TalesOfWaywardStars.enabled = true;
        if (isLocal) TalesOfWaywardStars.enabled = true;
    }
}
