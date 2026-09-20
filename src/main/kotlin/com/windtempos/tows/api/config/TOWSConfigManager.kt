package com.windtempos.tows.api.config

import com.terraformersmc.modmenu.ModMenu
import net.fabricmc.loader.api.FabricLoader
import java.nio.file.Path

class TOWSConfigManager {
    var path: Path = FabricLoader.getInstance().configDir.resolve(ModMenu.MOD_ID + ".json")

    // TODO
}