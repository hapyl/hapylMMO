package me.hapyl.mmo.database.entry;

import me.hapyl.mmo.database.PlayerDatabase;
import me.hapyl.mmo.item.Items;
import me.hapyl.mmo.serializable.SerializeData;
import me.hapyl.mmo.serializable.SerializeField;
import me.hapyl.mmo.util.BiIterable;
import me.hapyl.spigotutils.module.util.Enums;
import me.hapyl.spigotutils.module.util.Validate;
import org.bson.Document;

import javax.annotation.Nonnull;
import java.util.UUID;
import java.util.function.BiConsumer;

/**
 * Stores all the player's inventory information.
 * <p>
 * Structure:
 * <pre>
 *     inventory: {
 *         generic: {
 *             ID: COUNT
 *         },
 *         unique: {
 *             UUID: {
 *                 ...data
 *             }
 *         }
 *     }
 * </pre>
 */
public class InventoryEntry extends DatabaseEntry {

    public InventoryEntry(@Nonnull PlayerDatabase database, @Nonnull String path) {
        super(database, path);
    }

    public GenericItems getGenericItems() {
        return new GenericItems(computeIfAbsent("generic"));
    }

    public UniqueItems getUniqueItems() {
        return new UniqueItems(computeIfAbsent("unique"));
    }

    public static class GenericItems extends DocumentWrapper implements BiIterable<Items, Long> {
        public GenericItems(Document document) {
            super(document);
        }

        public long getCount(Items item) {
            return get(item.getItem().getId(), 0L);
        }

        public void setCount(Items item, long count) {
            put(item.name(), count);
        }

        @Override
        public void iterate(@Nonnull BiConsumer<Items, Long> consumer) {
            forEach((id, value) -> {
                final Items item = Enums.byName(Items.class, id);
                final long count = Validate.getLong(value);

                if (item == null || count < 0) {
                    return;
                }

                consumer.accept(item, count);
            });
        }
    }

    public static class UniqueItems extends DocumentWrapper implements BiIterable<UUID, SerializeData> {
        public UniqueItems(Document document) {
            super(document);
        }

        public void setItem(UUID uuid, Document document) {
            put(uuid.toString(), document);
        }

        @Override
        public void iterate(@Nonnull BiConsumer<UUID, SerializeData> consumer) {
            forEach((uuid, value) -> {
                if (!(value instanceof Document document)) {
                    return;
                }

                final SerializeData data = SerializeData.fromDocument(document);
                consumer.accept(data.getUUID(), data);
            });
        }
    }

}
