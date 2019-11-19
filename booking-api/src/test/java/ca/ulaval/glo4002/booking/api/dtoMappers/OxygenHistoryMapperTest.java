package ca.ulaval.glo4002.booking.api.dtoMappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.api.resources.oxygen.dto.OxygenHistoryDto;
import ca.ulaval.glo4002.booking.api.resources.oxygen.dto.OxygenHistoryMapper;
import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistoryEntry;

public class OxygenHistoryMapperTest {

    private final static LocalDate SOME_DATE = LocalDate.of(2050, 7, 17);
    private final static int SOME_NUMBER_OF_CANDLES = 45;
    private final static int SOME_QUANTITY_OF_WATER_USED = 24;
    private final static int SOME_NUMBER_OF_TANKS_BOUGHT = 0;
    private final static int SOME_NUMBER_OF_TANKS_MADE = 0;

    private List<OxygenHistoryEntry> history;
    private OxygenHistoryEntry historyEntry;
    private OxygenHistoryMapper historyMapper = new OxygenHistoryMapper();
    
    @BeforeEach
    public void setUpOxygenHistoryMapper() {
        mockOxygenHistoryItem();
        createHistoryMap();
    }

    @Test
    public void whenMappingHistoryToDto_thenReturnEquivalentHistoryDto() {
        OxygenHistoryDto historyDto = historyMapper.toDto(history).get(0);
        
        assertEquals(SOME_NUMBER_OF_CANDLES, historyDto.qtyCandlesUsed);
        assertEquals(SOME_DATE, historyDto.date);
        assertEquals(SOME_NUMBER_OF_TANKS_BOUGHT, historyDto.qtyOxygenTankBought);
        assertEquals(SOME_NUMBER_OF_TANKS_MADE, historyDto.qtyOxygenTankMade);
        assertEquals(SOME_QUANTITY_OF_WATER_USED, historyDto.qtyWaterUsed);
    }

    private void mockOxygenHistoryItem() {
        historyEntry = mock(OxygenHistoryEntry.class);

        when(historyEntry.getCandlesUsed()).thenReturn(SOME_NUMBER_OF_CANDLES);
        when(historyEntry.getDate()).thenReturn(SOME_DATE);
        when(historyEntry.getTankBought()).thenReturn(SOME_NUMBER_OF_TANKS_BOUGHT);
        when(historyEntry.getTankMade()).thenReturn(SOME_NUMBER_OF_TANKS_MADE);
        when(historyEntry.getWaterUsed()).thenReturn(SOME_QUANTITY_OF_WATER_USED);
    }

    private void createHistoryMap() {
        history = new ArrayList<>();
        history.add(historyEntry);
    }
}
