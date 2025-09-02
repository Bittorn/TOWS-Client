package net.bittorn.towsclient.data.player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import net.bittorn.towsclient.TOWSClient;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("unused")
public class PlayerData {
    private final String version = "v0.0.2-alpha";
    private int coins = 50;

    @SuppressWarnings("FieldMayBeFinal")
    private Map<String, String> flags = Map.of();
    @SuppressWarnings("FieldMayBeFinal")
    private transient Map<String, String> sessionFlags = Map.of();

    // TODO: move storage directory
    private static final File FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "towsclient.player.json");

    public static PlayerData read() {
        if (!FILE.exists()) {
            TOWSClient.LOGGER.warn("Unable to find player data at path {}, assuming it doesn't exist", FILE.getPath());
            return new PlayerData().write();
        }

        try (Reader reader = new FileReader(FILE, StandardCharsets.UTF_8)) {
            TOWSClient.LOGGER.info("Successfully loaded player data from disk");
            return new Gson().fromJson(reader, PlayerData.class);
        } catch (Exception e) {
            TOWSClient.LOGGER.error("Error loading player data from disk", e);
            throw new RuntimeException(e);
        }
    }

    public PlayerData write() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (JsonWriter writer = gson.newJsonWriter(new FileWriter(FILE))) {
            gson.toJson(gson.toJsonTree(this, PlayerData.class), writer);
            TOWSClient.LOGGER.info("Successfully wrote player data to disk");
        } catch (Exception e) {
            TOWSClient.LOGGER.error("Error writing player data to disk", e);
            throw new RuntimeException(e);
        }
        return this;
    }

    //region Data retrieval

    public Map<String, String> getFlags() {
        return flags;
    }

    // TODO: simplify flag and session flag code by getting rid of basically-duplicate methods

    public void setFlag(String flag) {
        flags.put(flag, null);
        write();
    }

    public void setFlag(String flag, String value) {
        flags.put(flag, value);
        write();
    }

    public void setSessionFlag(String flag) {
        sessionFlags.put(flag, null);
        write();
    }

    public void setSessionFlag(String flag, String value) {
        sessionFlags.put(flag, value);
        write();
    }

    public boolean containsFlag(String flag) {
        return flags.containsKey(flag);
    }

    public boolean containsSessionFlag(String flag) {
        return sessionFlags.containsKey(flag);
    }

    public Optional<String> getFlagOrEmpty(String flag) {
        return Optional.ofNullable(flags.get(flag));
    }

    public Optional<String> getSessionFlagOrEmpty(String flag) {
        return Optional.ofNullable(sessionFlags.get(flag));
    }

    public String getVersion() {
        return version;
    }

    public int getCoins() {
        return coins;
    }

    public void addCoins(int coinsToAdd) {
        coins += coinsToAdd;
        write();
    }

    public void removeCoins(int coinsToRemove) {
        coins = Math.min(coins - coinsToRemove, 0);
        write();
    }

    //endregion
}
