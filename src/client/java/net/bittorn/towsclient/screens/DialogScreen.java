package net.bittorn.towsclient.screens;

import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.container.FlowLayout;
import net.bittorn.towsclient.TOWSClient;
import net.minecraft.client.gui.Element;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class DialogScreen extends BaseUIModelScreen<FlowLayout> {

    public DialogScreen() {
        super(FlowLayout.class, DataSource.asset(Identifier.of(TOWSClient.MOD_ID, "dialog_screen_model")));
    }

    @Override
    protected void build(FlowLayout rootComponent) {
//        rootComponent.childById(ButtonComponent.class, "the-button").onPress(button -> {
//            System.out.println("click");
//        });
    }

    @Override
    public boolean shouldPause() {
        return false; // no pausing on servers
    }

    @Override
    public Optional<Element> hoveredElement(double mouseX, double mouseY) {
        return super.hoveredElement(mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
