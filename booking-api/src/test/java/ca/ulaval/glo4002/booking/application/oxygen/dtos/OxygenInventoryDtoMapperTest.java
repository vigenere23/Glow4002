package ca.ulaval.glo4002.booking.application.oxygen.dtos;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.inventory.OxygenInventoryEntry;

public class OxygenInventoryDtoMapperTest {

    private OxygenInventoryDtoMapper inventoryDtoMapper;
    private OxygenInventoryEntry inventoryEntry;

    @BeforeEach
    public void setuo() {
        inventoryDtoMapper = new OxygenInventoryDtoMapper();
        setupOxygenInventoryEntry();
    }

    @Test
    public void whenMappignToDto_itSetsTheSameGrade() {
        OxygenInventoryEntryDto inventoryEntryDto = inventoryDtoMapper.toEntryDto(inventoryEntry);
        assertThat(inventoryEntryDto.oxygenGrade).isEqualTo(inventoryEntry.getOxygenGrade());
    }

    @Test
    public void whenMappignToDto_itSetsTheSameQuantity() {
        OxygenInventoryEntryDto inventoryEntryDto = inventoryDtoMapper.toEntryDto(inventoryEntry);
        assertThat(inventoryEntryDto.quantity).isEqualTo(inventoryEntry.getTotalQuantity());
    }

    private void setupOxygenInventoryEntry() {
        inventoryEntry =  new OxygenInventoryEntry(OxygenGrade.A);
        inventoryEntry.addQuantity(10);
        inventoryEntry.useQuantity(5);
    }
}
