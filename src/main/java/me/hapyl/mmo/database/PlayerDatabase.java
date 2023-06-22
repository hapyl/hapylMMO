package me.hapyl.mmo.database;

import com.google.common.collect.Maps;
import com.mongodb.client.MongoCollection;
import me.hapyl.mmo.Debug;
import me.hapyl.mmo.MMO;
import me.hapyl.mmo.database.entry.DatabaseEntry;
import me.hapyl.mmo.database.entry.EntryType;
import me.hapyl.spigotutils.module.annotate.StaticShortcut;
import org.bson.Document;
import org.bukkit.Bukkit;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.UUID;

public class PlayerDatabase extends FilteredCollection {

    private final UUID uuid;
    private final Map<EntryType<?>, DatabaseEntry> entries;

    public PlayerDatabase(@Nonnull UUID uuid, @Nonnull Filter filter, @Nonnull MongoCollection<Document> collection) {
        super(filter, collection);
        this.uuid = uuid;
        this.entries = Maps.newHashMap();

        loadEntries();
        Debug.info("loaded database for " + Bukkit.getOfflinePlayer(uuid).getName());
    }

    public <T extends DatabaseEntry> T getEntry(@Nonnull EntryType<T> type) {
        final DatabaseEntry entry = entries.get(type);

        if (entry == null) {
            throw new IllegalArgumentException("entry %s does not exist!".formatted(type));
        }

        final Class<T> clazz = type.getClazz();

        if (!clazz.isInstance(entry)) {
            throw new IllegalArgumentException("entry %s is not a valid type for %s".formatted(entry.getClass().getSimpleName(), type));
        }

        return clazz.cast(entry);
    }

    @StaticShortcut
    public void save() {
        MMO.getDatabase().removePlayerDatabase(uuid);
    }

    private void loadEntries() {
        for (EntryType<?> value : EntryType.values()) {
            entries.put(value, value.create(this));
        }
    }

    public UUID getUUID() {
        return uuid;
    }
}
