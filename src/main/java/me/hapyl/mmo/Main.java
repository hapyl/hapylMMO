package me.hapyl.mmo;

import me.hapyl.mmo.command.CommandRegistry;
import me.hapyl.mmo.database.Database;
import me.hapyl.mmo.event.PlayerEventHandler;
import me.hapyl.mmo.player.PlayerManager;
import me.hapyl.spigotutils.EternaAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

public class Main extends JavaPlugin {

    protected Database database;
    protected PlayerManager playerManager;

    @Override
    public void onEnable() {
        MMO.main = this;

        new EternaAPI(this);

        // Load default config
        getConfig().options().copyDefaults(true);
        saveConfig();

        // Initiate
        database = new Database(this);
        playerManager = new PlayerManager(this);

        // Load event handlers
        new PlayerEventHandler(this);

        // Register commands
        new CommandRegistry(this);

        // Reload player data >> /reload
        Bukkit.getOnlinePlayers().forEach(MMO::getPlayer);
    }

    @Override
    public void onDisable() {
        database.saveAll();
        database.stopConnection();
    }

    @Nonnull
    public Database getDatabase() {
        return database;
    }

}
