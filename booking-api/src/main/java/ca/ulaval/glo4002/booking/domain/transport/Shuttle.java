package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.passes.PassNumber;

public abstract class Shuttle {
    
    protected int capacity;
    protected List<PassNumber> passNumbers = new LinkedList<PassNumber>();
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
    
    public boolean availableCapacity(int passengers) {
        return (passNumbers.size() + passengers) > capacity ? true : false; 
    }
}
