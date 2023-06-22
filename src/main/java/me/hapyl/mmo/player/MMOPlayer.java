package me.hapyl.mmo.player;

import me.hapyl.mmo.database.PlayerDatabase;
import me.hapyl.mmo.player.inventory.MMOInventory;
import org.bukkit.entity.Player;

public class MMOPlayer {

    private final Player player;
    private final PlayerDatabase database;
    private final MMOInventory inventory;

    public MMOPlayer(Player player, PlayerDatabase database) {
        this.player = player;
        this.database = database;
        this.inventory = new MMOInventory(this);
    }

    public MMOInventory getInventory() {
        return inventory;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerDatabase getDatabase() {
        return database;
    }

    @Override
    public String toString() {
        return player.getName();
    }
}
