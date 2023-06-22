package me.hapyl.mmo.item.mmo;

import me.hapyl.mmo.item.Items;
import me.hapyl.mmo.serializable.SerializeData;
import me.hapyl.mmo.serializable.SerializeField;

import javax.annotation.Nonnull;
import java.util.UUID;

public class LevelableMMOItem extends MMOItem {

    @SerializeField private int level;

    public LevelableMMOItem(UUID uuid, Items item) {
        super(uuid, item);
        this.level = 1;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public void deserialize(@Nonnull SerializeData data) {
    }

    @Nonnull
    @Override
    public SerializeData serialize() {
        return new SerializeData(this);
    }

}
