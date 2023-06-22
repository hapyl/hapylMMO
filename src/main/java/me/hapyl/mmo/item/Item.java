package me.hapyl.mmo.item;

import org.bukkit.Material;

import javax.annotation.Nonnull;
import java.util.regex.Pattern;

/**
 * Represents a base item.
 * <p>
 * A little explanation of how items are implemented:
 * All items have enum ID associated with them, and that's how they are stored.
 */
public class Item extends PatternId {

    private final Material material;
    private final ItemData data;
    private Items handle;

    public Item(@Nonnull Material material) {
        super(Pattern.compile("^[A-Z0-9_]+$"));

        this.material = material;
        this.data = new ItemData(material);
    }

    public void setHandle(@Nonnull Items handle) throws IllegalArgumentException {
        if (this.handle != null) {
            throw new IllegalArgumentException("cannot reassign handle, already present: " + this.handle);
        }

        this.handle = handle;
        this.setId(handle);
    }

    @Nonnull
    public final Items getHandle() {
        return handle;
    }

    @Nonnull
    public Material getMaterial() {
        return material;
    }

    @Nonnull
    public ItemData getData() {
        return data;
    }

    public int getMaxStack() {
        return 1000;
    }

}
