package net.bittorn.towsclient.data.npc;

import com.bladecoder.ink.runtime.Story;
import net.bittorn.towsclient.data.npc.models.BasicNPCModel;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class NPCRegistry {
    private static Map<Identifier, BasicNPCModel> entries = Map.of();

    public static Set<Identifier> getIds() {
        return entries.keySet();
    }

    public static Optional<BasicNPCModel> getOrEmpty(Identifier id) {
        return Optional.ofNullable(entries.get(id));
    }

    public static boolean containsId(Identifier id) {
        return getIds().contains(id);
    }

    static void setEntries(Map<Identifier, BasicNPCModel> newEntries) {
        entries = newEntries;
    }

    static void appendEntry(Identifier newId, BasicNPCModel newNPC) {
        entries.put(newId, newNPC);
    }
}
