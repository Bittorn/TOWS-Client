package net.bittorn.towsclient.data.player;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.cbor.databind.CBORMapper;
import io.github.novacrypto.base58.Base58;
import net.bittorn.towsclient.TOWSClient;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("unused")
public class PlayerData {
    @SuppressWarnings("FieldCanBeLocal")
    private final int schemaVersion = 1;
    private int coins = 50;

    @SuppressWarnings("FieldMayBeFinal")
    private Map<String, String> flags = Map.of();
    @SuppressWarnings("FieldMayBeFinal")
    private transient Map<String, String> sessionFlags = Map.of();

    // TODO: move storage directory

    private static final File FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "towsclient.sav");
    private static final CBORMapper cbor = new CBORMapper();

    public static PlayerData read() {
        if (!FILE.exists()) {
            TOWSClient.LOGGER.warn("Unable to find player data at path {}, assuming it doesn't exist", FILE.getPath());
            return new PlayerData().write();
        }

        byte[] data;

        try (FileReader reader = new FileReader(FILE)) {
            StringBuilder sb = new StringBuilder();
            int i;

            // Using read method
            while ((i = reader.read()) != -1) {
                sb.append((char)i);
            }

            data = Base58.base58Decode(sb.toString());
        } catch (Exception e) {
            TOWSClient.LOGGER.error("Error reading player data from disk", e);
            throw new RuntimeException(e);
        }

        try {
            PlayerData playerData = cbor.readValue(data, PlayerData.class);
            TOWSClient.LOGGER.info("Successfully loaded player data from disk");
            return playerData;
        } catch (Exception e) {
            TOWSClient.LOGGER.error("Error loading player data from disk", e);
            throw new RuntimeException(e);
        }
    }

    public PlayerData write() {
        String data;
        try {
            data = Base58.base58Encode(cbor.writeValueAsBytes(this));
        } catch (Exception e) {
            TOWSClient.LOGGER.error("Error encoding player data", e);
            throw new RuntimeException(e);
        }

        try (FileWriter writer = new FileWriter(FILE)) {
            writer.write(data);
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
        return flags.containsKey(flag) || sessionFlags.containsKey(flag);
    }

    public Optional<String> getFlagOrEmpty(String flag) {
        return Optional.ofNullable(flags.get(flag));
    }

    public Optional<String> getSessionFlagOrEmpty(String flag) {
        return Optional.ofNullable(sessionFlags.get(flag));
    }

    public int getSchemaVersion() {
        return schemaVersion;
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
