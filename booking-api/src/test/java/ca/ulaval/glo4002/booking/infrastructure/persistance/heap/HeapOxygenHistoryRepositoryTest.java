package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.oxygen.History;

public class HeapOxygenHistoryRepositoryTest {

    private HeapOxygenHistoryRepository oxygenHistoryRepository;
    private History history;
    private final LocalDate date = LocalDate.of(2050, 2, 17);

    @BeforeEach
    public void setUp() {
        oxygenHistoryRepository = new HeapOxygenHistoryRepository();
        history = new History();
        history.date = date;
        history.qtyCandlesUsed = 2;
        history.qtyOxygenTankBought = 3;
        history.qtyOxygenTankMade = 6;
        history.qtyWaterUsed = 1;
    }

    @Test
    public void whenUpdateCreationHistory_thenHistoryIsCorrectlyUpdated() {
        oxygenHistoryRepository.updateCreationHistory(date, history);
        assertEquals(history, oxygenHistoryRepository.getCreationHistoryPerDate(date));
    }

    @Test
    public void whenGetCompleteCreationHistory_thenHistoryIsCorrectlyReturn() {
        oxygenHistoryRepository.updateCreationHistory(date, history);
       
        History secondHistory = new History();
        LocalDate secondDate = LocalDate.of(2050, 5, 19);
        secondHistory.date = secondDate;
        secondHistory.qtyCandlesUsed = 2;
        secondHistory.qtyOxygenTankBought = 3;
        secondHistory.qtyOxygenTankMade = 6;
        secondHistory.qtyWaterUsed = 1;

        oxygenHistoryRepository.updateCreationHistory(secondDate, secondHistory);
        HashMap<LocalDate, History> historySaved = oxygenHistoryRepository.getCreationHistory();
        
        assertEquals(history, historySaved.get(date));       
        assertEquals(secondHistory, historySaved.get(secondDate));
    }

    @Test
    public void whenTrySaveNullHistory_thenHistoryIsEmptyForThatDate() {
        oxygenHistoryRepository.updateCreationHistory(date, null);
        History historyForDateProvided = oxygenHistoryRepository.getCreationHistoryPerDate(date);

        assertEquals(historyForDateProvided.date, date);
        assertEquals(historyForDateProvided.qtyCandlesUsed, 0);
        assertEquals(historyForDateProvided.qtyOxygenTankBought, 0);
        assertEquals(historyForDateProvided.qtyOxygenTankMade, 0);
        assertEquals(historyForDateProvided.qtyWaterUsed, 0);
    }

    @Test
    public void whenTrySaveNullHistoryOnExistingHistory_thenHistoryIsNotReplaced() {
        oxygenHistoryRepository.updateCreationHistory(date, history);
        oxygenHistoryRepository.updateCreationHistory(date, null);

        assertEquals(history, oxygenHistoryRepository.getCreationHistoryPerDate(date));
    }
}