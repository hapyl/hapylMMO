package me.hapyl.mmo.database.entry;

import me.hapyl.mmo.database.PlayerDatabase;
import org.bson.Document;

import javax.annotation.Nonnull;
import java.util.function.Function;

public class DatabaseEntry {

    private final PlayerDatabase database;
    private final String path;

    protected Document document;

    public DatabaseEntry(@Nonnull PlayerDatabase database, @Nonnull String path) {
        this.database = database;
        this.path = path;

        document = database.getDocument().get(path, new Document());

        if (document == null) {
            database.getDocument().put(path, document = new Document());
        }
    }

    public PlayerDatabase getDatabase() {
        return database;
    }

    public String getPath() {
        return path;
    }

    public Document getDocument() {
        return document;
    }

    @Nonnull
    public final <T extends Document> T computeIfAbsent(String name, Function<String, T> function) {
        final T doc = document.get(name, function.apply(name));

        document.putIfAbsent(name, doc);

        return doc;
    }

    @Nonnull
    public final Document computeIfAbsent(String name) {
        return computeIfAbsent(name, fn -> new Document());
    }
}
