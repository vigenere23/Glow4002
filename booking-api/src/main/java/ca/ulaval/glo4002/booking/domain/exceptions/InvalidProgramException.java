package ca.ulaval.glo4002.booking.domain.exceptions;

public class InvalidProgramException extends RuntimeException {

    public InvalidProgramException() {
        super(
            String.format(
                "the program is invalid"
            )
        );
    }
}
