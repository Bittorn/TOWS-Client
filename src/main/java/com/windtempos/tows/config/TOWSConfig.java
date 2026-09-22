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

    @SerialEntry(comment = "Whether to enable the mod on singleplayer worlds")
    public boolean enableInSingleplayer = _enableInSingleplayer;
    private static final boolean _enableInSingleplayer = true;

    @SerialEntry(comment = "Server IP to match against")
    public String serverIP = _serverIP;
    private static final String _serverIP = "*.callmecarson.live";

    @SerialEntry
    public boolean forceEnabled = _forceEnabled;
    private static final boolean _forceEnabled = false;

    public Screen getConfigScreen(Screen previous) {
        HANDLER.load();
        var config = HANDLER.instance();
        return YetAnotherConfigLib.createBuilder()
                .title(Component.literal("Tales of Wayward Stars Configuration")) // used for narration
                .category(ConfigCategory.createBuilder()
                        .name(Component.literal("Client")) // name of category
                        .tooltip(Component.literal("Client configuration options")) // hover tooltip
                        .group(OptionGroup.createBuilder()
                                .name(Component.literal("Connection")) // group name
                                .description(OptionDescription.of(Component.literal("Connection options"))) // hover description, TODO
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.literal("Enable in singleplayer"))
                                        .description(OptionDescription.of(Component.literal("Whether to enable the mod in singleplayer worlds.")))
                                        .binding(_enableInSingleplayer, () -> config.enableInSingleplayer, newVal -> config.enableInSingleplayer = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<String>createBuilder()
                                        .name(Component.literal("Server IP"))
                                        .description(OptionDescription.of(Component.literal("Server IP to match against. Use '*' to match against a range.")))
                                        .binding(_serverIP, () -> config.serverIP, newVal -> config.serverIP = newVal)
                                        .controller(StringControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.literal("Force enable"))
                                        .description(OptionDescription.of(Component.literal("Force enables the mod in all environments. Useful for debugging.")))
                                        .binding(_forceEnabled, () -> config.forceEnabled, newVal -> config.forceEnabled = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .build())
                        .build())
                .save(() -> HANDLER.save())
                .build()
                .generateScreen(previous);
    }
}
