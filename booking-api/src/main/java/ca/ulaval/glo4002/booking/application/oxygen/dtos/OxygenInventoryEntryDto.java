package ca.ulaval.glo4002.booking.application.oxygen.dtos;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;

public class OxygenInventoryEntryDto {
    
    public OxygenGrade oxygenGrade;
    public int quantity;

    public OxygenInventoryEntryDto(OxygenGrade oxygenGrade, int quantity) {
        this.oxygenGrade = oxygenGrade;
        this.quantity = quantity;
    }
}
