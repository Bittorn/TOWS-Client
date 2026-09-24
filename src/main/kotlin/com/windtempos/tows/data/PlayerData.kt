@file:Suppress("unused")

package com.windtempos.tows.data

import com.windtempos.tows.TalesOfWaywardStars.LOGGER
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.Transient
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.decodeFromHexString
import kotlinx.serialization.encodeToHexString
import net.fabricmc.loader.api.FabricLoader
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import kotlin.math.max

@Serializable
class PlayerData {
    val schemaVersion = 1
    var coins = 50
        set(value) {
            field = max(value, 0)
            write()
        }

    val flags: MutableMap<String?, String?> = mutableMapOf()

    @Transient
    private val sessionFlags: MutableMap<String?, String?> = mutableMapOf()

    companion object {

        @Transient
        private val file = File(FabricLoader.getInstance().configDir.toFile(), "tales-of-wayward-stars.sav")

        @OptIn(ExperimentalSerializationApi::class)
        fun read(): PlayerData {
            LOGGER.info("Reading player data")
            if (!file.exists()) {
                LOGGER.warn("Unable to find player data at path {}, assuming it doesn't exist", file.path)
                write()
                return PlayerData()
            }

            try {
                FileReader(file).readLines()
            } catch (e: Exception) {
                LOGGER.error("Error reading player data from disk", e)
                throw RuntimeException(e)
            }

            try {
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
                        LOGGER.error("Error reading player data from disk", e)
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
                FileWriter(file).use { writer ->
                    writer.write(data)
                    LOGGER.info("Successfully wrote player data to disk")
                }
                file.writeText(data)
            } catch (e: Exception) {
                LOGGER.error("Error writing player data", e)
                throw RuntimeException(e)
            }
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