package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.passes.PassNumber;

public abstract class Shuttle {
    
    protected int capacity;
    protected List<PassNumber> passNumbers = new ArrayList<>();
    protected LocalDate date;
    protected ShuttleCategory category;

    public List<PassNumber> getPassNumbers() {
        return passNumbers;
    }
    
    public LocalDate getDate() {
        return date;
    }

    public ShuttleCategory getCategory() {
        return category;
    }
        
    public void addPassNumber(PassNumber passNumber) {
        this.passNumbers.add(passNumber);
    }
    
    public boolean hasAvailableCapacity(int passengers) {
        return passNumbers.size() + passengers <= capacity; 
    }

    public boolean hasDate(LocalDate date) {
        return this.date.equals(date);
    }

	public boolean hasCategory(ShuttleCategory shuttleCategory) {
		return this.category == shuttleCategory;
	}
}
