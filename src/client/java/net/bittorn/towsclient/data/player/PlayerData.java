package net.bittorn.towsclient.data.player;

import java.util.Map;
import java.util.Optional;

@SuppressWarnings("unused")
public class PlayerData {
    private Map<String, String> playerFlags;
    private int coins;

    private PlayerData() {
        // no-op constructor
    }

    public Map<String, String> getFlags() {
        return playerFlags;
    }

    public void setFlag(String flag) {
        playerFlags.put(flag, null);
    }

    public void setFlag(String flag, String value) {
        playerFlags.put(flag, value);
    }

    public boolean containsFlag(String flag) {
        return playerFlags.containsKey(flag);
    }

    public Optional<String> getFlagOrEmpty(String flag) {
        return Optional.ofNullable(playerFlags.get(flag));
    }

    public int getCoins() {
        return coins;
    }

    public void addCoins(int coinsToAdd) {
        coins += coinsToAdd;
    }

    public void removeCoins(int coinsToRemove) {
        coins = Math.min(coins - coinsToRemove, 0);
    }
}
