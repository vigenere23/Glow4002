package ca.ulaval.glo4002.booking.domain.transport2;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;

public class ShuttleFiller {
    
    @Inject private ShuttleRepository shuttleRepository;
    @Inject private ShuttleFactory shuttleFactory;
    @Inject private OutcomeSaver outcomeSaver;

    public void fillShuttles(Direction direction, LocalDate date, ShuttleCategory shuttleCategory, PassengerNumber passengerNumber, int numberOfPassengers) {
        List<Shuttle> availableShuttles = shuttleRepository.findAllAvailable(direction, date, shuttleCategory);

        for (Shuttle shuttle : availableShuttles) {
            if (shuttle.hasAvailableCapacity(numberOfPassengers)) {
                shuttle.addPassengers(passengerNumber, numberOfPassengers);
                shuttleRepository.replace(shuttle);
                return;
            }
        }

        Shuttle shuttle = shuttleFactory.create(direction, date, shuttleCategory);
        shuttle.saveOutcome(outcomeSaver);
        shuttle.addPassengers(passengerNumber, numberOfPassengers);
        shuttleRepository.add(shuttle);
    }
}
