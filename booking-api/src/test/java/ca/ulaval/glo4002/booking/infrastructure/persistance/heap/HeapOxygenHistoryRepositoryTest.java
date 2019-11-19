package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistoryEntry;

public class HeapOxygenHistoryRepositoryTest {
    
    private final static LocalDate SOME_DATE = LocalDate.now();

    private HeapOxygenHistoryRepository oxygenHistory;

    @BeforeEach
    public void setup() {
        oxygenHistory = new HeapOxygenHistoryRepository();
    }

    @Test
    public void whenCreating_noHistoryEntryIsPresent() {
        assertThat(oxygenHistory.findAll()).isEmpty();
    }

    @Test
    public void givenEmptyHistory_whenFindingOrCreating_itReturnsAHistoryEntry() {
        OxygenHistoryEntry entry = oxygenHistory.findOrCreate(SOME_DATE);
        assertThat(entry).isNotNull();
    }

    @Test
    public void givenNonEmptyHistory_whenFindingOrCreating_itReturnsThePresentHistoryEntry() {
        OxygenHistoryEntry entry = oxygenHistory.findOrCreate(SOME_DATE);
        oxygenHistory.save(entry);

        OxygenHistoryEntry savedEntry = oxygenHistory.findOrCreate(SOME_DATE);
        
        assertThat(savedEntry).isEqualTo(entry);
    }

    @Test
    public void whenSaving_itAddsTheSavedEntryToTheHistory() {
        int oldSize = oxygenHistory.findAll().size();
        OxygenHistoryEntry entry = oxygenHistory.findOrCreate(SOME_DATE);
        oxygenHistory.save(entry);

        int newSize = oxygenHistory.findAll().size();
        
        assertThat(newSize).isEqualTo(oldSize + 1);
    }
}
