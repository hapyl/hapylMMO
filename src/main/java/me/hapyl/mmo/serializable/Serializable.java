package me.hapyl.mmo.serializable;

import me.hapyl.mmo.Debug;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.function.Consumer;

public interface Serializable {

    void deserialize(@Nonnull SerializeData data);

    default void deserialize0(@Nonnull SerializeData data) {
        deserialize(data);
        forEachSerializeField(field -> {
            final Object value = data.get(field.getName());

            Debug.info("field=%s, value=%s", field.getName(), value);

            if (value == null) {
                return;
            }

            field.setValue(this, value);
        });
    }

    @Nonnull
    SerializeData serialize();

    default SerializeData serialize0() {
        final SerializeData data = serialize();
        forEachSerializeField(field -> data.append(field.getName(), field.getValue()));

        return data;
    }

    default void forEachSerializeField(@Nonnull Consumer<SerializeFieldData> consumer) {
        final Class<? extends Serializable> clazz = this.getClass();

        forEachField(clazz.getFields(), consumer);
        forEachField(clazz.getDeclaredFields(), consumer);
    }

    default void forEachField(Field[] fields, Consumer<SerializeFieldData> consumer) {
        for (Field field : fields) {
            final SerializeField annotation = field.getAnnotation(SerializeField.class);

            if (annotation == null) {
                continue;
            }

            final boolean access = field.canAccess(this);
            field.setAccessible(true);

            try {
                consumer.accept(new SerializeFieldData(field, field.getName(), field.get(this)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } finally {
                field.setAccessible(access);
            }
        }
    }

}
