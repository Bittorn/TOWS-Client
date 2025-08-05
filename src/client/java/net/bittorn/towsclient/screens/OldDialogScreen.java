package net.bittorn.towsclient.screens;

import net.bittorn.towsclient.TOWSClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;

public class OldDialogScreen extends Screen {
    public static final Identifier DIALOG_BOX_TEXTURE = Identifier.of(TOWSClient.MOD_ID, "textures/gui/dialog/dialog_box_gui.png");

    public OldDialogScreen(Text title) {
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

        int imageWidth = 384;
        int imageHeight = 128;

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) - 24;

        context.drawTexture(RenderLayer::getGuiTextured, DIALOG_BOX_TEXTURE, x, y, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight, new Color(255, 255, 255, 255).hashCode());

        String dialog = "This is some dialog!\nYippee!!";
        context.drawWrappedTextWithShadow(this.textRenderer, StringVisitable.plain(dialog),
                width / 2 - this.textRenderer.getWidth(dialog) / 2,
                y + imageHeight / 2 - this.textRenderer.getWrappedLinesHeight(dialog, 320) / 2,
                320, 0xFFFFFFFF);
    }
}
