package me.hapyl.mmo;

import me.hapyl.spigotutils.module.chat.Chat;
import me.hapyl.spigotutils.module.chat.Gradient;
import me.hapyl.spigotutils.module.chat.gradient.Interpolators;
import me.hapyl.spigotutils.module.util.Placeholder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;

public final class Message {

    public static void success(@Nonnull CommandSender sender, @Nonnull String message, @Nullable Object... format) {
        send(sender, Type.SUCCESS, message, format);
    }

    public static void info(@Nonnull CommandSender sender, @Nonnull String message, @Nullable Object... format) {
        send(sender, Type.INFO, message, format);
    }

    public static void question(@Nonnull CommandSender sender, @Nonnull String message, @Nullable Object... format) {
        send(sender, Type.QUESTION, message, format);
    }

    public static void error(@Nonnull CommandSender sender, @Nonnull String message, @Nullable Object... format) {
        send(sender, Type.ERROR, message, format);
    }

    public static void send(@Nonnull CommandSender sender, @Nonnull Type type, @Nonnull String message, @Nullable Object... format) {
        sender.sendMessage(type.toString(Placeholder.format(message, format)));
    }

    public static void nl(@Nonnull CommandSender sender) {
        sender.sendMessage("");
    }

    public enum Type {
        SUCCESS("&a&l✔", new Color(12, 156, 24), new Color(35, 194, 48)),
        ERROR(ChatColor.of("#c91010") + "&l❌ ", new Color(235, 16, 16), new Color(232, 23, 23)),
        QUESTION("&e&l❓ ", new Color(230, 190, 73), new Color(237, 200, 92)),
        INFO("&f&l¡ ", new Color(161, 160, 159), new Color(207, 207, 207)),

        ;

        private final String prefix;
        private final Color from;
        private final Color to;

        Type(String prefix, Color from, Color to) {
            this.prefix = prefix;
            this.from = from;
            this.to = to;
        }

        public String toString(String input) {
            return Chat.format(prefix + " " + new Gradient(input).rgb(from, to, Interpolators.LINEAR));
        }

        public String toStringRaw(String input) {
            return Chat.format(prefix + " " + input);
        }
    }
}
