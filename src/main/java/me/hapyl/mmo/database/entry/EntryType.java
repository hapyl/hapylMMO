package me.hapyl.mmo.database.entry;

import com.google.common.collect.Lists;
import me.hapyl.mmo.database.PlayerDatabase;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.util.List;

public class EntryType<T extends DatabaseEntry> {

    private static final List<EntryType<?>> values = Lists.newArrayList();

    public static final EntryType<InventoryEntry> INVENTORY = of("inventory", InventoryEntry.class);

    private final String name;
    private final Class<T> clazz;

    private EntryType(@Nonnull String name, @Nonnull Class<T> clazz) {
        this.name = name;
        this.clazz = clazz;

        values.add(this);
    }

    public String getName() {
        return name;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    @Nonnull
    public T create(@Nonnull PlayerDatabase playerDatabase) {
        try {
            final Constructor<T> constructor = clazz.getConstructor(PlayerDatabase.class, String.class);
            return constructor.newInstance(playerDatabase, name);
        } catch (Exception e) {
            throw new IllegalArgumentException("cannot create entry for %s because %s".formatted(
                    getClass().getSimpleName(),
                    e.getMessage()
            ));
        }
    }

    /**
     * Returns a copy of registered values.
     *
     * @return a copy of registered values.
     */
    public static List<EntryType<?>> values() {
        return Lists.newArrayList(values);
    }

    private static <T extends DatabaseEntry> EntryType<T> of(String name, Class<T> clazz) {
        return new EntryType<>(name, clazz);

    }
}
