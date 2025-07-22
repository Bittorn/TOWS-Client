package net.bittorn.towsclient.screens;

import net.bittorn.towsclient.TOWSClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;

public class DialogScreen extends Screen {
    public static final Identifier DIALOG_BOX_TEXTURE = Identifier.of(TOWSClient.MOD_ID, "textures/gui/dialog/dialog_box_gui.png");

    public DialogScreen(Text title) {
        super(title);
    }

    @Override
    public boolean shouldPause() {
        return false; // cannot pause on server
    }

    @Override
    public void applyBlur() {
        // NO-OP
    }

    @Override
    protected void renderDarkening(DrawContext context) {
        // NO-OP
    }

    @Override
    protected void init() {
        // do stuff here
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        assert this.client != null; // stop warnings

        int imageWidth = 512;
        int imageHeight = 128;

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) - 24;

        String dialog = "This is some placeholder dialog!";

        context.drawTexture(RenderLayer::getGuiTextured, DIALOG_BOX_TEXTURE, x, y, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight, new Color(255, 255, 255, 127).hashCode());

        context.drawWrappedTextWithShadow(this.textRenderer, StringVisitable.plain(dialog),
                width / 2 - this.textRenderer.getWidth(dialog) / 2, y + imageHeight / 2,
                imageWidth - 24, 0xFFFFFFFF);
    }
}
