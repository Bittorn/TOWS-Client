package net.bittorn.towsclient.screens;

import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.component.LabelComponent;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.Color;
import net.bittorn.towsclient.TOWSClient;
import net.bittorn.towsclient.data.StoryManager;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class DialogScreen extends BaseUIModelScreen<FlowLayout> {

    StoryManager storyManager;
    InputUtil.Key interactKey = KeyBindingHelper.getBoundKeyOf(KeyBinding.byId("key.towsclient.interact"));

    public DialogScreen(String pathToStory) {
        super(FlowLayout.class, DataSource.asset(Identifier.of(TOWSClient.MOD_ID, "dialog_screen_model")));
        storyManager = new StoryManager(pathToStory);
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        rootComponent.childById(LabelComponent.class, "speaker-label")
                .text(storyManager.speaker)
                .color(Color.WHITE)
                .tooltip(storyManager.tooltip);
        rootComponent.childById(LabelComponent.class, "text-label")
                .text(storyManager.line)
                .color(Color.WHITE);
        rootComponent.childById(LabelComponent.class, "controls-label")
                .text(Text.of("⏴ Press '" + interactKey.getLocalizedText().getLiteralString() + "' to continue ⏵"));
    }

    @Override
    public boolean shouldPause() {
        return false; // no pausing on servers
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
