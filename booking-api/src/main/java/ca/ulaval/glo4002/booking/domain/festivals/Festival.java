package ca.ulaval.glo4002.booking.domain.festivals;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.domain.persistanceInterface.ShuttlePersistance;
import ca.ulaval.glo4002.booking.domain.transport.Location;
import ca.ulaval.glo4002.booking.domain.transport.Shuttle;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleFiller;
import ca.ulaval.glo4002.booking.domain.transport.TransportService;
import ca.ulaval.glo4002.booking.persistance.heap.HeapShuttlePersistance;
import ca.ulaval.glo4002.booking.interfaces.rest.responses.ShuttleDto;

public abstract class Festival {
    
    protected ShuttlePersistance repository = new HeapShuttlePersistance();
    protected ShuttleFiller shuttleFiller = new ShuttleFiller();
    protected TransportService transportService = new TransportService(repository, shuttleFiller);

    protected OffsetDateTime startDate;
    protected OffsetDateTime endDate;
    protected OffsetDateTime saleStartDate;
    protected OffsetDateTime saleEndDate;

    protected Festival(OffsetDateTime startDate, OffsetDateTime endDate, OffsetDateTime saleStartDate, OffsetDateTime saleEndDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.saleStartDate = saleStartDate;
        this.saleEndDate = saleEndDate;
    }

    public OffsetDateTime getStartDate() {
        return this.startDate;
    }

    public OffsetDateTime getEndDate() {
        return this.endDate;
    }

    public boolean isDuringSaleTime(OffsetDateTime dateTime) {
        return dateTime.isAfter(this.saleStartDate) && dateTime.isBefore(this.saleEndDate);
    }

    public boolean isDuringEventTime(OffsetDateTime dateTime) {
        return dateTime.isAfter(this.startDate) && dateTime.isBefore(this.endDate);
    }
    
    public List<ShuttleDto> getAllShuttles(Location location) {
        List<Shuttle> shuttlesByLocation = transportService.getShuttles(location);
        
		return getShuttlesDto(shuttlesByLocation);
	}

	public List<ShuttleDto> getShuttles(String date, Location location) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        LocalDate localDate = LocalDate.parse(date, formatter); 

        List<Shuttle> shuttlesByLocation = transportService.getShuttles(location);

        List<Shuttle> shuttlesByDate = shuttlesByLocation.stream()
                .filter(shuttle -> localDate.equals(shuttle.getDate()))
                .collect(Collectors.toList());
      
		return getShuttlesDto(shuttlesByDate);
    }

    private List<ShuttleDto> getShuttlesDto(List<Shuttle> shuttles) {
        List<ShuttleDto> shuttlesDto = new LinkedList<>();
        for (Shuttle shuttle: shuttles) {        
            ShuttleDto shuttleDto = new ShuttleDto();
            shuttleDto.date = shuttle.getDate().toString();
            shuttleDto.passengers = shuttle.getPassNumbers();
            shuttleDto.shuttleName = shuttle.getCategory().toString();
            shuttlesDto.add(shuttleDto);
        }
        return shuttlesDto; 
    }
}
