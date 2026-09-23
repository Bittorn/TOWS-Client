package com.windtempos.tows.util

import com.mojang.blaze3d.platform.InputConstants
import com.windtempos.tows.TalesOfWaywardStars
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.KeyMapping
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.resources.Identifier
import org.lwjgl.glfw.GLFW
import java.io.File

class KeybindManager {

    companion object {
        fun register() {

            TalesOfWaywardStars.LOGGER.info("Registering keybinds")

            val interactKey = KeyBindingHelper.registerKeyBinding(
                KeyMapping(
                    "key.tales-of-wayward-stars.interact",
                    InputConstants.Type.KEYSYM,  // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                    GLFW.GLFW_KEY_G,  // The keycode of the key
                    KeyMapping.Category.MISC // The category of the key
                )
            )

            ClientTickEvents.END_CLIENT_TICK.register(ClientTickEvents.EndTick { client: Minecraft? ->
                while (interactKey.consumeClick()) {
                    if (!TalesOfWaywardStars.enabled) return@EndTick

                    val npcId: Identifier =
                        Identifier.fromNamespaceAndPath(
                            TalesOfWaywardStars.MOD_ID,
                            "skyland" + File.separator + "placeholder_npc"
                        )

                    try {
                        //val npc: BasicNPCData = NPCRegistry.getOrEmpty(npcId).orElseThrow()

                        val interactionRange = 4.0
                        checkNotNull(client?.player)

                        val playerBlockPos: BlockPos = client.player!!.blockPosition()

                        //                  // placeholder code for regions
//                    boolean isWithinRangeX = (clientBlockPos.getX() >= -16 && clientBlockPos.getX() <= 16);
//                    boolean isWithinRangeZ = (clientBlockPos.getZ() >= -16 && clientBlockPos.getZ() <= 16);
//                    if (!isWithinRangeX || !isWithinRangeZ) {
//                        return;
//                    }

                        //if (playerBlockPos.closerThan(npc.position, interactionRange)) {
                        //    client.setScreen(DialogScreen(npc.getDialogId()))
                        //}
                    } catch (e: Exception) {
                        TalesOfWaywardStars.LOGGER.error("Error parsing NPC: {}", npcId, e)
                    }
                }
            })

            TalesOfWaywardStars.LOGGER.info("Keybinds registered successfully")
        }
    }
}