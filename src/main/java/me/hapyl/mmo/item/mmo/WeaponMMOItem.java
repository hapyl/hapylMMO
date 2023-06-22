package me.hapyl.mmo.item.mmo;

import me.hapyl.mmo.item.Items;
import me.hapyl.mmo.item.mmo.LevelableMMOItem;
import me.hapyl.mmo.serializable.Serializable;

import java.util.UUID;

/**
 * Represents an actual item in player inventory, in this case, a weapon.
 */
public class WeaponMMOItem extends LevelableMMOItem implements Serializable {

    public WeaponMMOItem(UUID uuid, Items item) {
        super(uuid, item);
    }
}

