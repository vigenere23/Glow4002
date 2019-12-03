package ca.ulaval.glo4002.booking.domain.artists;

import java.util.Arrays;
import java.util.Optional;

public enum ArtistRanking {
    LOW_COSTS("lowCosts"),
    MOST_POPULARITY("mostPopular");

    private final String text;

    private ArtistRanking(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static ArtistRanking fromString(String text) {
        Optional<ArtistRanking> value = Arrays.stream(values())
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
