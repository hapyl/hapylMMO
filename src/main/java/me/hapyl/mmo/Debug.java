package me.hapyl.mmo;

import me.hapyl.spigotutils.module.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public final class Debug {

    public static final String PREFIX = "&4&lDEBUG &c—&7";

    public static void info(String info, Object... format) {
        log(ChatColor.GRAY, info, format);
    }

    public static void warn(String info, Object... format) {
        log(ChatColor.YELLOW, info, format);
    }

    public static void severe(String info, Object... format) {
        log(ChatColor.RED, info, format);
    }

    private static void log(ChatColor color, String info, Object... format) {
        final String toLog = Chat.format(PREFIX + " " + info.formatted(format));

        Chat.broadcastOp(toLog);
        Bukkit.getConsoleSender().sendMessage(toLog);
    }

}
