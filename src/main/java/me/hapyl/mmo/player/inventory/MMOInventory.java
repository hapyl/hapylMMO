package me.hapyl.mmo.player.inventory;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mongodb.client.model.Updates;
import me.hapyl.mmo.Debug;
import me.hapyl.mmo.Message;
import me.hapyl.mmo.database.PlayerDatabase;
import me.hapyl.mmo.database.entry.EntryType;
import me.hapyl.mmo.database.entry.InventoryEntry;
import me.hapyl.mmo.item.Items;
import me.hapyl.mmo.item.LevelableItem;
import me.hapyl.mmo.item.mmo.LevelableMMOItem;
import me.hapyl.mmo.item.mmo.MMOItem;
import me.hapyl.mmo.player.MMOPlayer;
import me.hapyl.mmo.serializable.SerializeData;
import me.hapyl.mmo.util.Debuggable;
import me.hapyl.mmo.util.UnsignedLongMap;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class MMOInventory implements Debuggable {

    private final MMOPlayer player;
    private final UnsignedLongMap<Items> genericItems;
    private final Map<UUID, MMOItem> uniqueItems;

    public MMOInventory(MMOPlayer player) {
        this.player = player;
        this.genericItems = new UnsignedLongMap<>();
        this.uniqueItems = Maps.newHashMap();

        loadInventory();
    }

    public void load() {
        loadInventory();
    }

    public void save() {
        saveInventory();
    }

    public boolean contains(@Nonnull Items item) {
        return getCount(item) > 0;
    }

    public long getCount(@Nonnull Items item) {
        if (item.isGeneric()) {
            return genericItems.getOrDefault(item, 0L);
        }

        for (MMOItem mmoItem : uniqueItems.values()) {
            if (mmoItem.getItem() == item) {
                return 1;
            }
        }

        return 0;
    }

    public boolean addItem(@Nonnull Items item) {
        return addItem(item, 1);
    }

    public boolean addItem(@Nonnull Items item, long count) {
        if (item.isGeneric()) {
            if (!canFit(item, count)) {
                return false;
            }

            genericItems.increment(item, count);
            return true;
        }

        // TODO (hapyl): 021, Jun 21: Add type limit check.

        final LevelableItem levelableItem = item.getItem(LevelableItem.class);
        final LevelableMMOItem mmoItem = levelableItem.createItem();

        uniqueItems.put(mmoItem.getUUID(), mmoItem);
        return true;
    }

    public boolean removeItem(@Nonnull Items item) {
        return removeItem(item, 1);
    }

    public boolean removeItem(@Nonnull Items item, long count) {
        if (item.isGeneric()) {
            return genericItems.decrement(item, count) >= 0;
        }

        // Should probably not use this for unique items
        final Set<MMOItem> items = getMMOItems(item);
        final Iterator<MMOItem> iterator = items.iterator();

        for (int i = 0; i < count; i++) {
            final MMOItem next = iterator.next();
            if (next == null) {
                return true;
            }

            uniqueItems.remove(next.getUUID());
        }

        return false;
    }

    public boolean removeItem(@Nonnull MMOItem item) {
        return uniqueItems.remove(item.getUUID()) != null;
    }

    public boolean canFit(@Nonnull Items item, long count) {
        return canFit(item) >= count;
    }

    public long canFit(@Nonnull Items item) {
        return item.getItem().getMaxStack() - getCount(item);
    }

    public Set<MMOItem> getMMOItems() {
        return Sets.newHashSet(uniqueItems.values());
    }

    public Set<MMOItem> getMMOItems(@Nonnull Items item) {
        final Set<MMOItem> items = getMMOItems();
        items.removeIf(mmoItem -> mmoItem.getItem() != item);

        return items;
    }

    @Override
    public void debug() {
        Debug.info("INVENTORY DEBUG");
        Debug.info("owner=" + player.toString());
        Debug.info("genericItems=[");

        genericItems.forEach((item, count) -> Debug.info(" %s: %s", item.getItem().getId(), count));

        Debug.info("]");

        Debug.info("uniqueItems=[");
        uniqueItems.forEach((uuid, item) -> item.debug());
        Debug.info("]");
    }

    private void loadInventory() {
        final PlayerDatabase database = player.getDatabase();
        final InventoryEntry inventory = database.getEntry(EntryType.INVENTORY);

        // Load generics
        inventory.getGenericItems().iterate((key, value) -> {
            if (key == null || value == null) {
                return;
            }

            this.genericItems.put(key, value);
        });

        // Load uniques
        inventory.getUniqueItems().iterate((uuid, data) -> {
            final LevelableItem levelableItem = data.getItem().getItem(LevelableItem.class);
            final LevelableMMOItem mmoItem = levelableItem.createItem(uuid);

            mmoItem.deserialize0(data);

            this.uniqueItems.put(uuid, mmoItem);
        });

        Debug.info("Loaded inventory");
    }

    private void saveInventory() {
        final PlayerDatabase database = player.getDatabase();
        final InventoryEntry inventory = database.getEntry(EntryType.INVENTORY);

        // Save generics
        final InventoryEntry.GenericItems generic = inventory.getGenericItems();
        this.genericItems.forEach(generic::setCount);

        // Save uniques
        final InventoryEntry.UniqueItems unique = inventory.getUniqueItems();
        this.uniqueItems.forEach((uuid, item) -> {
            final SerializeData serialize = item.serialize0();

            unique.setItem(uuid, serialize);
        });

        database.updateAsync(Updates.set("inventory", inventory.getDocument()), then -> {
            // TODO (hapyl): 022, Jun 22: add send message methods to MMOPlayer
            Message.success(player.getPlayer(), "Saved");
        });
    }

}
