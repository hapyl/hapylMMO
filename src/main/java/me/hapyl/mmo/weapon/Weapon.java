package me.hapyl.mmo.weapon;

public abstract class Weapon {

    private final String name;

    public Weapon(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract WeaponItem createItem();
}
