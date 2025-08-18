package net.bittorn.towsclient.data.story;

import com.bladecoder.ink.runtime.Story;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class StoryRegistry {

    private static Map<Identifier, Story> entries = Map.of();

    public static Set<Identifier> getIds() {
        return entries.keySet();
    }

    public static Optional<Story> getOrEmpty(Identifier id) {
        return Optional.ofNullable(entries.get(id));
    }

    public static boolean containsId(Identifier id) {
        return getIds().contains(id);
    }

    static void setEntries(Map<Identifier, Story> newEntries) {
        entries = newEntries;
    }

    static void appendEntry(Identifier newId, Story newStory) {
        entries.put(newId, newStory);
    }
}
