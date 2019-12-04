package ca.ulaval.glo4002.booking.domain.exceptions;

public class ItemNotFound extends RuntimeException {

    private static final long serialVersionUID = -628063557768970188L;
    
    public String entityName;
    public String identifier;

    public ItemNotFound(String entityName) {
        super(String.format("no %s found", entityName));

        this.entityName = entityName;
        this.identifier = "";
    }

    public ItemNotFound(String entityName, String identifier) {
        super(String.format("%s with number %s not found", entityName, identifier));
        
        this.entityName = entityName;
        this.identifier = identifier;
    }
}
