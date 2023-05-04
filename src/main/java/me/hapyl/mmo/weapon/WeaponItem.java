package me.hapyl.mmo.weapon;

import me.hapyl.mmo.item.Items;
import me.hapyl.mmo.item.LevelableItem;

import java.util.UUID;

/**
 * Represents an actual item in player inventory, in this case, a weapon.
 */
public class WeaponItem implements LevelableItem {

    private final Weapon weapon;
    private final UUID uuid;
    private int level;
    private Items levelMaterial;

    public WeaponItem(Weapon weapon) {
        this.weapon = weapon;
        this.uuid = UUID.randomUUID();
    }

    public Weapon getWeapon() {
        return weapon;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public int getCurrentLevel() {
        return level;
    }

    @Override
    public Items getLevelMaterial() {
        return levelMaterial;
    }

    public void setLevelMaterial(Items levelMaterial) {
        this.levelMaterial = levelMaterial;
    }
}
