package ca.ulaval.glo4002.booking.infrastructure.persistence.memory;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.oxygen.history.OxygenHistoryEntry;

public class InMemoryOxygenHistoryRepositoryTest {
    
    private final static LocalDate SOME_DATE = LocalDate.now();

    private InMemoryOxygenHistoryRepository oxygenHistory;

    @BeforeEach
    public void setup() {
        oxygenHistory = new InMemoryOxygenHistoryRepository();
    }

    @Test
    public void whenCreating_noEntryIsPresent() {
        assertThat(oxygenHistory.findAll()).isEmpty();
    }

    @Test
    public void givenEmptyHistory_whenFindingOrCreating_itReturnsAnEntry() {
        OxygenHistoryEntry entry = oxygenHistory.findOrCreate(SOME_DATE);
        assertThat(entry).isNotNull();
    }

    @Test
    public void givenNonEmptyHistory_whenFindingOrCreating_itReturnsTheExistingEntry() {
        OxygenHistoryEntry entry = oxygenHistory.findOrCreate(SOME_DATE);
        oxygenHistory.add(entry);

        OxygenHistoryEntry savedEntry = oxygenHistory.findOrCreate(SOME_DATE);
        
        assertThat(savedEntry).isEqualTo(entry);
    }

    @Test
    public void givenNewEntry_whenAddingEntry_itAddsTheNewEntryToTheHistory() {
        int oldSize = oxygenHistory.findAll().size();
        OxygenHistoryEntry entry = new OxygenHistoryEntry(SOME_DATE);
        
        oxygenHistory.add(entry);

        int newSize = oxygenHistory.findAll().size();
        assertThat(newSize).isEqualTo(oldSize + 1);
    }

    @Test
    public void givenExistantHistoryEntry_whenAddingEntry_itReplacesTheExistantEntry() {
        OxygenHistoryEntry entry = oxygenHistory.findOrCreate(SOME_DATE);
        oxygenHistory.add(entry);
        int oldSize = oxygenHistory.findAll().size();

        oxygenHistory.add(entry);

        int newSize = oxygenHistory.findAll().size();
        assertThat(newSize).isEqualTo(oldSize);
    }
}
