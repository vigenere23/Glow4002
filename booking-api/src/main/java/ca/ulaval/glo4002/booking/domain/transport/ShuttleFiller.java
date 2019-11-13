package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.passes.PassNumber;

public class ShuttleFiller {
    
    private ShuttleFactory shuttleFactory;

    public ShuttleFiller(ShuttleFactory shuttleFactory) {
        this.shuttleFactory = shuttleFactory;
    }
    
    public List<Shuttle> fillShuttle(List<Shuttle> shuttlesToFill, ShuttleCategory shuttleCategory, PassNumber passNumber, LocalDate date, int numberOfPassengers) {
        Shuttle availableShuttle = getAvailableShuttle(shuttlesToFill, shuttleCategory, date, numberOfPassengers);
        assignNewPlaces(availableShuttle, passNumber, numberOfPassengers);
        if (!shuttlesToFill.contains(availableShuttle)) {
            shuttlesToFill.add(availableShuttle);
            addShuttleCostToOutcome(availableShuttle);
        }
        return shuttlesToFill;
    }

    private Shuttle getAvailableShuttle(List<Shuttle> shuttlesToFill, ShuttleCategory shuttleCategory, LocalDate date, int numberOfPassengers) {
        return shuttlesToFill.stream()
            .filter(shuttle -> shuttleIsAvailable(shuttle, shuttleCategory, date, numberOfPassengers))
            .findAny()
            .orElse(shuttleFactory.createShuttle(shuttleCategory, date));          
    }

    private void addShuttleCostToOutcome(Shuttle shuttle) {
        shuttle.saveOutcome();
    }
    private boolean shuttleIsAvailable(Shuttle shuttleToVerify, ShuttleCategory shuttleCategory, LocalDate date, int numberOfPassengers) {
        return shuttleToVerify.hasDate(date) && shuttleToVerify.hasCategory(shuttleCategory) && shuttleToVerify.hasAvailableCapacity(numberOfPassengers);
    }

    private void assignNewPlaces(Shuttle availableShuttle, PassNumber passNumber, int numberOfPassengers) {
        for (int place = 0; place < numberOfPassengers; place++) {
            availableShuttle.addPassNumber(passNumber);
        }
    }
}
