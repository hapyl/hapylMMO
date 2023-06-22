package me.hapyl.mmo.command;

import com.google.common.collect.Sets;
import me.hapyl.mmo.Dependency;
import me.hapyl.mmo.Main;
import me.hapyl.mmo.Message;
import me.hapyl.spigotutils.module.command.CommandProcessor;
import me.hapyl.spigotutils.module.command.SimpleCommand;
import me.hapyl.spigotutils.module.command.SimplePlayerAdminCommand;
import me.hapyl.spigotutils.module.command.SimplePlayerCommand;
import me.hapyl.spigotutils.module.util.TypeConverter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

public class CommandRegistry extends Dependency {

    private final CommandProcessor processor;

    public CommandRegistry(Main plugin) {
        super(plugin);
        this.processor = new CommandProcessor(plugin);

        registerCommands();
    }

    private void registerCommands() {
        register(new InventoryCommand("inventory"));

        register(new SimplePlayerCommand("plugins") {
            @Override
            protected void execute(Player player, String[] args) {
                final Plugin[] plugins = Bukkit.getPluginManager().getPlugins();

                final List<Plugin> customPlugins = Arrays.stream(plugins)
                        .filter(plugin -> plugin.getDescription().getAuthors().contains("hapyl"))
                        .toList();

                final List<Plugin> publicPlugins = Arrays.stream(plugins)
                        .filter(plugin -> !plugin.getDescription().getAuthors().contains("hapyl"))
                        .toList();

                Message.info(player, "We're using a mix of custom-made and public plugins to make this project possible.");
                Message.nl(player);

                Message.success(player, "Custom Plugins");
                Message.info(player, listToString(customPlugins));
                Message.nl(player);

                Message.success(player, "Public Plugins");
                Message.info(player, listToString(publicPlugins));
            }

            private String listToString(List<Plugin> plugins) {
                final StringBuilder builder = new StringBuilder();

                for (int i = 0; i < plugins.size(); i++) {
                    if (i != 0) {
                        builder.append(", ");
                    }
                    builder.append(plugins.get(i).getDescription().getName());
                }

                return builder.toString();
            }

        });
    }

    private void register(SimpleCommand command) {
        processor.registerCommand(command);
    }

    private void register(String name, BiConsumer<Player, Arguments> bi) {
        processor.registerCommand(new SimplePlayerCommand(name) {
            @Override
            protected void execute(Player player, String[] args) {
                bi.accept(player, new Arguments(args));
            }
        });
    }

    private void registerAdminCommand(String name, BiConsumer<Player, Arguments> bi) {
        processor.registerCommand(new SimplePlayerAdminCommand(name) {
            @Override
            protected void execute(Player player, String[] args) {
                bi.accept(player, new Arguments(args));
            }
        });
    }

    private record Arguments(String[] array) {
        @Nonnull
        public TypeConverter get(int index) {
            return TypeConverter.from(index >= array.length ? "" : array[index]);
        }

    }
}
