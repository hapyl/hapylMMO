package me.hapyl.mmo.item;

import me.hapyl.mmo.MMO;
import me.hapyl.mmo.item.mmo.LevelableMMOItem;
import org.bukkit.Material;

import javax.annotation.Nonnull;
import java.util.UUID;

// TODO (hapyl): 021, Jun 21: Simple leveling system for now
public abstract class LevelableItem extends Item {

    private Items levelMaterial;
    private int maxLevel;

    public LevelableItem(Material material) {
        super(material);

        this.maxLevel = 20;
        this.levelMaterial = Items.POPPY;
    }

    public Items getLevelMaterial() {
        return levelMaterial;
    }

    public void setLevelMaterial(@Nonnull Items levelMaterial) {
        this.levelMaterial = levelMaterial;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    @Nonnull
    public LevelableMMOItem createItem() {
        return MMO.createItem(uuid -> new LevelableMMOItem(uuid, getHandle()));
    }

    @Nonnull
    public LevelableMMOItem createItem(@Nonnull UUID uuid) {
        return new LevelableMMOItem(uuid, getHandle());
    }

}
