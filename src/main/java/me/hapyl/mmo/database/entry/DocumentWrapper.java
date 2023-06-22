package me.hapyl.mmo.database.entry;

import org.bson.Document;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.BiConsumer;

public class DocumentWrapper {

    public final Document wrapped;

    public DocumentWrapper(Document document) {
        this.wrapped = document;
    }

    public <T> T get(@Nonnull Object key, @Nullable T def) {
        return wrapped.get(key, def);
    }

    public <T> void put(@Nonnull String key, @Nonnull T value) {
        wrapped.put(key, value);
    }

    public void forEach(@Nonnull BiConsumer<String, Object> consumer) {
        wrapped.forEach(consumer);
    }

}
