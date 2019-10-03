package ca.ulaval.glo4002.booking.persistance.heap.exceptions;

public class RecordNotReplaceableException extends Exception {

    public RecordNotReplaceableException() {
        super("Record Not Replacable - it doesn't have an id");
    }

    public RecordNotReplaceableException(String message) {
        super(message);
    }
}
