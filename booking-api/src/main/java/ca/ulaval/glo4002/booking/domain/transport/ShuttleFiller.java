package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.Resource;

public class ShuttleFiller {
    
    @Resource
    private ShuttleFactory shuttleFactory = new ShuttleFactory();
    
    public List<Shuttle> fillShuttle(List<Shuttle> shuttlesToFill, ShuttleCategory shuttleCategory, long passNumber, LocalDate date) throws FullCapacityException {
        Shuttle availableShuttle = getAvailableShuttle(shuttlesToFill, shuttleCategory, date);
        availableShuttle.addPassNumber(passNumber);
        if(!shuttlesToFill.contains(availableShuttle)) {
            shuttlesToFill.add(availableShuttle);
        }
        return shuttlesToFill;
    }

    private Shuttle getAvailableShuttle(List<Shuttle> shuttlesToFill, ShuttleCategory shuttleCategory, LocalDate date) {
        Shuttle availableShuttle = shuttlesToFill.stream()
            .filter(shuttle -> {
                if(shuttleIsAvailable(shuttle, shuttleCategory, date)) {
                    return true;
                }
                return false;
            }).findAny()
            .orElse(shuttleFactory.createShuttle(shuttleCategory, date));
        return availableShuttle;                      
    }

    private boolean shuttleIsAvailable(Shuttle shuttleToVerify, ShuttleCategory shuttleCategory, LocalDate date) {
        boolean shuttleIsAtRightDate = date.equals(shuttleToVerify.getDate());
        boolean shuttleIsOfRightCategory = shuttleCategory.equals(shuttleToVerify.getCategory());
        boolean shuttleIsFull = shuttleToVerify.isFull();
        return shuttleIsAtRightDate && shuttleIsOfRightCategory && !shuttleIsFull;
    } 
}
