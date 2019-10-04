package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.LinkedList;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.ShuttlePersistance;

public class TransportRequester implements TransportExposer {
    
    private ShuttlePersistance transportRepository;
    private ShuttleFiller shuttleFiller;
    private Glow4002 festival;
    
    public TransportRequester(ShuttlePersistance transportRepository, Glow4002 festival) {
        this.transportRepository = transportRepository;
        this.festival = festival;
        shuttleFiller = new ShuttleFiller();
    }

    @Override
    public List<Shuttle> getShuttlesDepartureByDate(LocalDate date) throws OutOfFestivalDatesException {
        validateDateRange(date); 
        return transportRepository.getShuttlesByDate(Location.EARTH, date);
    }

    @Override
    public List<Shuttle> getShuttlesArrivalByDate(LocalDate date) throws OutOfFestivalDatesException {
        validateDateRange(date); 
        return transportRepository.getShuttlesByDate(Location.ULAVALOGY, date);
    }

    private void validateDateRange(LocalDate date) throws OutOfFestivalDatesException {
        if(!festival.isDuringEventTime(OffsetDateTime.of(date, LocalTime.NOON, ZoneOffset.UTC))) {
            throw new OutOfFestivalDatesException(festival.getStartDate(), festival.getEndDate());
        }
    }

    @Override
    public List<Shuttle> getAllDepartures() {
        return transportRepository.getShuttles(Location.EARTH);
    }

    @Override
    public List<Shuttle> getAllArrivals() {
        return transportRepository.getShuttles(Location.ULAVALOGY);
    }
    
    @Override
    public void reserveDeparture(ShuttleCategory shuttleCategory, LocalDate date, long passNumber) {
        List<Shuttle> shuttlesToSave = assignNewPlace(Location.EARTH, shuttleCategory, date, passNumber);
        transportRepository.saveDeparture(shuttlesToSave);
    }
    
    @Override
    public void reserveArrival(ShuttleCategory shuttleCategory, LocalDate date, long passNumber) {
        List<Shuttle> shuttlesToSave = assignNewPlace(Location.ULAVALOGY, shuttleCategory, date, passNumber);
        transportRepository.saveArrival(shuttlesToSave);
    }   

    private List<Shuttle> assignNewPlace(Location location, ShuttleCategory shuttleCategory, LocalDate date, long passNumber) {
        List<Shuttle> shuttlesToFill = new LinkedList<Shuttle>();
        shuttlesToFill = transportRepository.getShuttles(location);  
        return shuttleFiller.fillShuttle(shuttlesToFill, shuttleCategory, passNumber, date);
    }
}
