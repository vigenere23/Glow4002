package ca.ulaval.glo4002.booking.application.oxygen.dtos;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;

public class OxygenInventoryEntryDto {
    
    public String gradeTankOxygen;
    public int quantity;

    public OxygenInventoryEntryDto() {}

    public OxygenInventoryEntryDto(OxygenGrade gradeTankOxygen, int quantity) {
        this.gradeTankOxygen = gradeTankOxygen.toString();
        this.quantity = quantity;
    }
}
