package ca.ulaval.glo4002.booking.domain.transport2;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;

public class ShuttleFiller {
    
    @Inject private ShuttleFactory shuttleFactory;
    @Inject private OutcomeSaver outcomeSaver;

    public List<Shuttle> fillShuttle(List<Shuttle> shuttlesToFill, ShuttleCategory shuttleCategory, PassengerNumber passengerNumber, LocalDate date, int numberOfPassengers) {
        Shuttle availableShuttle = getAvailableShuttle(shuttlesToFill, shuttleCategory, date, numberOfPassengers);
        assignNewPlaces(availableShuttle, passengerNumber, numberOfPassengers);
        
        if (!shuttlesToFill.contains(availableShuttle)) {
            shuttlesToFill.add(availableShuttle);
            addShuttleCostToOutcome(availableShuttle);
        }
        
        return shuttlesToFill;
    }

    private Shuttle getAvailableShuttle(List<Shuttle> shuttlesToFill, ShuttleCategory shuttleCategory, LocalDate date, int numberOfPassengers) {
        return shuttlesToFill
            .stream()
            .filter(shuttle -> shuttleIsAvailable(shuttle, shuttleCategory, date, numberOfPassengers))
            .findAny()
            .orElse(shuttleFactory.createShuttle(shuttleCategory, date));          
    }

    private void addShuttleCostToOutcome(Shuttle shuttle) {
        shuttle.saveOutcome(outcomeSaver);
    }

    private boolean shuttleIsAvailable(Shuttle shuttleToVerify, ShuttleCategory shuttleCategory, LocalDate date, int numberOfPassengers) {
        return shuttleToVerify.hasDate(date) && shuttleToVerify.hasCategory(shuttleCategory) && shuttleToVerify.hasAvailableCapacity(numberOfPassengers);
    }

    private void assignNewPlaces(Shuttle availableShuttle, PassengerNumber passengerNumber, int numberOfPassengers) {
        for (int place = 0; place < numberOfPassengers; place++) {
            availableShuttle.addPassenger(passengerNumber);
        }
    }
}
