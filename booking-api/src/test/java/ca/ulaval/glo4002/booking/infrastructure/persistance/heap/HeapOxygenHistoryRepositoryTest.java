package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Map;

import ca.ulaval.glo4002.booking.domain.oxygen.HistoryType;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryItem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HeapOxygenHistoryRepositoryTest {

    private final static LocalDate SOME_DATE = LocalDate.of(2050, 2, 17);
    private final static int SOME_CANDLES_USED_QTY = 2;
    private final static int SOME_OXYGEN_TANK_BOUGH_QTY = 2;
    private final static int SOME_OXYGEN_TANK_MADE_USED_QTY = 2;
    private final static int SOME_WATER_USED_QTY = 2;

    private HeapOxygenHistoryRepository oxygenHistoryRepository;

    @BeforeEach
    public void setUpOxygenHistoryRepository() {
        initializeOxygenHistoryItem();

        oxygenHistoryRepository = new HeapOxygenHistoryRepository();
    }

    @Test
    public void HistoryIsInitialized() {
        Map<LocalDate, OxygenHistoryItem> history = oxygenHistoryRepository.findAll();

        assertEquals(0, history.size());
    }

    @Test
    public void whenUpdateHistory_thenHistoryIsCorrectlyUpdated() {
        OxygenHistoryItem someOxygenHistoryItem = initializeOxygenHistoryItem();
        oxygenHistoryRepository.save(someOxygenHistoryItem);

        assertEquals(someOxygenHistoryItem, oxygenHistoryRepository.findOxygenHistoryOfDate(SOME_DATE));
    }

    private OxygenHistoryItem initializeOxygenHistoryItem() {
        OxygenHistoryItem someOxygenHistoryItem = new OxygenHistoryItem(SOME_DATE);
        someOxygenHistoryItem.updateQuantity(HistoryType.CANDLES_USED, SOME_CANDLES_USED_QTY);
        someOxygenHistoryItem.updateQuantity(HistoryType.OXYGEN_TANK_BOUGHT, SOME_OXYGEN_TANK_BOUGH_QTY);
        someOxygenHistoryItem.updateQuantity(HistoryType.OXYGEN_TANK_MADE, SOME_OXYGEN_TANK_MADE_USED_QTY);
        someOxygenHistoryItem.updateQuantity(HistoryType.WATER_USED, SOME_WATER_USED_QTY);

        return someOxygenHistoryItem;
    }
}