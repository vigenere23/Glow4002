package ca.ulaval.glo4002.booking.interfaces.rest.exception_mappers.dtos;

public class InvalidFormatErrorDto extends ClientErrorDto {
    public InvalidFormatErrorDto() {
        super("INVALID_FORMAT", "invalid format");
    }
}
