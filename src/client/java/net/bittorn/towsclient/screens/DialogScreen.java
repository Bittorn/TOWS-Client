package net.bittorn.towsclient.screens;

import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.component.LabelComponent;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.Color;
import net.bittorn.towsclient.TOWSClient;
import net.bittorn.towsclient.data.StoryManager;
import net.minecraft.client.gui.Element;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class DialogScreen extends BaseUIModelScreen<FlowLayout> {

    StoryManager storyManager;

    public DialogScreen(String pathToStory) {
        super(FlowLayout.class, DataSource.asset(Identifier.of(TOWSClient.MOD_ID, "dialog_screen_model")));
        storyManager = new StoryManager(pathToStory);
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        rootComponent.childById(LabelComponent.class, "speaker-label")
                .text(storyManager.speaker)
                .color(Color.WHITE);
        rootComponent.childById(LabelComponent.class, "text-label")
                .text(storyManager.line)
                .color(Color.WHITE);
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
