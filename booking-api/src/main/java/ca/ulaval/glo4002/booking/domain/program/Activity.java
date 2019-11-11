package ca.ulaval.glo4002.booking.domain.program;

import java.util.Arrays;
import java.util.Optional;

import ca.ulaval.glo4002.booking.domain.exceptions.InvalidProgramException;

public enum Activity {

    YOGA("yoga"),
    CARDIO("cardio");

    private final String text;
    private static final int OXY_CARDIO = 15;
    private static final int OXY_YOGA = 10; 

    private Activity(String text) {
        this.text = text;
    }

    public static Activity fromString(String text) {
        Optional<Activity> value = Arrays.stream(values())
            .filter(entry -> entry.text.equalsIgnoreCase(text))
            .findFirst();

        if (!value.isPresent()) {
            throw new InvalidProgramException();
        }
        return value.get();
    }

    public static boolean contains(Activity activity) {
        boolean isAnActivity = false;
        for(Activity activityEnum : Activity.values()) {
            if (activityEnum.equals(activity)) {
                isAnActivity = true;
            }
        } 
        return isAnActivity; 
    }

    public static int oxygenForActivity(Activity activity) {
        return activity == CARDIO ? OXY_CARDIO : OXY_YOGA;
    }

    public static void artistIsActivity(String artistName) {
        if(artistName.equals("yoga") || artistName.equals("cardio")) {
            throw new InvalidProgramException();
        }
    }
}
