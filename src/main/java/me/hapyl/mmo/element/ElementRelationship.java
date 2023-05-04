package me.hapyl.mmo.element;

import com.google.common.collect.Maps;

import java.util.Map;

public class ElementRelationship {

    private final Element self;
    private final Map<Element, Float> relationship;

    public ElementRelationship(Element self) {
        this.self = self;
        this.relationship = Maps.newHashMap();
    }

    public void setRelationship(Element element, float value) {
        if (element == self) {
            value = 0.0f;
        }

        relationship.put(element, value);
    }
}
