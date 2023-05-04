package me.hapyl.mmo.item;

/**
 * Represents an item, that can be leveled up.
 */
public interface LevelableItem extends UniqueItem {

    int getCurrentLevel();

    Items getLevelMaterial();


}
