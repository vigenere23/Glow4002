package ca.ulaval.glo4002.booking.domain.transport2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;

public abstract class Shuttle {
    
    private Direction direction;
    private LocalDate date;
    private ShuttleCategory category;
    private int capacity;
    private Price price;
    private List<PassengerNumber> passengerNumbers;

    protected Shuttle(Direction direction, LocalDate date, ShuttleCategory category, int capacity, Price price) {
        this.direction = direction;
        this.date = date;
        this.category = category;
        this.capacity = capacity;
        this.price = price;

        passengerNumbers = new ArrayList<>();
    }

    public void saveOutcome(OutcomeSaver outcomeSaver) {
        outcomeSaver.saveOutcome(price);
    }
        
    public void addPassengers(PassengerNumber passengerNumber, int numberOfPassengers) {
        for (int i = 0; i < numberOfPassengers; i++) {
            this.passengerNumbers.add(passengerNumber);
        }
    }
    
    public boolean hasAvailableCapacity(int numberOfPassengers) {
        return passengerNumbers.size() + numberOfPassengers <= capacity; 
    }

    public boolean isFull() {
        return passengerNumbers.size() == capacity;
    }

    public Direction getDirection() {
        return direction;
    }

    public List<PassengerNumber> getPassengerNumbers() {
        return passengerNumbers;
    }
    
    public LocalDate getDate() {
        return date;
    }

    public ShuttleCategory getCategory() {
        return category;
    }
    
    public boolean hasDate(LocalDate date) {
        return this.date.equals(date);
    }

	public boolean hasCategory(ShuttleCategory shuttleCategory) {
		return this.category == shuttleCategory;
	}
}
