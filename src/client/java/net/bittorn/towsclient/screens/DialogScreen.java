package net.bittorn.towsclient.screens;

import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.LabelComponent;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.Color;
import net.bittorn.towsclient.TOWSClient;
import net.bittorn.towsclient.data.story.StoryManager;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

public class DialogScreen extends BaseUIModelScreen<FlowLayout> {

    FlowLayout root;
    StoryManager storyManager;
    InputUtil.Key interactKey = KeyBindingHelper.getBoundKeyOf(Objects.requireNonNull(KeyBinding.byId("key.towsclient.interact")));

    boolean justEntered = false;

    public DialogScreen(String pathToStory) {
        super(FlowLayout.class, DataSource.asset(Identifier.of(TOWSClient.MOD_ID, "dialog_screen_model")));
        storyManager = new StoryManager(pathToStory, this);
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        root = rootComponent;
        storyManager.continueOrExitStory();
        root.childById(LabelComponent.class, "speaker-label")
                .text(storyManager.speaker)
                .color(Color.WHITE)
                .tooltip(storyManager.tooltip);
        root.childById(LabelComponent.class, "controls-label")
                .text(Text.of("⏴ Press '" + interactKey.getLocalizedText().getLiteralString() + "' or click to continue ⏵"));

        // Here to avoid accidentally continuing when we're not supposed to
        justEntered = true;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        root.childById(LabelComponent.class, "text-label")
                .text(storyManager.line)
                .color(Color.WHITE);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false; // no pausing on servers
    }

    public void setChoices(HashMap<Integer, String> choices) {

        int color = 0xC0101010;
        int hoveredColor = 0xFF101010;
        int disabledColor = 0x44101010;

        for (int index : choices.keySet()) {
            FlowLayout choicesBox = root.childById(FlowLayout.class, "choices-box");
            choicesBox.child(
                    Components.button(Text.translatable(choices.get(index)), button -> {
                        storyManager.selectChoice(index);
                        choicesBox.clearChildren();
                    }).renderer(ButtonComponent.Renderer.flat(color, hoveredColor, disabledColor)));
        }
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        // Here to avoid accidentally continuing when we're not supposed to
        if (justEntered) {
            justEntered = false;
            return super.keyReleased(keyCode, scanCode, modifiers);
        }
        if (keyCode == interactKey.getCode()) {
            storyManager.continueOrExitStory();
        }
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    // This seems bad... oh well!
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        storyManager.continueOrExitStory();
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
