package net.bittorn.towsclient.data.npc;

import net.bittorn.towsclient.data.npc.models.BasicNPCData;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class NPCRegistry {
    private static Map<Identifier, BasicNPCData> entries = Map.of();

    public static Set<Identifier> getIds() {
        return entries.keySet();
    }

    public static Optional<BasicNPCData> getOrEmpty(Identifier id) {
        return Optional.ofNullable(entries.get(id));
    }

    public static boolean containsId(Identifier id) {
        return getIds().contains(id);
    }

    static void setEntries(Map<Identifier, BasicNPCData> newEntries) {
        entries = newEntries;
    }

    static void appendEntry(Identifier newId, BasicNPCData newNPC) {
        entries.put(newId, newNPC);
    }
}
