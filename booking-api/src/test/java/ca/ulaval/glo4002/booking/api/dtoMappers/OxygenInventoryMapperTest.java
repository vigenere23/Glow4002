package ca.ulaval.glo4002.booking.api.dtoMappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.api.resources.oxygen.dto.OxygenInventoryDto;
import ca.ulaval.glo4002.booking.api.resources.oxygen.dto.OxygenInventoryMapper;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen2.inventory.OxygenInventoryEntry;

public class OxygenInventoryMapperTest {

    private final static int SOME_INVENTORY_NUMBER = 12;
    private final static OxygenGrade SOME_OXYGEN_GRADE = OxygenGrade.A;
    
    private List<OxygenInventoryEntry> inventory = new ArrayList<>();
    private OxygenInventoryEntry inventoryEntry;
    private OxygenInventoryMapper inventoryMapper = new OxygenInventoryMapper();

    @BeforeEach
    public void setUpOxygenInventoryMapper() {
        mockInventoryItem();
 
        inventory.add(inventoryEntry);
    }

    @Test
    public void whenMappingInventoryToDto_thenReturnEquivalentInventoryDto() {
        OxygenInventoryDto inventoryDto = inventoryMapper.toDto(inventory).get(0);

        assertEquals(SOME_INVENTORY_NUMBER, inventoryDto.quantity);
        assertEquals(SOME_OXYGEN_GRADE.toString(), inventoryDto.gradeTankOxygen);
    }

    private void mockInventoryItem() {
        inventoryEntry = mock(OxygenInventoryEntry.class);

        when(inventoryEntry.getQuantity()).thenReturn(SOME_INVENTORY_NUMBER);
        when(inventoryEntry.getOxygenGrade()).thenReturn(SOME_OXYGEN_GRADE);
    }
}
