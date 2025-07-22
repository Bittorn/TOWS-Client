package net.bittorn.towsclient;

import net.bittorn.towsclient.screens.DialogScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class TOWSClientKeybinds {
    public static void register() {
        KeyBinding interactBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.towsclient.interact", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "key.category.towsclient"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (interactBinding.wasPressed()) {
//                client.player.sendMessage(Text.literal("Interact was pressed!"), true); // placeholder logic
                MinecraftClient.getInstance().setScreen(new DialogScreen(Text.empty()));
            }
        });
    }
}
