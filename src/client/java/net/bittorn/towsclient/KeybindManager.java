package net.bittorn.towsclient;

import net.bittorn.towsclient.data.npc.NPCRegistry;
import net.bittorn.towsclient.data.npc.models.BasicNPCData;
import net.bittorn.towsclient.screens.DialogScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.glfw.GLFW;

import java.io.File;

public class KeybindManager {
    public static void register() {
        KeyBinding interactBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.towsclient.interact", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "key.category.towsclient"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (interactBinding.wasPressed()) {
                if (!TOWSClient.enabled) return;

                Identifier npcId = Identifier.of(TOWSClient.MOD_ID, "skyland" + File.separator + "placeholder_npc");

                try {
                    BasicNPCData npc = NPCRegistry.getOrEmpty(npcId).orElseThrow();

                    int interactionRange = 4;
                    assert client.player != null;

                    BlockPos playerBlockPos = client.player.getBlockPos();

//                  // placeholder code for regions
//                    boolean isWithinRangeX = (clientBlockPos.getX() >= -16 && clientBlockPos.getX() <= 16);
//                    boolean isWithinRangeZ = (clientBlockPos.getZ() >= -16 && clientBlockPos.getZ() <= 16);
//                    if (!isWithinRangeX || !isWithinRangeZ) {
//                        return;
//                    }

                    if (playerBlockPos.isWithinDistance(npc.getPosition(), interactionRange)) {
                        MinecraftClient.getInstance().setScreen(new DialogScreen(npc.getDialogId()));
                    }
                } catch (Exception e) {
                    TOWSClient.LOGGER.error("Error parsing NPC: {}", npcId, e);
                }
            }
        });
    }
}
