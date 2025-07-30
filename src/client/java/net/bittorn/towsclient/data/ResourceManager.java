package net.bittorn.towsclient.data;

import net.bittorn.towsclient.TOWSClient;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.io.InputStream;

public class ResourceManager {
    public static void register() {
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return Identifier.of(TOWSClient.MOD_ID, "mod_resources");
            }

            @Override
            public void reload(net.minecraft.resource.ResourceManager manager) {
                // Clear caches here
                for (Identifier id : manager.findResources("dialog", path -> path.toString().endsWith(".json")).keySet()) {
                    try (InputStream stream = manager.getResourceOrThrow(id).getInputStream()) {
                        // Consume the stream
                        // Remember to close resource stream after processing.
                        // Forgetting to do this is an easy way to end up with a resource leak.
                    } catch (Exception e) {
                        TOWSClient.LOG.error("Error occurred while loading resource JSON {}", id.toString());
                    }
                }
            }
        });
    }
}
