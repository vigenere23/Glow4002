package ca.ulaval.glo4002.booking.domain.artists;

import java.util.Arrays;
import java.util.Optional;

public enum ArtistSortingType {
    LOW_COSTS("lowCosts"),
    MOST_POPULARITY("mostPopular");

    private final String text;

    private ArtistSortingType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static ArtistSortingType fromString(String text) {
        Optional<ArtistSortingType> value = Arrays.stream(values())
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
