package com.windtempos.tows

import com.windtempos.tows.config.TOWSConfig
import com.windtempos.tows.hud.HudRenderer
import com.windtempos.tows.render.NPCRenderer
import com.windtempos.tows.util.KeybindManager
import net.fabricmc.api.ClientModInitializer
import net.minecraft.resources.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object TalesOfWaywardStars : ClientModInitializer {

	@JvmField
	var enabled: Boolean = true

	const val MOD_ID: String = "tales-of-wayward-stars"

	@JvmField
	var LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)

    override fun onInitializeClient() {
		// some things (like resources) may still be uninitialized

		LOGGER.info("Initializing client")
		LOGGER.info("Loading configuration")
		TOWSConfig.HANDLER.load()
		LOGGER.info("Loaded configuration")
		KeybindManager.register()
		HudRenderer.register()
		NPCRenderer.register()
		LOGGER.info("Client initialized successfully")
	}

	@Suppress("unused")
	fun id(path: String): Identifier
		= Identifier.fromNamespaceAndPath(MOD_ID, path)
}
