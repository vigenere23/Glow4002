package ca.ulaval.glo4002.booking.domain.oxygen2.history;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OxygenHistoryTest {
    
    private final static LocalDate SOME_DATE = LocalDate.now();

    private OxygenHistory oxygenHistory;

    @BeforeEach
    public void setup() {
        oxygenHistory = new OxygenHistory();
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
