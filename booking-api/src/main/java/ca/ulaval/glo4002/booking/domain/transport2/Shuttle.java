package ca.ulaval.glo4002.booking.domain.transport2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;

public abstract class Shuttle {
    
    protected int capacity;
    protected List<PassengerNumber> passengerNumbers = new ArrayList<>();
    protected LocalDate date;
    protected ShuttleCategory category;
    protected Price price;

    public List<PassengerNumber> getPassengerNumbers() {
        return passengerNumbers;
    }
    
    public LocalDate getDate() {
        return date;
    }

    public ShuttleCategory getCategory() {
        return category;
    }

    public void saveOutcome(OutcomeSaver outcomeSaver) {
        outcomeSaver.saveOutcome(price);
    }
        
    public void addPassenger(PassengerNumber passengerNumber) {
        this.passengerNumbers.add(passengerNumber);
    }
    
    public boolean hasAvailableCapacity(int numberOfPassengers) {
        return passengerNumbers.size() + numberOfPassengers <= capacity; 
    }
    
    public boolean hasDate(LocalDate date) {
        return this.date.equals(date);
    }

	public boolean hasCategory(ShuttleCategory shuttleCategory) {
		return this.category == shuttleCategory;
	}
}
