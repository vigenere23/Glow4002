package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.SortedMap;
import java.util.TreeMap;

import ca.ulaval.glo4002.booking.domain.oxygen.HistoryType;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenDateHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HeapOxygenDateHistoryRepositoryTest {

    private HeapOxygenHistoryRepository oxygenHistoryRepository;
    private final LocalDate date = LocalDate.of(2050, 2, 17);
    private SortedMap<LocalDate, OxygenDateHistory> oxygenHistories;

    @BeforeEach
    public void setUp() {
        oxygenHistoryRepository = new HeapOxygenHistoryRepository();
        OxygenDateHistory oxygenDateHistory = new OxygenDateHistory(date);
        oxygenDateHistory.updateQuantity(HistoryType.CANDLES_USED, 2);
        oxygenDateHistory.updateQuantity(HistoryType.OXYGEN_TANK_BOUGHT, 3);
        oxygenDateHistory.updateQuantity(HistoryType.OXYGEN_TANK_MADE, 6);
        oxygenDateHistory.updateQuantity(HistoryType.WATER_USED, 1);
        oxygenHistories = new TreeMap<LocalDate, OxygenDateHistory>();
        oxygenHistories.put(date, oxygenDateHistory);
    }

    @Test
    public void whenUpdateHistory_thenHistoryIsCorrectlyUpdated() {
        oxygenHistoryRepository.saveOxygenHistory(oxygenHistories);
        assertEquals(oxygenHistories, oxygenHistoryRepository.findOxygenHistory());
    }

    @Test
    public void whenTrySaveNullHistory_thenHistoryIsEmptyForThatDate() {
        oxygenHistoryRepository.saveOxygenHistory(null);
        SortedMap<LocalDate, OxygenDateHistory> historyProvided = oxygenHistoryRepository.findOxygenHistory();
        EnumMap<HistoryType, Integer> oxygenHistory = historyProvided.get(date).getOxygenHistory();

        for (int quantity: oxygenHistory.values()) {
            assertEquals(0, quantity);
        }
    }

    @Test
    public void whenTrySaveNullHistoryOnExistingHistory_thenHistoryIsNotReplaced() {
        oxygenHistoryRepository.saveOxygenHistory(oxygenHistories);
        oxygenHistoryRepository.saveOxygenHistory(null);

        assertEquals(oxygenHistories, oxygenHistoryRepository.findOxygenHistory());
    }
}