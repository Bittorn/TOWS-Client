package com.windtempos.tows

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.windtempos.tows.util.KeybindManager
import net.fabricmc.api.ClientModInitializer
import net.minecraft.resources.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object TalesOfWaywardStars : ClientModInitializer {

	@JvmField
	var enabled: Boolean = true

	const val MOD_ID: String = "tales-of-wayward-stars"
	val builder: GsonBuilder =
		GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
	val GSON: Gson = builder.setPrettyPrinting().create()
	val GSON_MINIFIED: Gson = builder.create()

	@JvmField
	var LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)

    override fun onInitializeClient() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Initializing client")
		KeybindManager.register()
		LOGGER.info("Client initialized successfully")
	}

	@Suppress("unused")
	fun id(path: String): Identifier
		= Identifier.fromNamespaceAndPath(MOD_ID, path)
}
