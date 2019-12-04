package ca.ulaval.glo4002.booking.domain.transport;

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
        outcomeSaver.addOutcome(price);
    }
    
    public void addPassengers(PassengerNumber passengerNumber, int numberOfPassengers) {
        assert numberOfPassengers > 0;
        validateAvailableCapacity(numberOfPassengers);

        for (int i = 0; i < numberOfPassengers; i++) {
            this.passengerNumbers.add(passengerNumber);
        }
    }

    private void validateAvailableCapacity(int numberOfPassengers) {
        if (passengerNumbers.size() + numberOfPassengers > capacity) {
            throw new IllegalArgumentException(
                String.format("Not enough space for %d passengers", numberOfPassengers)
            );
        }
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
}
