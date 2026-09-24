package com.windtempos.tows.config;

import com.google.gson.GsonBuilder;
import com.windtempos.tows.TalesOfWaywardStars;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class TOWSConfig {
    public static ConfigClassHandler<TOWSConfig> HANDLER = ConfigClassHandler.createBuilder(TOWSConfig.class)
            .id(Identifier.fromNamespaceAndPath(TalesOfWaywardStars.MOD_ID, "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve(TalesOfWaywardStars.MOD_ID + ".json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting) // not needed, pretty print by default
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry
    public boolean enableInSingleplayer = _enableInSingleplayer;
    private static final boolean _enableInSingleplayer = true;

    @SerialEntry
    public String serverIP = _serverIP;
    private static final String _serverIP = "*.callmecarson.live";

    @SerialEntry(comment = "Useful for testing")
    public boolean forceEnabled = _forceEnabled;
    private static final boolean _forceEnabled = false;

    // debug values
    @SerialEntry
    public boolean ignoreSave = _ignoreSave;
    private static final boolean _ignoreSave = false;

    public Screen getConfigScreen(Screen previous) {
        HANDLER.load();
        var config = HANDLER.instance();
        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("config.tales-of-wayward-stars")) // used for narration
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("config.tales-of-wayward-stars.client"))
                        .tooltip(Component.translatable("config.tales-of-wayward-stars.client.description"))
                        .group(OptionGroup.createBuilder()
                                .name(Component.translatable("config.tales-of-wayward-stars.connection"))
                                .description(OptionDescription.of(Component.translatable("config.tales-of-wayward-stars.connection.description")))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.translatable("config.tales-of-wayward-stars.enable-in-singleplayer"))
                                        .description(OptionDescription.of(Component.translatable("config.tales-of-wayward-stars.enable-in-singleplayer.description")))
                                        .binding(_enableInSingleplayer, () -> config.enableInSingleplayer, newVal -> config.enableInSingleplayer = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<String>createBuilder()
                                        .name(Component.translatable("config.tales-of-wayward-stars.server-ip"))
                                        .description(OptionDescription.of(Component.translatable("config.tales-of-wayward-stars.server-ip.description")))
                                        .binding(_serverIP, () -> config.serverIP, newVal -> config.serverIP = newVal)
                                        .controller(StringControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.translatable("config.tales-of-wayward-stars.force-enabled"))
                                        .description(OptionDescription.of(Component.translatable("config.tales-of-wayward-stars.force-enabled.description")))
                                        .binding(_forceEnabled, () -> config.forceEnabled, newVal -> config.forceEnabled = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("config.tales-of-wayward-stars.debug"))
                        .tooltip(Component.translatable("config.tales-of-wayward-stars.debug.description"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("config.tales-of-wayward-stars.ignore-save"))
                                .description(OptionDescription.of(Component.translatable("config.tales-of-wayward-stars.ignore-save.description")))
                                .binding(_ignoreSave, () -> config.ignoreSave, newVal -> config.ignoreSave = newVal)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .build())
                .save(() -> HANDLER.save())
                .build()
                .generateScreen(previous);
    }
}
