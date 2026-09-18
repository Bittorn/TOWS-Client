package com.windtempos.tows

import net.fabricmc.api.ClientModInitializer
import net.minecraft.resources.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object TalesOfWaywardStars : ClientModInitializer {
	const val MOD_ID: String = "tales-of-wayward-stars"

	private val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)

    override fun onInitializeClient() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!")
	}

	fun id(path: String): Identifier
		= Identifier.fromNamespaceAndPath(MOD_ID, path)
}
