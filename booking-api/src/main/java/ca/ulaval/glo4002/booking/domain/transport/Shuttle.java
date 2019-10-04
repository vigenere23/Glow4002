package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public abstract class Shuttle {
    
    protected int capacity;
    protected List<Long> passNumbers = new LinkedList<Long>();
    protected LocalDate date;
    protected ShuttleCategory category;

    public List<Long> getPassNumbers() {
        return passNumbers;
    }
    
    public LocalDate getDate() {
        return date;
    }

    public ShuttleCategory getCategory() {
        return category;
    }
        
    public void addPassNumber(Long passNumber) {
        this.passNumbers.add(passNumber);
    }
    
    public boolean isFull() {
        return passNumbers.size() == capacity ? true : false; 
    }
}
