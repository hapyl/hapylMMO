package me.hapyl.mmo.player;

import com.google.common.collect.Maps;
import me.hapyl.mmo.Dependency;
import me.hapyl.mmo.MMO;
import me.hapyl.mmo.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

public class PlayerManager extends Dependency implements Listener {

    private final Map<Player, MMOPlayer> playerMap;

    public PlayerManager(Main plugin) {
        super(plugin);
        playerMap = Maps.newHashMap();

        registerListener(this);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void handlePlayerJoin(PlayerJoinEvent ev) {
        final Player player = ev.getPlayer();

        // load player
        getOrCreatePlayer(player);
    }

    @EventHandler()
    public void handlePlayerQuit(PlayerQuitEvent ev) {
        final Player player = ev.getPlayer();

        // unload player
        removePlayer(player);
    }

    @Nullable
    public MMOPlayer getPlayer(@Nonnull Player player) {
        return playerMap.get(player);
    }

    @Nonnull
    public MMOPlayer getOrCreatePlayer(@Nonnull Player player) {
        return playerMap.computeIfAbsent(player, fn -> new MMOPlayer(player, MMO.getPlayerDatabase(player)));
    }

    public boolean removePlayer(@Nonnull Player player) {
        final MMOPlayer mmoPlayer = playerMap.remove(player);

        if (mmoPlayer != null) {
            mmoPlayer.getDatabase().save();
            mmoPlayer.getInventory().save();
            return true;
        }

        return false;
    }

}
