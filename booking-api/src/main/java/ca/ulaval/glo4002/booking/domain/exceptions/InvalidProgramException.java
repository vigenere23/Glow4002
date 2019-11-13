package ca.ulaval.glo4002.booking.domain.exceptions;

public class InvalidProgramException extends RuntimeException {

    public InvalidProgramException() {
        super("the program is invalid");
    }
}
