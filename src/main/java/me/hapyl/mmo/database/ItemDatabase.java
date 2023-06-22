package me.hapyl.mmo.database;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mongodb.client.MongoCollection;
import me.hapyl.mmo.item.mmo.MMOItem;
import org.bson.Document;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

public class ItemDatabase extends DatabaseElement {

    private final String PATH_KNOWN_ITEMS = "known_items";

    public ItemDatabase(MongoCollection<Document> collection, Filter filter) {
        super(collection, filter);
    }

    @Nonnull
    public Set<UUID> getRegisteredItems() {
        final List<String> stringUUIDs = read(PATH_KNOWN_ITEMS, Lists.newArrayList());
        final Set<UUID> uuids = Sets.newHashSet();

        for (String stringUUID : stringUUIDs) {
            uuids.add(stringToUUID(stringUUID));
        }

        return uuids;
    }

    public <T extends MMOItem> T createItem(@Nonnull Function<UUID, T> function) {
        final UUID uuid = randomUUID();
        final T t = function.apply(uuid);

        addUUID(uuid);

        return t;
    }

    public void deleteItem(@Nonnull MMOItem item) {
        removeUUID(item.getUUID());
    }

    public boolean isUUIDExists(@Nonnull UUID uuid) {
        return getRegisteredItems().contains(uuid);
    }

    /**
     * Gets random non-taken UUID.
     */
    public UUID randomUUID() {
        final UUID uuid = UUID.randomUUID();

        return isUUIDExists(uuid) ? randomUUID() : uuid;
    }

    @Nonnull
    public UUID createUUID() {
        final UUID uuid = randomUUID();
        addUUID(uuid);

        return uuid;
    }

    private void addUUID(@Nonnull UUID uuid) {
        final List<String> uuids = read(PATH_KNOWN_ITEMS, Lists.newArrayList());
        uuids.add(uuid.toString());

        write(PATH_KNOWN_ITEMS, uuids);
    }

    private void removeUUID(@Nonnull UUID uuid) {
        final List<String> uuids = read(PATH_KNOWN_ITEMS, Lists.newArrayList());
        uuids.remove(uuid.toString());

        write(PATH_KNOWN_ITEMS, uuids);
    }

    private UUID stringToUUID(String string) {
        try {
            return UUID.fromString(string);
        } catch (Exception e) {
            return new UUID(0, 0);
        }
    }

}
