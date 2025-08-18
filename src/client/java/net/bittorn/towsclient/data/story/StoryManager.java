package net.bittorn.towsclient.data.story;

import com.bladecoder.ink.runtime.Choice;
import com.bladecoder.ink.runtime.Story;
import net.bittorn.towsclient.TOWSClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;

public class StoryManager {

    Story story;
    Identifier storyId;

    HashMap<String, String> globalTags = new HashMap<String, String>();

    public Text speaker = Text.of("Placeholder speaker");
    public Text tooltip = Text.of("Placeholder tooltip");

    public Text line = Text.of("Placeholder line");

    public StoryManager(String pathToStory) {
        storyId = Identifier.of(TOWSClient.MOD_ID, pathToStory);

        // Creating Story object from JSON
        try {
            story = StoryRegistry.getOrEmpty(storyId).orElseThrow();
        } catch (Exception e) {
            TOWSClient.LOGGER.error("Error parsing Story: {}", storyId, e);
            throw new RuntimeException(e);
        }

        loadTags();
        parseTags();
    }

    private void loadTags() {
        try {
            List<String> tags = story.getGlobalTags();
            for (String tag : tags) {
                String[] splitTag = tag.split(":");
                globalTags.put(splitTag[0].trim(), splitTag[1].trim());
            }
        } catch (Exception e) {
            TOWSClient.LOGGER.error("Error loading Story tags: {}", storyId, e);
        }
    }

    private void parseTags() {
        if (globalTags.containsKey("speaker")) speaker = Text.translatable(globalTags.get("speaker"));
        if (globalTags.containsKey("tooltip")) tooltip = Text.translatable(globalTags.get("tooltip"));
    }

    public void continueOrExitStory() {
        try {
            if (story.canContinue()) {
                line = Text.translatable(story.Continue().trim());
                if (!story.getCurrentChoices().isEmpty()) {
                    HashMap<Integer, String> choices = new HashMap<Integer, String>();
                    for (Choice choice : story.getCurrentChoices()) {
                        choices.put(choice.getIndex(), choice.getText());
                    }
                    TOWSClient.LOGGER.info(choices);
                }
            } else if (story.getCurrentChoices().isEmpty()) {
                exitStory();
            }
        } catch (Exception e) {
            TOWSClient.LOGGER.error("Error continuing Story: {}", storyId, e);
            throw new RuntimeException(e);
        }
    }

    public void exitStory() {
        try { story.resetState(); } catch (Exception e) {
            TOWSClient.LOGGER.error("Error resetting Story state: {}", storyId, e);
            throw new RuntimeException(e);
        }
        MinecraftClient.getInstance().setScreen(null);
    }
}
