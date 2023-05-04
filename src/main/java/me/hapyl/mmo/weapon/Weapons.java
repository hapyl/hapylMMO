package me.hapyl.mmo.weapon;

public enum Weapons {

    WOODEN_SWORD(new Weapon("Wooden Sword") {
        @Override
        public WeaponItem createItem() {
            return new WeaponItem(this);
        }
    }),
    ;

    private final Weapon weapon;

    Weapons(Weapon weapon) {
        this.weapon = weapon;
    }

    public Weapon getWeapon() {
        return weapon;
    }
}
