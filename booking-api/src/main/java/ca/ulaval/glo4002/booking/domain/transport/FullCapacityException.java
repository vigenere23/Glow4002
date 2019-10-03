package ca.ulaval.glo4002.booking.domain.transport;

public class FullCapacityException extends Exception {
    public FullCapacityException() {
        super("Shuttle is full. Cannot add another pass number");
    }
}