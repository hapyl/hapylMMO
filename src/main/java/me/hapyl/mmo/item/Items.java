package me.hapyl.mmo.item;

import me.hapyl.mmo.item.mmo.MMOItem;
import org.bukkit.Material;

import javax.annotation.Nonnull;

/**
 * A collection of all items that can exist.
 * <p>
 * A note about items, the classes might be a little confusing, but here's the summary:
 *
 * <ul>
 *     <li>
 *         Classes that inherit from {@link Item} are blueprints for an item. All classes must have <code>Item</code> suffix.
 *     </li>
 *     <li>
 *         Classes that inherit from {@link MMOItem} are MMO items, which represent an actual item in player's inventory. All classes must have <code>MMOItem</code> suffix.
 *     </li>
 *     <ul>
 *         <b>Generic VS Non-Generic</b>
 *         <li>Generic items are items that only have a count, that's all.</li>
 *         <li>Non-Generic items have a leveling system and other stuff.</li>
 *     </ul>
 * </ul>
 * <p>
 * Warning from Jun 21 2023: This class will probably become a mess after 100 of custom items 🙂.
 */
public enum Items {

    POPPY(new Item(Material.POPPY)),

    WOODEN_SWORD(new WeaponItem(Material.WOODEN_SWORD)),

    ;

    private final Item item;

    Items(@Nonnull Item item) {
        this.item = item;
        this.item.setHandle(this);
    }

    public boolean isGeneric() {
        return !(item instanceof LevelableItem);
    }

    public <T extends Item> T getItem(@Nonnull Class<T> clazz) {
        if (clazz.isInstance(item)) {
            return clazz.cast(item);
        }

        throw new IllegalArgumentException("cannot cast %s to %s".formatted(item.getId(), clazz.getSimpleName()));
    }

    public Item getItem() {
        return item;
    }

}
