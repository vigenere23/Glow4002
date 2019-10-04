package ca.ulaval.glo4002.booking.domain.passOrdering.passes;

import java.util.Arrays;
import java.util.Optional;

public enum PassCategory {
    SUPERNOVA("supernova"),
    SUPERGIANT("supergiant"),
    NEBULA("nebula");

    private final String text;

    private PassCategory(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static PassCategory fromString(String text) {
        Optional<PassCategory> value = Arrays.stream(values())
          .filter(entry -> entry.text.equalsIgnoreCase(text))
          .findFirst();

        if (!value.isPresent()) {
            throw new IllegalArgumentException(
                String.format("No enum value for string %s", text)
            );
        }
        return value.get();
    }
}
