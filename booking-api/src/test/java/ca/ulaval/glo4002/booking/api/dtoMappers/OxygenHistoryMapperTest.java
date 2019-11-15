package ca.ulaval.glo4002.booking.api.dtoMappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.SortedMap;
import java.util.TreeMap;

import ca.ulaval.glo4002.booking.api.resources.oxygen.dto.OxygenHistoryDto;
import ca.ulaval.glo4002.booking.api.resources.oxygen.dto.OxygenHistoryMapper;
import ca.ulaval.glo4002.booking.domain.oxygen.HistoryType;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryItem;

public class OxygenHistoryMapperTest {

    private final static LocalDate SOME_DATE = LocalDate.of(2050, 7, 17);
    private final static int SOME_NUMBER_OF_CANDLES = 45;
    private final static int SOME_QUANTITY_OF_WATER_USED = 24;
    private final static int SOME_NUMBER_OF_TANKS_BOUGHT = 0;
    private final static int SOME_NUMBER_OF_TANKS_MADE = 0;

    private EnumMap<HistoryType, Integer> SOME_HISTORY;
    private SortedMap<LocalDate, OxygenHistoryItem> history;
    private OxygenHistoryItem historyItem;
    private OxygenHistoryMapper historyMapper = new OxygenHistoryMapper();
    
    @BeforeEach
    public void setUpOxygenHistoryMapper() {
        mockOxygenHistoryItem();
        createHistoryTypeMap();
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
        historyItem = mock(OxygenHistoryItem.class);

        when(historyItem.getCandlesUsed()).thenReturn(SOME_NUMBER_OF_CANDLES);
        when(historyItem.getDate()).thenReturn(SOME_DATE);
        when(historyItem.getOxygenTankBought()).thenReturn(SOME_NUMBER_OF_TANKS_BOUGHT);
        when(historyItem.getOxygenTankMade()).thenReturn(SOME_NUMBER_OF_TANKS_MADE);
        when(historyItem.getWaterUsed()).thenReturn(SOME_QUANTITY_OF_WATER_USED);
        when(historyItem.getOxygenHistory()).thenReturn(SOME_HISTORY);
    }

    private void createHistoryMap() {
        history = new TreeMap<>();
        history.put(SOME_DATE, historyItem);
    }

    private void createHistoryTypeMap() {
        SOME_HISTORY = new EnumMap<HistoryType, Integer>(HistoryType.class);
        SOME_HISTORY.put(HistoryType.OXYGEN_TANK_BOUGHT, SOME_NUMBER_OF_TANKS_BOUGHT);
        SOME_HISTORY.put(HistoryType.OXYGEN_TANK_MADE, SOME_NUMBER_OF_TANKS_MADE);
        SOME_HISTORY.put(HistoryType.WATER_USED, SOME_QUANTITY_OF_WATER_USED);
        SOME_HISTORY.put(HistoryType.CANDLES_USED, SOME_NUMBER_OF_CANDLES);
    }
}