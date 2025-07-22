package net.bittorn.towsclient.screens;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class DialogScreen extends Screen {

    public DialogScreen(Text title) {
        super(title);
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
        // do some stuff
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }
}
