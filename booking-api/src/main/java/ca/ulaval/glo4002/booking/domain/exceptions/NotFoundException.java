package ca.ulaval.glo4002.booking.domain.exceptions;

public class NotFoundException extends RuntimeException {

    public String entityName;
    public String identifier;

    public NotFoundException(String entityName) {
        super(String.format("no %s found", entityName));

        this.entityName = entityName;
        this.identifier = "";
    }

    public NotFoundException(String entityName, String identifier) {
        super(String.format("%s with number %s not found", entityName, identifier));
        
        this.entityName = entityName;
        this.identifier = identifier;
    }
}
