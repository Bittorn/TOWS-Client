package com.windtempos.tows.hud

import com.windtempos.tows.TalesOfWaywardStars
import com.windtempos.tows.util.GameManager
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements
import net.minecraft.client.DeltaTracker
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.core.component.DataComponents
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import net.minecraft.util.ARGB
import net.minecraft.util.Mth
import net.minecraft.util.Util
import net.minecraft.world.item.Items


object HudRenderer {

    fun register() {
        TalesOfWaywardStars.LOGGER.info("Registering HUD elements")

        HudElementRegistry.attachElementBefore(
            VanillaHudElements.CHAT,
            Identifier.fromNamespaceAndPath(TalesOfWaywardStars.MOD_ID, "before_chat"),
            ::render)

        TalesOfWaywardStars.LOGGER.info("HUD elements registered successfully")
    }

    fun render(graphics: GuiGraphics, tickCounter: DeltaTracker) {
        val color = 0xffff00 // electric yellow
        val targetColor = 0xffff80 // pastel yellow

        // divide by 1000 to get seconds
        val currentTime: Double = Util.getMillis() / 1000.0

        val lerpedAmount = Mth.abs(Mth.sin(currentTime.toFloat().toDouble()))
        val lerpedColor = ARGB.linearLerp(lerpedAmount, ARGB.opaque(color), ARGB.opaque(targetColor))

        // funny testing :)
        val targetAmount = 61

        val lerpedCoins = Mth.lerpInt(lerpedAmount, GameManager.playerData.coins, targetAmount)

        val item = Items.EMERALD.defaultInstance
        item.set(DataComponents.CUSTOM_NAME, Component.literal("Coin"))

        graphics.renderFakeItem(item, 6, 6)
        graphics.drawString(
            Minecraft.getInstance().font, // font
            lerpedCoins.toString(), // text
            26, // x
            10, // y
            lerpedColor,
            true // shadow
        )
    }
}