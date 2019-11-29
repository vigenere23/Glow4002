package ca.ulaval.glo4002.booking.domain.transport;

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
            try {
                shuttle.addPassengers(passengerNumber, numberOfPassengers);
                shuttleRepository.replace(shuttle);
                return;
            }
            catch (IllegalArgumentException exception) {
                continue;
            }
        }

        Shuttle shuttle = shuttleFactory.create(direction, date, shuttleCategory);
        shuttle.addPassengers(passengerNumber, numberOfPassengers);
        shuttle.saveOutcome(outcomeSaver);
        shuttleRepository.add(shuttle);
    }
}
