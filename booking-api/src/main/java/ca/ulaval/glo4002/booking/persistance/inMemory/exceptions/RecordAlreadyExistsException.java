package ca.ulaval.glo4002.booking.persistance.inMemory.exceptions;

public class RecordAlreadyExistsException extends Exception {
    
    private static final long serialVersionUID = 1L;

    public RecordAlreadyExistsException() {
        super("Record Already Exists");
    }

    public RecordAlreadyExistsException(String message) {
        super(message);
    }
}
