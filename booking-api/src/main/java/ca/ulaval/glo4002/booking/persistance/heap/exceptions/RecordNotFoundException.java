package ca.ulaval.glo4002.booking.persistance.heap.exceptions;

public class RecordNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public RecordNotFoundException() {
        super("Record Not Found");
    }

    public RecordNotFoundException(String message) {
        super(message);
    }
}