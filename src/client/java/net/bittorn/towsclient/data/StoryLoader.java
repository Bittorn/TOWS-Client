package net.bittorn.towsclient.data;

import com.bladecoder.ink.runtime.Story;
import net.bittorn.towsclient.TOWSClient;
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

public class StoryLoader implements SimpleResourceReloadListener<Map<Identifier, Story>> {

    public static final String DIALOG_PATH = "tows/dialog";

    public static void register() {
        StoryLoader instance = new StoryLoader();
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(instance);
    }

    @Override
    public CompletableFuture<Map<Identifier, Story>> load(ResourceManager resourceManager, Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            Map<Identifier, Story> data = new HashMap<>();
            resourceManager.findResources(DIALOG_PATH, (res) -> res.getPath().endsWith(".json")).forEach((location, resource) -> {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
                    Identifier id = Identifier.of(location.getNamespace(), location.getPath().substring(DIALOG_PATH.length() + 1, location.getPath().length() - 5));

                    StringBuilder sb = new StringBuilder();
                    String line = br.toString();

                    while (line != null) {
                        sb.append(line);
                        sb.append("\n");
                        line = br.readLine();
                    }

                    data.put(id, new Story(sb.toString()));
                } catch (Exception e) {
                    TOWSClient.LOGGER.error("Could not load resource from {}", location, e);
                    throw new RuntimeException(e);
                }
            });
            return data;
        }, executor);
    }

    @Override
    public CompletableFuture<Void> apply(Map<Identifier, Story> identifierStoryMap, ResourceManager resourceManager, Executor executor) {
        return CompletableFuture.runAsync(() -> StoryManager.setEntries(identifierStoryMap), executor);
    }

    @Override
    public Identifier getFabricId() {
        return Identifier.of(TOWSClient.MOD_ID, "story");
    }

    private StoryLoader() {} // don't exactly know why this is here but whatever
}
