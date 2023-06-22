package me.hapyl.mmo.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import me.hapyl.mmo.MMO;
import me.hapyl.mmo.Main;
import me.hapyl.mmo.util.MongoUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Represents a document element that can be read and written asynchronously.
 */
public class DatabaseElement {

    protected final MongoCollection<Document> collection;
    protected final Filter filter;

    protected Document document;

    public DatabaseElement(MongoCollection<Document> collection, Filter filter) {
        this.collection = collection;
        this.filter = filter;

        load();
    }

    public final void synchronize() {
        async(this::load);
    }

    /**
     * Reads a value from a given path.
     * Path may or may not be nested by using '.' (dot) as a separator.
     *
     * @param path - Path to read from.
     * @param def  - Default value.
     * @return an element or def.
     */
    public final <E> E read(@Nonnull String path, @Nullable E def) {
        return MongoUtils.get(document, path, def);
    }

    public final <E, V> V read(@Nonnull String path, @Nonnull E def, @Nonnull Function<E, V> function) {
        return function.apply(read(path, def));
    }

    /**
     * Writes a value to a given path.
     * Path may or may not be nested by using '.' (dot) as a separator.
     *
     * @param path  - Path to write to.
     * @param value - Value to write.
     */
    public final <E> void write(@Nonnull String path, @Nullable E value) {
        write(path, value, null);
    }

    /**
     * Writes a value to a given path.
     * Path may or may not be nested by using '.' (dot) as a separator.
     * <b>
     * Writing is done asynchronously.
     * </b>
     *
     * @param path    - Path to write to.
     * @param value   - Value to write.
     * @param andThen - Synchronized updated instance of this.
     */
    public final <E> void write(@Nonnull String path, @Nullable E value, @Nullable Consumer<DatabaseElement> andThen) {
        write(Updates.set(path, value), andThen);
    }

    /**
     * Writes an update.
     *
     * @param update - Update to write.
     */
    public final void write(@Nonnull Bson update) {
        write(update, null);
    }

    /**
     * Writes an ASYNC update.
     *
     * @param update  - Update.
     * @param andThen - Synced callback.
     */
    public final void write(@Nonnull Bson update, @Nullable Consumer<DatabaseElement> andThen) {
        try {
            async(() -> {
                collection.updateOne(filter, update);

                synchronized (collection) {
                    document = collection.find(filter).first();

                    if (andThen != null) {
                        andThen.accept(this);
                    }
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns keys of this document.
     *
     * @return keys of this document.
     */
    public final Set<String> keyset() {
        return document.keySet();
    }

    private void load() {
        this.document = collection.find(filter).first();

        if (document == null) {
            document = new Document(filter);
            collection.insertOne(document);
        }
    }

    private void async(Runnable runnable) {
        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskAsynchronously(MMO.getPlugin());
    }
}
