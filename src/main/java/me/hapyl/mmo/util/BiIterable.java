package me.hapyl.mmo.util;

import javax.annotation.Nonnull;
import java.util.function.BiConsumer;

public interface BiIterable<A, B> {

    void iterate(@Nonnull BiConsumer<A, B> consumer);

}
