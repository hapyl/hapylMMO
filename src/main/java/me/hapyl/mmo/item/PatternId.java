package me.hapyl.mmo.item;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.regex.Pattern;

public class PatternId {

    private final Pattern pattern;
    private String id;

    public PatternId(@Nonnull Pattern pattern) {
        this.pattern = pattern;
    }

    @Nonnull
    public final String getId() {
        if (id == null) {
            throw new IllegalStateException("id not set");
        }

        return id;
    }

    public PatternId setId(@Nonnull Enum<?> anEnum) {
        return setId(anEnum.name().toUpperCase());
    }

    public PatternId setId(@Nonnull String id) {
        if (!pattern.matcher(id).matches()) {
            throw new IllegalArgumentException("id %s does not match the pattern %s".formatted(id, pattern.toString()));
        }

        this.id = id;
        return this;
    }

    @Override
    public int hashCode() {
        if (id == null) {
            throw new IllegalArgumentException("id not set");
        }

        return Objects.hash(id);
    }
}
