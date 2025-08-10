package net.bittorn.towsclient.data;

import com.bladecoder.ink.runtime.Story;
import com.bladecoder.ink.runtime.VariablesState;
import net.bittorn.towsclient.TOWSClient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class StoryManager {

    Story story;
    Identifier storyId;

    public Text speaker = Text.of("Placeholder speaker");

    public Text line = Text.of("Placeholder line");

    public StoryManager(String pathToStory) {
        storyId = Identifier.of(TOWSClient.MOD_ID, pathToStory);

        try {
            story = StoryRegistry.getOrEmpty(storyId).orElseThrow();
        } catch (Exception e) {
            TOWSClient.LOGGER.error("Error parsing Story: {}", storyId, e);
        }

        try {
            String speakerStr = (String) story.getVariablesState().get("speaker");
            if (!speakerStr.isEmpty()) speaker = Text.translatable(speakerStr);
        } catch (Exception e) {
            TOWSClient.LOGGER.error("Error getting Story speaker: {}", storyId, e);
        }
    }
}
