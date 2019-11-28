package ca.ulaval.glo4002.booking.domain.program;

import java.util.Arrays;
import java.util.Optional;

public enum Activity {

    YOGA("yoga"),
    CARDIO("cardio");

    private final String text;
    private static final int OXY_CARDIO = 15;
    private static final int OXY_YOGA = 10; 

    private Activity(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static Activity fromString(String text) {
        Optional<Activity> value = Arrays.stream(values())
            .filter(entry -> entry.text.equalsIgnoreCase(text))
            .findFirst();

        if (!value.isPresent()) {
            throw new IllegalArgumentException(
                String.format("No enum value for string %s", text)
            );
        }
        return value.get();
    }

    public static int oxygenForActivity(Activity activity) {
        return activity == CARDIO ? OXY_CARDIO : OXY_YOGA;
    }
}
