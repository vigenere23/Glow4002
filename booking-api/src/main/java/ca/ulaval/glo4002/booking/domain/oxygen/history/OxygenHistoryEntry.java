package ca.ulaval.glo4002.booking.domain.oxygen.history;

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
        if (tankBought < 0) {
            throw new IllegalArgumentException("tanks bought must be positive");
        }

        this.tankBought += tankBought;
    }

    public void addTankMade(int tankMade) {
        if (tankMade < 0) {
            throw new IllegalArgumentException("tanks made must be positive");
        }

        this.tankMade += tankMade;
    }

    public void addCandlesUsed(int candlesUsed) {
        if (candlesUsed < 0) {
            throw new IllegalArgumentException("candles used must be positive");
        }

        this.candlesUsed += candlesUsed;
    }

    public void addWaterUsed(int waterUsed) {
        if (waterUsed < 0) {
            throw new IllegalArgumentException("water used must be positive");
        }

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
