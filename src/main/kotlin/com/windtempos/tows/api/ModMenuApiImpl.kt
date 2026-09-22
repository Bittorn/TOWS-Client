package com.windtempos.tows.api

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import com.windtempos.tows.config.TOWSConfig

class ModMenuApiImpl: ModMenuApi {
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        return ConfigScreenFactory(TOWSConfig()::getConfigScreen)
    }
}