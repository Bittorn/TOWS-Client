package com.windtempos.tows.screens

import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.Tooltip
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import java.util.Objects

class ConfigScreen(val parent: Screen) : Screen(Component.literal("TOWS Config")) {

    lateinit var enabledButton: Button

    override fun onClose() {
        minecraft.setScreen(parent) // this might not work idk
    }

    override fun init() {
        Objects.requireNonNull(minecraft)
        enabledButton = Button.builder(
            Component.literal("Enable/Disable Mod")
        ) {
            println("Mod status toggled")
        }
            .bounds(width / 2 - 205, 20, 200, 20)
            .tooltip(Tooltip.create(Component.literal("Enable or disable mod functionality")))
            .build()

        addRenderableWidget(enabledButton)
    }

}