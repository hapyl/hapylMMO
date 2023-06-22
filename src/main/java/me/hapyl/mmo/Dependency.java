package me.hapyl.mmo;

import me.hapyl.spigotutils.module.util.DependencyInjector;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import javax.annotation.Nonnull;
import java.util.logging.Logger;

public class Dependency extends DependencyInjector<Main> {

    protected final Logger logger;

    public Dependency(Main plugin) {
        super(plugin);

        logger = plugin.getLogger();
    }

    protected void registerListener(@Nonnull Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, getPlugin());
    }

    protected void disablePlugin(String reason) throws RuntimeException {
        final Main plugin = getPlugin();

        logger.severe(reason);
        Bukkit.getPluginManager().disablePlugin(plugin);

        throw new RuntimeException(reason);
    }

}
