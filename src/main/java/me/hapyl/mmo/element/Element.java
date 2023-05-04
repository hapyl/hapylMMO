package me.hapyl.mmo.element;

public enum Element {

    PHYSICAL("Physical"),
    FIRE("Fire"),
    WATER("Water"),
    EARTH("Earth"),
    WIND("Wind"),

    ;

    private final String name;
    private final ElementRelationship elementRelationship;

    Element(String name) {
        this.name = name;
        this.elementRelationship = new ElementRelationship(this);
    }

    public String getName() {
        return name;
    }

    public ElementRelationship getElementRelationship() {
        return elementRelationship;
    }
}
