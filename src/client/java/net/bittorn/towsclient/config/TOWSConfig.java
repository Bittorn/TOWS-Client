package net.bittorn.towsclient.config;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import net.bittorn.towsclient.TOWSClient;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.io.IOUtils;

import java.io.*;

// Contains runtime representation of TOWS config, encoded using GSON
// Most, if not all, of this code is taken from CITResewn
// https://github.com/SHsuperCM/CITResewn/blob/main/src/main/java/shcm/shsupercm/fabric/citresewn/config/CITResewnConfig.java

public class TOWSConfig {

    // Whether the mod should be enabled or not
    public boolean enabled = true;

    // TOWSClient's config storage file
    private static final File FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "towsclient.json");

    // Active instance of the current config
    public static final TOWSConfig INSTANCE = read();

    // Reads the stored config
    private static TOWSConfig read() {
        if (!FILE.exists())
            return new TOWSConfig().write();

        Reader reader = null;
        try {
            return new Gson().fromJson(reader = new FileReader(FILE), TOWSConfig.class);
        } catch (Exception e) {
            e.printStackTrace(); // should be replaced with more robust logging
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    // Saves this config to file
    private TOWSConfig write() {
        Gson gson = new Gson();
        JsonWriter writer = null;
        try {
            writer = gson.newJsonWriter(new FileWriter(FILE));
            writer.setIndent("    ");

            gson.toJson(gson.toJsonTree(this, TOWSConfig.class), writer);
        } catch (Exception e) {
            e.printStackTrace(); // again, should be replaced with more robust logging
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(writer);
        }
        return this;
    }
}
