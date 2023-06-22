package me.hapyl.mmo.serializable;

import me.hapyl.mmo.item.Items;
import me.hapyl.mmo.item.mmo.MMOItem;
import me.hapyl.spigotutils.module.util.Enums;
import org.bson.Document;

import javax.annotation.Nonnull;
import java.util.UUID;

public class SerializeData extends Document {

    public SerializeData(MMOItem mmoItem) {
        this(mmoItem.getUUID(), mmoItem.getItem());
    }

    public SerializeData(@Nonnull UUID uuid, @Nonnull Items item) {
        put("uuid", uuid.toString());
        put("item", item.name().toUpperCase());
    }

    @Nonnull
    public final UUID getUUID() {
        return UUID.fromString(get("uuid", ""));
    }

    public final Items getItem() {
        return Enums.byName(Items.class, get("item", ""));
    }

    @Nonnull
    public static SerializeData fromDocument(@Nonnull Document document) {
        // parse uuid and item statically to validate them
        final String uuidString = document.get("uuid", "");
        final String itemString = document.get("item", "");

        final UUID uuid;
        final Items item = Enums.byName(Items.class, itemString);

        try {
            uuid = UUID.fromString(uuidString);
        } catch (Exception e) {
            throw new IllegalArgumentException("invalid uuid: " + uuidString);
        }

        if (item == null) {
            throw new IllegalArgumentException("invalid item: " + itemString);
        }

        final SerializeData data = new SerializeData(uuid, item);

        document.forEach((key, value) -> {
            if (key.equals("uuid") || key.equals("item")) {
                return;
            }

            data.put(key, value);
        });

        return data;
    }

}
