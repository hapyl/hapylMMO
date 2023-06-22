package me.hapyl.mmo.event;

import me.hapyl.mmo.Main;
import me.hapyl.spigotutils.module.util.DependencyInjector;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerEventHandler extends DependencyInjector<Main> implements Listener {
    public PlayerEventHandler(Main plugin) {
        super(plugin);

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }


}
