package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.persistanceInterface.ShuttlePersistance;

public class TransportService {
    
    private ShuttlePersistance transportRepository;
    private ShuttleFiller shuttleFiller;
    
    public TransportService(ShuttlePersistance transportRepository, ShuttleFiller shuttleFiller) {
        this.transportRepository = transportRepository;
        this.shuttleFiller = shuttleFiller;
    }
    
    public List<Shuttle> getShuttles(Location location) {
        return transportRepository.getShuttles(location);
    }
    
    public void reserveDeparture(ShuttleCategory shuttleCategory, LocalDate date, long passNumber)
            throws FullCapacityException {
        List<Shuttle> shuttlesToSave = assignNewPlace(Location.EARTH, shuttleCategory, date, passNumber);
        transportRepository.saveDeparture(shuttlesToSave);
    }
    
    public void reserveArrival(ShuttleCategory shuttleCategory, LocalDate date, long passNumber)
            throws FullCapacityException {
        List<Shuttle> shuttlesToSave = assignNewPlace(Location.ULAVALOGY, shuttleCategory, date, passNumber);
        transportRepository.saveArrival(shuttlesToSave);
    }

    private List<Shuttle> assignNewPlace(Location location, ShuttleCategory shuttleCategory, LocalDate date, long passNumber) throws FullCapacityException {
        List<Shuttle> shuttlesToFill = new LinkedList<Shuttle>();
        List<Shuttle> shuttlesToSave = new LinkedList<Shuttle>();
        
        shuttlesToFill = getShuttles(location);
        shuttlesToSave = shuttleFiller.fillShuttle(shuttlesToFill, shuttleCategory, passNumber, date);
        return shuttlesToSave;
    }
}
