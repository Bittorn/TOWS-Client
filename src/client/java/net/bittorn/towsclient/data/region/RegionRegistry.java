package net.bittorn.towsclient.data.region;

import net.bittorn.towsclient.data.npc.models.BasicNPCData;
import net.minecraft.util.Identifier;

import java.util.Map;

@SuppressWarnings("unused")
public class RegionRegistry {
    @SuppressWarnings("FieldMayBeFinal")
    private static ArrayList<RegionData> entries = Map.of();

    public static void setEntry(Map<Identifier, RegionData> newEntry) {
        entries = newEntry;
    }
}
