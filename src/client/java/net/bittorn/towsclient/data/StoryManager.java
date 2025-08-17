package net.bittorn.towsclient.data;

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

    HashMap<String, String> tags = new HashMap<String, String>();

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

        // Loading Story tags
        try {
            List<String> tagsList = story.getGlobalTags();
            for (String tag : tagsList) {
                String[] splitTag = tag.split(":.");
                tags.put(splitTag[0].trim(), splitTag[1].trim());
            }
        } catch (Exception e) {
            TOWSClient.LOGGER.error("Error loading Story tags: {}", storyId, e);
        }

        // Parsing Story tags
        if (tags.containsKey("speaker")) speaker = Text.translatable(tags.get("speaker"));
        if (tags.containsKey("tooltip")) tooltip = Text.translatable(tags.get("tooltip"));
    }

    public String continueOrExitStory() {
        if (story.canContinue()) {

        } else if (story.getCurrentChoices().isEmpty()) {
            exitStory();
        }
        return null;
    }

    public void exitStory() {
        MinecraftClient.getInstance().setScreen(null);
    }
}
