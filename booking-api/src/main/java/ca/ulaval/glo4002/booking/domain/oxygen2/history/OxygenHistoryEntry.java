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

    public void addCandlesUsed(int candlesUsed) {
        validateNonNegativeQuantity(candlesUsed);
        this.candlesUsed += candlesUsed;
    }

    public void addWaterUsed(int waterUsed) {
        validateNonNegativeQuantity(waterUsed);
        this.waterUsed += waterUsed;
    }

    public void addTankMade(int tankMade) {
        validateNonNegativeQuantity(tankMade);
        this.tankMade += tankMade;
    }

    public void addTankBought(int tankBought) {
        validateNonNegativeQuantity(tankBought);
        this.tankBought += tankBought;
    }

    private void validateNonNegativeQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("quantity cannot be negative");
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public int getCandlesUsed() {
        return candlesUsed;
    }

    public int getWaterUsed() {
        return waterUsed;
    }

    public int getTankMade() {
        return tankMade;
    }

    public int getTankBought() {
        return tankBought;
    }
}
