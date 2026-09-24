package com.windtempos.tows.mixin;

import com.windtempos.tows.TalesOfWaywardStars;
import com.windtempos.tows.config.TOWSConfig;
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

import static com.windtempos.tows.TalesOfWaywardStars.LOGGER;
import static com.windtempos.tows.TalesOfWaywardStars.enabled;

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
        SocketAddress socketAddress = clientPacketListener.getConnection().getRemoteAddress();

        String regexConvert = TOWSConfig.HANDLER.instance().serverIP
                .replaceAll("\\.", ".")
                .replace("*", "\\S+");
        String REGEX = regexConvert + "\\S*";

        boolean isMatch = socketAddress.toString().matches(REGEX);
        boolean isLocal = socketAddress.toString().matches("local\\S*");

        if (TOWSConfig.HANDLER.instance().forceEnabled) {
            if (isLocal) {
                LOGGER.info("Forcing enabled for singleplayer world");
            } else {
                LOGGER.info("Forcing enabled for server IP {}", socketAddress);
            }
            enabled = true;
        } else if (isLocal) {
            if (TOWSConfig.HANDLER.instance().enableInSingleplayer) {
                LOGGER.info("Server is local, enabling for singleplayer world");
                enabled = true;
            } else {
                LOGGER.info("Server is local, disabling for singleplayer world");
                enabled = false;
            }
        } else if (isMatch) {
            LOGGER.info("Server IP {} is a match for regex {}, enabling", socketAddress, REGEX);
            enabled = true;
        } else {
            LOGGER.info("Server IP {} is not a match for regex {}, disabling", socketAddress, REGEX);
            enabled = false;
        }
    }
}
