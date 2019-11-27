package ca.ulaval.glo4002.booking.infrastructure.persistence.exceptions;

public class NotFoundException extends RuntimeException {

    public String entityName;
    public String identifier;

    public NotFoundException(String entityName, String identifier) {
        super(String.format("%s with number %s not found", entityName, identifier));
        
        this.entityName = entityName;
        this.identifier = identifier;
    }
}
