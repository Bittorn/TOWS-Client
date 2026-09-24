package com.windtempos.tows.util

import com.windtempos.tows.data.PlayerData

object GameManager {
    var playerData: PlayerData = PlayerData()

    init {
        playerData = playerData.read() // this is stupid and I hate it >:(
    }
}