package ca.ulaval.glo4002.booking.helpers.enums;

import java.util.Arrays;
import java.util.Optional;

public enum TestEnum {

    VALUE("value");

    private final String text;

    private TestEnum(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static TestEnum fromString(String text) {
        Optional<TestEnum> value = Arrays.stream(values())
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
