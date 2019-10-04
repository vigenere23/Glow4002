package ca.ulaval.glo4002.booking.interfaces.rest.responses;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;
import ca.ulaval.glo4002.booking.domain.transport.Location;
import ca.ulaval.glo4002.booking.interfaces.rest.exceptionMappers.OutOfFestivalDateException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TransportResponse {
    
    public List<ShuttleDto> departures = new LinkedList<>();
    public List<ShuttleDto> arrivals = new LinkedList<>();
    private Glow4002 festival;

    @JsonCreator
    public TransportResponse(@JsonProperty(value = "date", required = false) String stringDate, Glow4002 festival) throws OutOfFestivalDateException {
        this.festival = festival;
        if (stringDate == null) {
            getAllShuttles();
        } else {
            getShuttlesForDate(stringDate);
        }
    }

    private void getAllShuttles() {
        this.departures = festival.getAllShuttles(Location.EARTH);
        this.arrivals = festival.getAllShuttles(Location.ULAVALOGY);
    }

    private void getShuttlesForDate(String stringDate) throws OutOfFestivalDateException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        LocalDate localDate = LocalDate.parse(stringDate, formatter); 

        if(!isDuringFestival(localDate)) {
            throw new OutOfFestivalDateException();
        }
        this.departures = festival.getShuttles(stringDate, Location.EARTH);
        this.arrivals = festival.getShuttles(stringDate, Location.ULAVALOGY);  
    }

    private boolean isDuringFestival(LocalDate date) {
        boolean isDuringFestival = true;
        if(date.isBefore(festival.getStartDate().toLocalDate()) || date.isAfter(festival.getEndDate().toLocalDate())) {
            isDuringFestival = false;
        }
        return isDuringFestival;
    }
}
