package ca.ulaval.glo4002.booking.domain.program.exceptions;

public class InvalidProgramException extends RuntimeException {

    private static final long serialVersionUID = 8431668129055881366L;

    public InvalidProgramException() {
        super("the program is invalid");
    }
}
