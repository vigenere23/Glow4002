package ca.ulaval.glo4002.booking.persistance.heap.exceptions;

public class RecordNotReplaceableException extends Exception {
    
    private static final long serialVersionUID = 1L;

    public RecordNotReplaceableException() {
        super("Record Not Replacable - it doesn't have an id");
    }

    public RecordNotReplaceableException(String message) {
        super(message);
    }
}
