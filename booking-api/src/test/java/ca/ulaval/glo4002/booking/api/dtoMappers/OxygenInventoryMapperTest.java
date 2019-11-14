package ca.ulaval.glo4002.booking.api.dtoMappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.booking.api.resources.oxygen.dto.OxygenInventoryDto;
import ca.ulaval.glo4002.booking.api.resources.oxygen.dto.OxygenInventoryMapper;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventory;

public class OxygenInventoryMapperTest {

    private final static int SOME_INVENTORY_NUMBER = 12;
    private final static OxygenGrade SOME_OXYGEN_GRADE = OxygenGrade.A;
    
    private List<OxygenInventory> inventory = new ArrayList<>();
    private OxygenInventoryMapper inventoryMapper;

    @BeforeEach
    public void setUpMapper() {
        OxygenInventory inventoryItem = mock(OxygenInventory.class);

        when(inventoryItem.getInventory()).thenReturn(SOME_INVENTORY_NUMBER);
        when(inventoryItem.getOxygenGrade()).thenReturn(SOME_OXYGEN_GRADE);

        inventoryMapper = new OxygenInventoryMapper();
        inventory.add(inventoryItem);
    }

    @Test
    public void givenShuttleList_whenGetShuttlesDto_thenReturnEquivalentShuttlesDto() {
        OxygenInventoryDto inventoryDto = inventoryMapper.toDto(inventory).get(0);

        assertEquals(SOME_INVENTORY_NUMBER, inventoryDto.quantity);
        assertEquals(SOME_OXYGEN_GRADE.toString(), inventoryDto.gradeTankOxygen);
    }
}