package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.passes.PassNumber;

public class ShuttleFiller {
    
    private ShuttleFactory shuttleFactory;

    public ShuttleFiller() {
        shuttleFactory = new ShuttleFactory();
    }
    
    public List<Shuttle> fillShuttle(List<Shuttle> shuttlesToFill, ShuttleCategory shuttleCategory, PassNumber passNumber, LocalDate date, int numberOfPassengers) {
        Shuttle availableShuttle = getAvailableShuttle(shuttlesToFill, shuttleCategory, date, numberOfPassengers);
        assignNewPlaces(availableShuttle, passNumber, numberOfPassengers);
        if (!shuttlesToFill.contains(availableShuttle)) {
            shuttlesToFill.add(availableShuttle);
        }
        return shuttlesToFill;
    }

    private Shuttle getAvailableShuttle(List<Shuttle> shuttlesToFill, ShuttleCategory shuttleCategory, LocalDate date, int numberOfPassengers) {
        Shuttle availableShuttle = shuttlesToFill.stream()
            .filter(shuttle -> shuttleIsAvailable(shuttle, shuttleCategory, date, numberOfPassengers))
            .findAny()
            .orElse(shuttleFactory.createShuttle(shuttleCategory, date));
        return availableShuttle;            
    }

    private boolean shuttleIsAvailable(Shuttle shuttleToVerify, ShuttleCategory shuttleCategory, LocalDate date, int numberOfPassengers) {
        return shuttleToVerify.hasCorrectDate(date) && shuttleToVerify.hasCorrectCategory(shuttleCategory) && shuttleToVerify.hasAvailableCapacity(numberOfPassengers);
    }

    private void assignNewPlaces(Shuttle availableShuttle, PassNumber passNumber, int numberOfPassengers) {
        for (int place = 0; place < numberOfPassengers; place++) {
            availableShuttle.addPassNumber(passNumber);
        }
    }
}
