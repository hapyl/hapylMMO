package me.hapyl.mmo.item;

import com.google.common.collect.Maps;
import me.hapyl.mmo.item.ability.Ability;
import me.hapyl.mmo.item.ability.Type;
import org.bukkit.Material;

import javax.annotation.Nonnull;
import java.util.Map;

public class AbilityItem extends Item {

    private final Map<Type, Ability> abilities;

    public AbilityItem(@Nonnull Material material) {
        super(material);

        abilities = Maps.newHashMap();
    }

    public AbilityItem setAbility(@Nonnull Ability ability) {
        abilities.put(ability.getType(), ability);
        return this;
    }

}
