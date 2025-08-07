package net.bittorn.towsclient.screens;

import com.bladecoder.ink.runtime.Story;
import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.component.LabelComponent;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.Color;
import net.bittorn.towsclient.TOWSClient;
import net.bittorn.towsclient.data.StoryRegistry;
import net.minecraft.client.gui.Element;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class DialogScreen extends BaseUIModelScreen<FlowLayout> {

    public DialogScreen(String storyId) {
        super(FlowLayout.class, DataSource.asset(Identifier.of(TOWSClient.MOD_ID, "dialog_screen_model")));
//        Optional<Story> story = StoryRegistry.getOrEmpty(Identifier.of(TOWSClient.MOD_ID, "placeholder_dialog"));
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        rootComponent.childById(LabelComponent.class, "speaker-label")
                .text(Text.of("Villager"))
                .color(Color.WHITE)
                .tooltip(Text.of("Prisoner of God"));
        rootComponent.childById(LabelComponent.class, "text-label")
                .text(Text.of("This is some placeholder text!~ ★"))
                .color(Color.WHITE);
    }

    @Override
    public boolean shouldPause() {
        return false; // no pausing on servers
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return super.shouldCloseOnEsc();
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
