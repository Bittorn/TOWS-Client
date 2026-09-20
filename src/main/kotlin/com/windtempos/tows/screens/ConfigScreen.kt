package com.windtempos.tows.screens

import com.terraformersmc.modmenu.config.ModMenuConfig
import com.terraformersmc.modmenu.config.ModMenuConfigManager
import com.windtempos.tows.TalesOfWaywardStars
import net.minecraft.ChatFormatting
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.Button.OnPress
import net.minecraft.client.gui.components.StringWidget
import net.minecraft.client.gui.components.Tooltip
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import java.util.*

class ConfigScreen(val parent: Screen) : Screen(Component.literal("TOWS Config")) {

    lateinit var enabledButton: Button
    lateinit var titleLabel: StringWidget
    lateinit var subtitleLabel: StringWidget

    override fun onClose() {
        minecraft.setScreen(parent) // this might not work IDK
    }

    override fun init() {
        Objects.requireNonNull(minecraft)
        var enabledButtonLabel = Component.literal("Disable Mod")
        if (TalesOfWaywardStars.enabled) enabledButtonLabel = Component.literal("Enable Mod")
        val enabledButtonWidth = font.width(enabledButtonLabel) + 20
        val enabledButtonHeight = 20
        enabledButton = Button.builder(enabledButtonLabel) {
            println("Mod status toggled")
            TalesOfWaywardStars.enabled = !TalesOfWaywardStars.enabled
        }
            .bounds(
                width / 2 - enabledButtonWidth / 2,
                font.lineHeight * 2 + 20 + enabledButtonHeight / 2,
                enabledButtonWidth,
                enabledButtonHeight
            )
            .tooltip(Tooltip.create(Component.literal("Enable or disable mod functionality")))
            .build()

        val titleLabelText = Component.literal("Tales of Wayward Stars")
            .withStyle(ChatFormatting.BOLD)
            .withStyle(Style.EMPTY.withColor(0xf1e3a4))
        titleLabel = StringWidget(titleLabelText, font)
        titleLabel.setPosition(width / 2 - font.width(titleLabelText) / 2, 16)

        val subtitleLabelText = Component.literal("Configuration")
        subtitleLabel = StringWidget(subtitleLabelText, font)
        subtitleLabel.setPosition(width / 2 - font.width(subtitleLabelText) / 2, 20 + font.lineHeight)

        addRenderableWidget(titleLabel)
        addRenderableWidget(subtitleLabel)

        addRenderableWidget(enabledButton)

        addRenderableWidget(
            Button.builder(CommonComponents.GUI_DONE) { onClose() }
                .bounds(width / 2 - 50, height - 40, 100, 20)
                .build()
        )
    }

    override fun render(context: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(context, mouseX, mouseY, delta)
    }

}