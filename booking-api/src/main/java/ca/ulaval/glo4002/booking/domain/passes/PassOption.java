package ca.ulaval.glo4002.booking.domain.passes;

import java.util.Arrays;
import java.util.Optional;

public enum PassOption {
    SINGLE_PASS("singlePass"),
    PACKAGE("package");

    private final String text;

    private PassOption(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static PassOption fromString(String text) {
        Optional<PassOption> value = Arrays.stream(values())
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
