package me.hapyl.mmo.item;

import java.util.UUID;

/**
 * Represents a unique item.
 */
public interface UniqueItem extends Item {

    UUID getUuid();

    @Override
    default int getMaxStack() {
        return 1;
    }
}
