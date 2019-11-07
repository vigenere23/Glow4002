package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.passes.PassNumber;

public class ShuttleFiller {
    
    private ShuttleFactory shuttleFactory;
    private ShuttleRepository shuttleRepository;

    public ShuttleFiller(ShuttleRepository shuttleRepository) {
        shuttleFactory = new ShuttleFactory();
        this.shuttleRepository = shuttleRepository;
    }
    
    public List<Shuttle> fillShuttle(List<Shuttle> shuttlesToFill, ShuttleCategory shuttleCategory, PassNumber passNumber, LocalDate date) {
        Shuttle availableShuttle = getAvailableShuttle(shuttlesToFill, shuttleCategory, date);
        availableShuttle.addPassNumber(passNumber);
        if (!shuttlesToFill.contains(availableShuttle)) {
            shuttlesToFill.add(availableShuttle);
        }
        return shuttlesToFill;
    }

    private Shuttle getAvailableShuttle(List<Shuttle> shuttlesToFill, ShuttleCategory shuttleCategory, LocalDate date) {
        Shuttle availableShuttle = shuttlesToFill.stream()
            .filter(shuttle -> {
                return shuttleIsAvailable(shuttle, shuttleCategory, date) ? true : false ;
            }).findAny()
            .orElse(shuttleFactory.createShuttle(shuttleCategory, date));
        return availableShuttle;            
    }

    private boolean shuttleIsAvailable(Shuttle shuttleToVerify, ShuttleCategory shuttleCategory, LocalDate date) {
        return date.equals(shuttleToVerify.getDate()) && shuttleCategory.equals(shuttleToVerify.getCategory()) && !shuttleToVerify.isFull();
    } 
}
