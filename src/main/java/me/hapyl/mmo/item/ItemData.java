package me.hapyl.mmo.item;

import me.hapyl.spigotutils.module.chat.Chat;
import org.bukkit.Material;

import javax.annotation.Nonnull;

public class ItemData {

    private String name;
    private String description;

    public ItemData() {
    }

    public ItemData(@Nonnull Material material) {
        if (!material.isItem()) {
            throw new IllegalArgumentException("material must be an item, %s is not!".formatted(material));
        }

        this.name = Chat.capitalize(material);
    }

    public ItemData setName(String name) {
        this.name = name;
        return this;
    }

    public ItemData setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
