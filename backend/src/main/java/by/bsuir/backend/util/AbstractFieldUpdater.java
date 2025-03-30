package by.bsuir.backend.util;

import java.util.function.Consumer;

public class AbstractFieldUpdater {
    protected <T> void updateField(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
