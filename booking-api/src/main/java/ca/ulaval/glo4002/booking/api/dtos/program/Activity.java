package ca.ulaval.glo4002.booking.api.dtos.program;

import java.util.Arrays;
import java.util.Optional;

public enum Activity {

    YOGA("yoga"),
    CARDIO("cardio");

    private final String text;

    private Activity(String text) {
        this.text = text;
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

    public static boolean contains(Activity am) {
        boolean isAnActivity = false;
        for(Activity activity : Activity.values()) {
            if (activity.equals(am)) {
                isAnActivity = true;
            }
        } 
        return isAnActivity; 
    }
}
