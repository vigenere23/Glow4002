package ca.ulaval.glo4002.booking.domain.oxygen2.history;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OxygenHistoryEntryTest {

    private static final LocalDate SOME_DATE = LocalDate.now();

    private OxygenHistoryEntry oxygenHistoryEntry;

    @BeforeEach
    public void setup() {
        oxygenHistoryEntry = new OxygenHistoryEntry(SOME_DATE);
    }

    @Test
    public void givenADate_whenCreating_ItSetsTheDate() {
        LocalDate date = LocalDate.now();
        oxygenHistoryEntry = new OxygenHistoryEntry(date);
        assertThat(oxygenHistoryEntry.getDate()).isEqualTo(date);
    }

    @Test
    public void whenCreating_itSetsTheTankBoughtToZero() {
        oxygenHistoryEntry = new OxygenHistoryEntry(SOME_DATE);
        assertThat(oxygenHistoryEntry.getTankBought()).isZero();
    }

    @Test
    public void whenCreating_itSetsTheTanksMadeAtZero() {
        oxygenHistoryEntry = new OxygenHistoryEntry(SOME_DATE);
        assertThat(oxygenHistoryEntry.getTankMade()).isZero();
    }

    @Test
    public void whenCreating_itSetsTheWaterUsedAtZero() {
        oxygenHistoryEntry = new OxygenHistoryEntry(SOME_DATE);
        assertThat(oxygenHistoryEntry.getWaterUsed()).isZero();
    }

    @Test
    public void whenCreating_itSetsTheCandlesUsedAtZero() {
        oxygenHistoryEntry = new OxygenHistoryEntry(SOME_DATE);
        assertThat(oxygenHistoryEntry.getCandlesUsed()).isZero();
    }

    @Test
    public void whenAddingTankBought_itAddsTheTankBought() {
        int tankBought = 14;
        oxygenHistoryEntry.addTankBought(tankBought);
        assertThat(oxygenHistoryEntry.getTankBought()).isEqualTo(tankBought);
    }

    @Test
    public void whenAddingTankMade_itAddsTheTankMade() {
        int tankMade = 14;
        oxygenHistoryEntry.addTankMade(tankMade);
        assertThat(oxygenHistoryEntry.getTankMade()).isEqualTo(tankMade);
    }

    @Test
    public void whenAddingCandlesUsed_itAddsTheCandleUsed() {
        int candlesUsed = 14;
        oxygenHistoryEntry.addCandlesUsed(candlesUsed);
        assertThat(oxygenHistoryEntry.getCandlesUsed()).isEqualTo(candlesUsed);
    }

    @Test
    public void whenAddingWaterUsed_itAddsTheWaterUsed() {
        int waterUsed = 14;
        oxygenHistoryEntry.addWaterUsed(waterUsed);
        assertThat(oxygenHistoryEntry.getWaterUsed()).isEqualTo(waterUsed);
    }
}
