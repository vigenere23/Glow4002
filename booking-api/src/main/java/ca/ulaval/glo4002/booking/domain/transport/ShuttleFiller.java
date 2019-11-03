package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.passes.PassNumber;

public class ShuttleFiller {
    
    private ShuttleFactory shuttleFactory = new ShuttleFactory();
    
    public List<Shuttle> fillShuttle(List<Shuttle> shuttlesToFill, ShuttleCategory shuttleCategory, PassNumber passNumber, LocalDate date, int passengers) {
        Shuttle availableShuttle = getAvailableShuttle(shuttlesToFill, shuttleCategory, date, passengers);
        assignNewPlaces(availableShuttle, passNumber, passengers);
        if (!shuttlesToFill.contains(availableShuttle)) {
            shuttlesToFill.add(availableShuttle);
        }
        return shuttlesToFill;
    }

    private Shuttle getAvailableShuttle(List<Shuttle> shuttlesToFill, ShuttleCategory shuttleCategory, LocalDate date, int passengers) {
        Shuttle availableShuttle = shuttlesToFill.stream()
            .filter(shuttle -> {
                if (shuttleIsAvailable(shuttle, shuttleCategory, date, passengers)) {
                    return true;
                }
                return false;
            }).findAny()
            .orElse(shuttleFactory.createShuttle(shuttleCategory, date));
        return availableShuttle;                      
    }

    private boolean shuttleIsAvailable(Shuttle shuttleToVerify, ShuttleCategory shuttleCategory, LocalDate date, int passengers) {
        return date.equals(shuttleToVerify.getDate()) && shuttleCategory.equals(shuttleToVerify.getCategory()) && !shuttleToVerify.availableCapacity(passengers);
    }

    private void assignNewPlaces(Shuttle availableShuttle, PassNumber passNumber, int passengers) {
        for (int place = 0; place < passengers; place++) {
            availableShuttle.addPassNumber(passNumber);
        }
    }
}
