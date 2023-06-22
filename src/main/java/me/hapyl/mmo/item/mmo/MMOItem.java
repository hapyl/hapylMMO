package me.hapyl.mmo.item.mmo;

import me.hapyl.mmo.Debug;
import me.hapyl.mmo.MMO;
import me.hapyl.mmo.item.Items;
import me.hapyl.mmo.serializable.Serializable;
import me.hapyl.mmo.serializable.SerializeData;
import me.hapyl.mmo.util.Debuggable;

import javax.annotation.Nonnull;
import java.util.UUID;

/**
 * Represents a unique item.
 */
public abstract class MMOItem implements Serializable, Debuggable {

    private final UUID uuid;
    private final Items item;

    public MMOItem(@Nonnull Items item) {
        this.item = item;
        this.uuid = MMO.randomUUID();
    }

    public MMOItem(@Nonnull UUID uuid, @Nonnull Items item) {
        this.uuid = uuid;
        this.item = item;
    }

    public final UUID getUUID() {
        return uuid;
    }

    public Items getItem() {
        return item;
    }

    @Override
    public void debug() {
        final SerializeData data = serialize();

        Debug.info(uuid.toString() + ":");

        data.forEach((key, value) -> {
            Debug.info(" %s = %s", key, value);
        });

        forEachSerializeField(field -> {
            Debug.info(" *%s = %s", field.getName(), field.getValue());
        });
    }

    @Override
    public String toString() {
        return uuid.toString() + "=" + item.name();
    }
}