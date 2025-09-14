package net.bittorn.towsclient.data.region;

import com.google.gson.Gson;
import net.bittorn.towsclient.TOWSClient;
import net.bittorn.towsclient.data.npc.NPCRegistry;
import net.bittorn.towsclient.data.npc.NPCResourceLoader;
import net.bittorn.towsclient.data.npc.models.BasicNPCData;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class RegionResourceLoader implements SimpleResourceReloadListener<ArrayList<RegionData>> {

    public static void register() {
        RegionResourceLoader instance = new RegionResourceLoader();
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(instance);
    }

    @Override
    public CompletableFuture<ArrayList<RegionData>> load(ResourceManager resourceManager, Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            ArrayList<RegionData> data = new ArrayList<>();
//            resourceManager.findResources("", (res) -> res.getPath().endsWith(".json")).forEach((location, resource) -> {
//                try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
//                    Identifier id = Identifier.of(location.getNamespace(), location.getPath().substring(0, location.getPath().length() - 5));
//
//                    TOWSClient.LOGGER.info("Reading regions JSON: {}", id.toString());
//
//                    BasicNPCData npc = new Gson().fromJson(br, BasicNPCData.class);
//
//                    data.put(id, npc);
//
//                    TOWSClient.LOGGER.info("Successfully registered NPC: {}", id.toString());
//                } catch (Exception e) {
//                    TOWSClient.LOGGER.error("Could not load NPC from {}", location, e);
//                    throw new RuntimeException(e);
//                }
//            });
            try (BufferedReader br = new BufferedReader(new InputStreamReader(resourceManager.getResourceOrThrow(Identifier.of(TOWSClient.MOD_ID,"regions.json")).getInputStream(), StandardCharsets.UTF_8))) {
                TOWSClient.LOGGER.info("Reading regions.json");

                RegionData regions = new Gson().fromJson(br, RegionData.class);

                data.add(regions);

                TOWSClient.LOGGER.info("Successfully registered regions");
            } catch (Exception e) {
                TOWSClient.LOGGER.error("Could not load regions", e);
                throw new RuntimeException(e);
            }
            return data;
        }, executor);
    }

    @Override
    public CompletableFuture<Void> apply(ArrayList<RegionData> data, ResourceManager manager, Executor executor) {
        return CompletableFuture.runAsync(() -> RegionRegistry.setEntry(data), executor);
    }

    @Override
    public Identifier getFabricId() {
        return null;
    }
}
