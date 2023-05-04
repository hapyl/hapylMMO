package me.hapyl.mmo;

import me.hapyl.mmo.database.Database;
import me.hapyl.spigotutils.EternaAPI;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main instance;

    private Database database;

    @Override
    public void onEnable() {
        instance = this;

        new EternaAPI(this);

        // Initiate
        database = new Database(this);
    }

    @Override
    public void onDisable() {
        database.saveAll();
        database.stopConnection();
    }
}
