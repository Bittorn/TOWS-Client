package net.bittorn.towsclient.screens;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class TOWSClientConfigScreen extends Screen {
    public Screen parent;
    public TOWSClientConfigScreen(Text title, Screen parent) {
        super(title);
        this.parent = parent;
    }

    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }

    @Override
    protected void init() {
        assert this.client != null; // Get rid of stupid warnings
        ButtonWidget buttonWidget = ButtonWidget.builder(Text.translatable("gui.back"), (btn) -> {
            this.close();
        }).dimensions(20,
                20,
                60, 20).build();
        // Recommended to use the fixed height of 20 to prevent rendering issues with vanilla button textures.

        // Register the button widget.
        this.addDrawableChild(buttonWidget);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        // Minecraft doesn't have a "label" widget, so we'll have to draw our own text.
        // We'll subtract the font height from the Y position to make the text appear above the button.
        // Subtracting an extra 10 pixels will give the text some padding.
        // textRenderer, text, x, y, color, hasShadow
        assert this.client != null; // Get rid of dumbass warnings
        MutableText configTitle = Text.translatable("config.towsclient.title");
        context.drawText(this.textRenderer, configTitle,
                this.client.getWindow().getScaledWidth() / 2 - this.textRenderer.getWidth(configTitle) / 2, 20,
                0xFFFFFFFF, true);

        context.drawText(this.textRenderer, "Nothing to configure yet!",
                this.client.getWindow().getScaledWidth() / 2 - this.textRenderer.getWidth(configTitle) / 2,
                this.client.getWindow().getScaledHeight() / 2,
                0xFFFFFFFF, true);
    }
}
