package net.bittorn.towsclient.data;

import com.bladecoder.ink.runtime.Story;
import com.bladecoder.ink.runtime.VariablesState;
import net.bittorn.towsclient.TOWSClient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Dictionary;
import java.util.List;

public class StoryManager {

    Story story;
    public Identifier storyId;
    public Dictionary<String, Object> variables;

    public Text speaker;
    public Text tooltip;

    public Text line = Text.of("placeholder");

    public StoryManager(String pathToStory) {
        storyId = Identifier.of(TOWSClient.MOD_ID, pathToStory);

        try {
            story = StoryRegistry.getOrEmpty(storyId).orElseThrow();

            VariablesState variablesState = story.getVariablesState();
            variablesState.forEach(variable -> {
                variables.put(variable, variablesState.get(variable));
            });
        } catch (Exception e) {
            TOWSClient.LOGGER.error("Error loading Story: {}", storyId, e);
        }

        speaker = Text.translatableWithFallback(variables.get("speaker").toString(), variables.get("speaker").toString());

        tooltip = Text.translatableWithFallback(variables.get("tooltip").toString(), variables.get("tooltip").toString());
    }
}
