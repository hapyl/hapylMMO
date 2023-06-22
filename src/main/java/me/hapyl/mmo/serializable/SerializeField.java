package me.hapyl.mmo.serializable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Fields that are annotated with this annotation are automatically read and written upon serialization.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SerializeField {

    /**
     * Indicates the path that this field should be written/read from a root.
     * Default to the field name if not empty.
     *
     * @return the path to write/read this field to, keep empty to use fields name.
     */
    String path() default "";

}
