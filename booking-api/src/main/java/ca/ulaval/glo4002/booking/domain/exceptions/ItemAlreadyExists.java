package ca.ulaval.glo4002.booking.domain.exceptions;

public class ItemAlreadyExists extends RuntimeException {

    private static final long serialVersionUID = 608694868825154037L;

    public ItemAlreadyExists(String className, String item) {
        super(String.format("%s item %s already saved", className, item));
    }
}
