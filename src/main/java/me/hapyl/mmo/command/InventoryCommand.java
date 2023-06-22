package me.hapyl.mmo.command;

import me.hapyl.mmo.MMO;
import me.hapyl.mmo.item.Items;
import me.hapyl.mmo.player.MMOPlayer;
import me.hapyl.mmo.player.inventory.MMOInventory;
import me.hapyl.spigotutils.module.chat.Chat;
import me.hapyl.spigotutils.module.command.SimplePlayerAdminCommand;
import org.bukkit.entity.Player;

public class InventoryCommand extends SimplePlayerAdminCommand {
    public InventoryCommand(String name) {
        super(name);

        addCompleterValues(1, "load", "save", "debug", "add", "remove", "clear");
        addCompleterValues(2, Items.values());
    }

    @Override
    protected void execute(Player player, String[] args) {
        // inventory (load, save, debug)
        // inventory (add, remove) (item)

        if (args.length == 0) {
            Chat.sendMessage(player, "&cInvalid us!");
            return;
        }

        final MMOPlayer mmoPlayer = MMO.getPlayer(player);
        final MMOInventory inventory = mmoPlayer.getInventory();
        final Items item = getArgument(args, 1).toEnum(Items.class);

        switch (getArgument(args, 0).toString().toLowerCase()) {
            case "load" -> {
                inventory.load();
            }
            case "save" -> {
                inventory.save();
            }

            case "debug" -> {
                inventory.debug();
            }

            case "add" -> {
                if (item == null) {
                    Chat.sendMessage(player, "&cInvalid item: " + args[1]);
                    return;
                }

                inventory.addItem(item, 1);
            }

            case "remove" -> {
                if (item == null) {
                    Chat.sendMessage(player, "&cInvalid item: " + args[1]);
                    return;
                }

                inventory.removeItem(item, 1);
            }
        }

    }
}
