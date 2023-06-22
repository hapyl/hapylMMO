package me.hapyl.mmo.database;

import com.mongodb.client.MongoCollection;
import me.hapyl.mmo.MMO;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

public class FilteredCollection {

    private final Filter filter;
    private final MongoCollection<Document> collection;

    protected Document document;

    public FilteredCollection(Filter filter, MongoCollection<Document> collection) {
        this.filter = filter;
        this.collection = collection;

        this.document = collection.find(filter).first();

        if (this.document == null) {
            collection.insertOne(this.document = new Document(filter));
        }
    }

    public void update() {
        collection.replaceOne(filter, document);
    }

    public void update(@Nonnull Bson update) {
        document = collection.findOneAndUpdate(filter, update);
    }

    public void updateAsync() {
        new BukkitRunnable() {
            @Override
            public void run() {
                collection.replaceOne(filter, document);
            }
        }.runTaskAsynchronously(MMO.getPlugin());
    }

    public void updateAsync(@Nonnull Bson update) {
        updateAsync(update, null);
    }

    public void updateAsync(@Nonnull Bson update, @Nullable Consumer<FilteredCollection> andThen) {
        new BukkitRunnable() {
            @Override
            public void run() {
                collection.updateOne(filter, update);

                synchronized (collection) {
                    document = collection.find(filter).first();

                    if (andThen != null) {
                        andThen.accept(FilteredCollection.this);
                    }
                }
            }
        }.runTaskAsynchronously(MMO.getPlugin());
    }

    @Nonnull
    public Document getDocument() {
        return document;
    }

    @Nonnull
    public Filter getFilter() {
        return filter;
    }

    @Nonnull
    public MongoCollection<Document> getCollection() {
        return collection;
    }

}
