package ca.ulaval.glo4002.booking.persistance.heap.exceptions;

public class RecordAlreadyExistsException extends Exception {

    public RecordAlreadyExistsException() {
        super("Record Already Exists");
    }

    public RecordAlreadyExistsException(String message) {
        super(message);
    }
}
