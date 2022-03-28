package dev.mrsterner.bewitchmentplus.common.interfaces;

import java.util.Optional;

public interface Magical {
    static Optional<Magical> of(Object context) {
        if (context instanceof Magical) {
            return Optional.of(((Magical) context));
        }
        return Optional.empty();
    }

    void setMagical(boolean magical);

    boolean hasMagical();

}

