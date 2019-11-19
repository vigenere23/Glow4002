package ca.ulaval.glo4002.booking.domain.oxygen2.history;

import java.time.LocalDate;

public class OxygenHistoryEntry {

    private LocalDate date;
    private int candlesUsed;
    private int waterUsed;
    private int tankMade;
    private int tankBought;

    public OxygenHistoryEntry(LocalDate date) {
        this.date = date;

        candlesUsed = 0;
        waterUsed = 0;
        tankMade = 0;
        tankBought = 0;
    }

    public void addTankBought(int tankBought) {
        assert tankBought >= 0 : "tanks bought cannot be negative";
        this.tankBought += tankBought;
    }

    public void addTankMade(int tankMade) {
        assert tankMade >= 0 : "tanks made cannot be negative";
        this.tankMade += tankMade;
    }

    public void addCandlesUsed(int candlesUsed) {
        assert candlesUsed >= 0 : "candles used cannot be negative";
        this.candlesUsed += candlesUsed;
    }

    public void addWaterUsed(int waterUsed) {
        assert waterUsed >= 0 : "water used cannot be negative";
        this.waterUsed += waterUsed;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getTankBought() {
        return tankBought;
    }

    public int getTankMade() {
        return tankMade;
    }

    public int getCandlesUsed() {
        return candlesUsed;
    }

    public int getWaterUsed() {
        return waterUsed;
    }
}
