package ca.ulaval.glo4002.booking.persistance.heap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.pressurizedGaz.History;

public class HeapOxygenHistoryTest {

    private HeapOxygenHistory historyPersistance;
    private History history;
    private final OffsetDateTime date = OffsetDateTime.of(LocalDate.of(2050, 2, 17), LocalTime.MIDNIGHT, ZoneOffset.UTC);

    @BeforeEach
    public void setUp() {
        historyPersistance = new HeapOxygenHistory();
        history = new History();
        history.date = date;
        history.qtyCandlesUsed = 2;
        history.qtyOxygenTankBought = 3;
        history.qtyOxygenTankMade = 6;
        history.qtyWaterUsed = 1;
    }

    @Test
    public void whenUpdateCreationHistory_thenHistoryIsCorrectlyUpdated() {
        historyPersistance.updateCreationHistory(date, history);
        assertEquals(history, historyPersistance.getCreationHistoryPerDate(date));
    }

    @Test
    public void whenGetCompleteCreationHistory_thenHistoryIsCorrectlyReturn() {
        historyPersistance.updateCreationHistory(date, history);
       
        History secondHistory = new History();
        OffsetDateTime secondDate = OffsetDateTime.of(LocalDate.of(2050, 5, 19), LocalTime.MIDNIGHT, ZoneOffset.UTC);
        secondHistory.date = secondDate;
        secondHistory.qtyCandlesUsed = 2;
        secondHistory.qtyOxygenTankBought = 3;
        secondHistory.qtyOxygenTankMade = 6;
        secondHistory.qtyWaterUsed = 1;

        historyPersistance.updateCreationHistory(secondDate, secondHistory);
        HashMap<OffsetDateTime, History> historySaved = historyPersistance.getCreationHistory();
        
        assertEquals(history, historySaved.get(date));       
        assertEquals(secondHistory, historySaved.get(secondDate));
    }

    @Test
    public void whenTrySaveNullHistory_thenHistoryIsEmptyForThatDate() {
        historyPersistance.updateCreationHistory(date, null);
        History historyForDateProvided = historyPersistance.getCreationHistoryPerDate(date);

        assertEquals(historyForDateProvided.date, date);
        assertEquals(historyForDateProvided.qtyCandlesUsed, 0);
        assertEquals(historyForDateProvided.qtyOxygenTankBought, 0);
        assertEquals(historyForDateProvided.qtyOxygenTankMade, 0);
        assertEquals(historyForDateProvided.qtyWaterUsed, 0);
    }

    @Test
    public void whenTrySaveNullHistoryOnExistingHistory_thenHistoryIsNotReplaced() {
        historyPersistance.updateCreationHistory(date, history);
        historyPersistance.updateCreationHistory(date, null);

        assertEquals(history, historyPersistance.getCreationHistoryPerDate(date));
    }
}