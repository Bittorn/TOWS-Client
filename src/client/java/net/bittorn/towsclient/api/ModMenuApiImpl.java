package net.bittorn.towsclient.api;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.bittorn.towsclient.screens.TOWSClientConfigScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ModMenuApiImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return new ConfigScreenFactory<Screen>() {
            @Override
            public Screen create(Screen parent) {
                return new TOWSClientConfigScreen(Text.empty(), MinecraftClient.getInstance().currentScreen);
            }
        };
    }
}
