package me.hapyl.mmo.serializable;

import java.lang.reflect.Field;

public class SerializeFieldData {

    private final Field field;
    private final String name;
    private final Object value;

    public SerializeFieldData(Field field, String name, Object value) {
        this.field = field;
        this.name = name;
        this.value = value;
    }

    public Field getField() {
        return field;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Serializable serializable, Object value) {
        final boolean access = field.canAccess(serializable);

        try {
            field.setAccessible(true);
            field.set(serializable, value);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            field.setAccessible(access);
        }
    }
}
