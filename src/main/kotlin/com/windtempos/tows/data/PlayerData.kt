@file:Suppress("unused")

package com.windtempos.tows.data

import com.windtempos.tows.TalesOfWaywardStars.LOGGER
import com.windtempos.tows.config.TOWSConfig
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.Transient
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.decodeFromHexString
import kotlinx.serialization.encodeToHexString
import net.fabricmc.loader.api.FabricLoader
import java.io.File
import java.io.IOException

@Serializable
data class PlayerData(
    var schemaVersion: Int = 1, var coins: Int = 50, var flags: MutableMap<String?, String?> = mutableMapOf()
) {
    @Transient
    private val sessionFlags: MutableMap<String?, String?> = mutableMapOf()

    @Transient
    private val file = File(FabricLoader.getInstance().configDir.toFile(), "tales-of-wayward-stars.sav")

    @OptIn(ExperimentalSerializationApi::class)
    fun read(): PlayerData {
        LOGGER.info("Reading player data")
        if (TOWSConfig.HANDLER.instance().ignoreSave) {
            LOGGER.info("Ignoring save player data")
            return PlayerData()
        }
        if (!file.exists()) {
            LOGGER.warn("Unable to find player data at path {}, assuming it doesn't exist", file.path)
            write()
            return read()
        } else {
            LOGGER.info("Player data found at {}", file.path)
        }

        try {
            LOGGER.info("Decoding player data to object")
            val playerData: PlayerData = Cbor.decodeFromHexString(file.readText())
            LOGGER.info("Successfully read player data from disk")
            return playerData
        } catch (e: Exception) {
            when (e) {
                is SerializationException, is IllegalArgumentException -> {
                    LOGGER.error("Player data is malformed", e)
                }

                is IOException -> {
                    LOGGER.error("Player data could not be read", e)
                }

                else -> {
                    LOGGER.error("Error parsing player data", e)
                }
            }
            throw e
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun write() {
        LOGGER.info("Writing player data")
        val data: String

        try {
            data = Cbor.encodeToHexString(this)
        } catch (e: Exception) {
            LOGGER.error("Error encoding player data", e)
            throw e
        }

        try {
            file.writeText(data)
            LOGGER.info("Successfully wrote player data to disk")
        } catch (e: Exception) {
            LOGGER.error("Error writing player data", e)
            throw RuntimeException(e)
        }
    }

    //region TODO: refactor to Kotlin standards

    fun setFlag(flag: String) {
        flags[flag] = null
        write()
    }

    fun setFlag(flag: String, value: String) {
        flags[flag] = value
        write()
    }

    fun setSessionFlag(flag: String) {
        sessionFlags[flag] = null
        write()
    }

    fun setSessionFlag(flag: String, value: String) {
        sessionFlags[flag] = value
        write()
    }

    fun hasFlag(flag: String): Boolean {
        return flags.containsKey(flag) || sessionFlags.containsKey(flag)
    }

    fun getFlag(flag: String): String? {
        return flags[flag]
    }

    fun getSessionFlag(flag: String): String? {
        return sessionFlags[flag]
    }

    //endregion
}