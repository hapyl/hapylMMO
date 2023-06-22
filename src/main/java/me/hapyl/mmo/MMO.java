package me.hapyl.mmo;

import me.hapyl.mmo.database.Database;
import me.hapyl.mmo.database.PlayerDatabase;
import me.hapyl.mmo.item.mmo.MMOItem;
import me.hapyl.mmo.player.MMOPlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.UUID;
import java.util.function.Function;

/**
 * MMO core class for a plugin singleton.
 */
public final class MMO {

    static Main main;

    public static Database getDatabase() {
        return main.database;
    }

    public static PlayerDatabase getPlayerDatabase(@Nonnull UUID uuid) {
        return main.database.getPlayerDatabase(uuid);
    }

    public static PlayerDatabase getPlayerDatabase(@Nonnull Player player) {
        return getPlayerDatabase(player.getUniqueId());
    }

    public static <T extends MMOItem> T createItem(@Nonnull Function<UUID, T> function) {
        return getDatabase().getItemDatabase().createItem(function);
    }

    @Nonnull
    public static UUID randomUUID() {
        return getDatabase().getItemDatabase().randomUUID();
    }

    public static UUID createUUID() {
        return getDatabase().getItemDatabase().createUUID();
    }

    @Nonnull
    public static MMOPlayer getPlayer(Player player) {
        return main.playerManager.getOrCreatePlayer(player);
    }

    public static Main getPlugin() {
        return main;
    }
}
