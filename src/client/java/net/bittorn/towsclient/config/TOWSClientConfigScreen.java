package net.bittorn.towsclient.config;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class TOWSClientConfigScreen extends Screen {
    protected TOWSClientConfigScreen(Text title) {
        super(title);
    }

    @Override
    protected void init() {

    }
}
