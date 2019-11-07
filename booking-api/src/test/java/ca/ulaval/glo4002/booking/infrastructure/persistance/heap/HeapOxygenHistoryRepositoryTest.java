package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.SortedMap;
import java.util.TreeMap;

import ca.ulaval.glo4002.booking.domain.oxygen.HistoryType;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenDateHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HeapOxygenHistoryRepositoryTest {

    private static final int SOME_CANDLES_USED_QTY = 2;
    private static final int SOME_OXYGEN_TANK_BOUGH_QTY = 2;
    private static final int SOME_OXYGEN_TANK_MADE_USED_QTY = 2;
    private static final int SOME_WATER_USED_QTY = 2;

    private HeapOxygenHistoryRepository oxygenHistoryRepository;
    private final LocalDate date = LocalDate.of(2050, 2, 17);
    private SortedMap<LocalDate, OxygenDateHistory> oxygenHistory;

    @BeforeEach
    public void setUp() {
        OxygenDateHistory oxygenDateHistory = new OxygenDateHistory(date);
        initializeOxygenDateHistory(oxygenDateHistory);
        initializeOxygenHistory(oxygenDateHistory);

        oxygenHistoryRepository = new HeapOxygenHistoryRepository();
    }

    @Test
    public void whenUpdateHistory_thenHistoryIsCorrectlyUpdated() {
        oxygenHistoryRepository.save(oxygenHistory);
        assertEquals(oxygenHistory, oxygenHistoryRepository.findOxygenHistory());
    }

    private void initializeOxygenDateHistory(OxygenDateHistory oxygenDateHistory) {
        oxygenDateHistory.updateQuantity(HistoryType.CANDLES_USED, SOME_CANDLES_USED_QTY);
        oxygenDateHistory.updateQuantity(HistoryType.OXYGEN_TANK_BOUGHT, SOME_OXYGEN_TANK_BOUGH_QTY);
        oxygenDateHistory.updateQuantity(HistoryType.OXYGEN_TANK_MADE, SOME_OXYGEN_TANK_MADE_USED_QTY);
        oxygenDateHistory.updateQuantity(HistoryType.WATER_USED, SOME_WATER_USED_QTY);
    }

    private void initializeOxygenHistory(OxygenDateHistory oxygenDateHistory) {
        oxygenHistory = new TreeMap<LocalDate, OxygenDateHistory>();
        oxygenHistory.put(date, oxygenDateHistory);
    }
}