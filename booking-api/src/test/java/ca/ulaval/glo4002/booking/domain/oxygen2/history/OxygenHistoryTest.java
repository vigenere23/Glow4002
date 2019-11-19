package ca.ulaval.glo4002.booking.domain.oxygen2.history;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OxygenHistoryTest {
    
    private final static LocalDate SOME_DATE = LocalDate.now();
    private final static int SOME_QUANTITY = 15;

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
    public void givenEmptyHistory_whenFinding_itReturnsAnEmptyOptional() {
        Optional<OxygenHistoryEntry> entry = oxygenHistory.find(SOME_DATE);
        assertThat(entry).isNotPresent();
    }

    @Test
    public void givenEmptyHistory_whenAddingTankBought_itCreatesNewEntry() {
        oxygenHistory.addTankBought(SOME_DATE, SOME_QUANTITY);
        assertThat(oxygenHistory.find(SOME_DATE)).isPresent();
    }

    @Test
    public void givenEmptyHistory_whenAddingTankMadet_itCreatesNewEntry() {
        oxygenHistory.addTankMade(SOME_DATE, SOME_QUANTITY);
        assertThat(oxygenHistory.find(SOME_DATE)).isPresent();
    }

    @Test
    public void givenEmptyHistory_whenAddingCandlesUsed_itCreatesNewEntry() {
        oxygenHistory.addCandlesUsed(SOME_DATE, SOME_QUANTITY);
        assertThat(oxygenHistory.find(SOME_DATE)).isPresent();
    }

    @Test
    public void givenEmptyHistory_whenAddingWaterUsed_itCreatesNewEntry() {
        oxygenHistory.addWaterUsed(SOME_DATE, SOME_QUANTITY);
        assertThat(oxygenHistory.find(SOME_DATE)).isPresent();
    }

    @Test
    public void whenAddingTankBought_itAddsTheTankBought() {
        oxygenHistory.addTankBought(SOME_DATE, SOME_QUANTITY);
        OxygenHistoryEntry oxygenHistoryEntry = oxygenHistory.find(SOME_DATE).get();
        assertThat(oxygenHistoryEntry.getTankBought()).isEqualTo(SOME_QUANTITY);
    }

    @Test
    public void whenAddingTankMade_itAddsTheTankMade() {
        oxygenHistory.addTankMade(SOME_DATE, SOME_QUANTITY);
        OxygenHistoryEntry oxygenHistoryEntry = oxygenHistory.find(SOME_DATE).get();
        assertThat(oxygenHistoryEntry.getTankMade()).isEqualTo(SOME_QUANTITY);
    }

    @Test
    public void whenAddingCandlesUsed_itAddsTheCandlesUsed() {
        oxygenHistory.addCandlesUsed(SOME_DATE, SOME_QUANTITY);
        OxygenHistoryEntry oxygenHistoryEntry = oxygenHistory.find(SOME_DATE).get();
        assertThat(oxygenHistoryEntry.getCandlesUsed()).isEqualTo(SOME_QUANTITY);
    }

    @Test
    public void whenAddingWaterUsed_itAddsTheWaterUsed() {
        oxygenHistory.addWaterUsed(SOME_DATE, SOME_QUANTITY);
        OxygenHistoryEntry oxygenHistoryEntry = oxygenHistory.find(SOME_DATE).get();
        assertThat(oxygenHistoryEntry.getWaterUsed()).isEqualTo(SOME_QUANTITY);
    }
}
