package me.hapyl.mmo.item.ability;

import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public abstract class Ability {

    private final Type type;

    public Ability(@Nonnull Type type) {
        this.type = type;
    }

    public abstract Response execute(Player player);

    public Type getType() {
        return type;
    }
}
