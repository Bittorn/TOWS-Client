package net.bittorn.towsclient.data.npc;

import com.bladecoder.ink.runtime.Story;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonFactoryBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.bittorn.towsclient.TOWSClient;
import net.bittorn.towsclient.data.npc.models.BasicNPCModel;
import net.bittorn.towsclient.data.story.StoryRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class NPCResourceLoader implements SimpleResourceReloadListener<Map<Identifier, BasicNPCModel>> {

    public static final String NPC_PATH = "npc";

    public static void register() {
        NPCResourceLoader instance = new NPCResourceLoader();
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(instance);
    }

    @Override
    public CompletableFuture<Map<Identifier, BasicNPCModel>> load(ResourceManager resourceManager, Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            Map<Identifier, BasicNPCModel> data = new HashMap<>();
            resourceManager.findResources(NPC_PATH, (res) -> res.getPath().endsWith(".json")).forEach((location, resource) -> {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
                    Identifier id = Identifier.of(location.getNamespace(), location.getPath().substring(NPC_PATH.length() + 1, location.getPath().length() - 5));

                    TOWSClient.LOGGER.info("Reading new NPC JSON: {}", id.toString());

                    BasicNPCModel npc = new Gson().fromJson(br, BasicNPCModel.class);

                    data.put(id, npc);

                    TOWSClient.LOGGER.info("Successfully registered NPC: {}", id.toString());
                } catch (Exception e) {
                    TOWSClient.LOGGER.error("Could not load NPC from {}", location, e);
                    throw new RuntimeException(e);
                }
            });
            return data;
        }, executor);
    }

    @Override
    public CompletableFuture<Void> apply(Map<Identifier, BasicNPCModel> basicNPCModelMap, ResourceManager resourceManager, Executor executor) {
        return CompletableFuture.runAsync(() -> NPCRegistry.setEntries(basicNPCModelMap), executor);
    }

    @Override
    public Identifier getFabricId() {
        return Identifier.of(TOWSClient.MOD_ID, NPC_PATH);
    }

    private NPCResourceLoader() {
        // no-op constructor
    }
}
