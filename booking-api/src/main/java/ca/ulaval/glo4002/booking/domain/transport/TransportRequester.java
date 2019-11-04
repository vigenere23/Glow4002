package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;
import ca.ulaval.glo4002.booking.domain.passes.PassNumber;
import ca.ulaval.glo4002.booking.domain.transport.Location;
import ca.ulaval.glo4002.booking.domain.transport.Shuttle;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleFiller;

public class TransportRequester implements TransportExposer {
    
    private ShuttleRepository transportRepository;
    private ShuttleFiller shuttleFiller;
    private Glow4002 festival;
    
    public TransportRequester(ShuttleRepository transportRepository, Glow4002 festival) {
        this.transportRepository = transportRepository;
        this.festival = festival;
        shuttleFiller = new ShuttleFiller();
    }

    @Override
    public List<Shuttle> getShuttlesDepartureByDate(LocalDate date) {
        validateDateRange(date); 
        return transportRepository.findShuttlesByDate(Location.EARTH, date);
    }

    @Override
    public List<Shuttle> getShuttlesArrivalByDate(LocalDate date) {
        validateDateRange(date); 
        return transportRepository.findShuttlesByDate(Location.ULAVALOGY, date);
    }

    private void validateDateRange(LocalDate date) {
        if (!festival.isDuringEventTime(date)) {
            throw new OutOfFestivalDatesException(festival.getStartDate(), festival.getEndDate());
        }
    }

    @Override
    public List<Shuttle> getAllDepartures() {
        return transportRepository.findShuttlesByLocation(Location.EARTH);
    }

    @Override
    public List<Shuttle> getAllArrivals() {
        return transportRepository.findShuttlesByLocation(Location.ULAVALOGY);
    }
    
    public void reserveDeparture(ShuttleCategory shuttleCategory, LocalDate date, PassNumber passNumber) {
        List<Shuttle> shuttlesToSave = assignNewPlace(Location.EARTH, shuttleCategory, date, passNumber);
        transportRepository.saveDeparture(shuttlesToSave);
    }
    
    public void reserveArrival(ShuttleCategory shuttleCategory, LocalDate date, PassNumber passNumber) {
        List<Shuttle> shuttlesToSave = assignNewPlace(Location.ULAVALOGY, shuttleCategory, date, passNumber);
        transportRepository.saveArrival(shuttlesToSave);
    }   

    private List<Shuttle> assignNewPlace(Location location, ShuttleCategory shuttleCategory, LocalDate date, PassNumber passNumber) {
        List<Shuttle> shuttlesToFill = new LinkedList<Shuttle>();
        shuttlesToFill = transportRepository.findShuttlesByLocation(location);  
        return shuttleFiller.fillShuttle(shuttlesToFill, shuttleCategory, passNumber, date);
    }
}
