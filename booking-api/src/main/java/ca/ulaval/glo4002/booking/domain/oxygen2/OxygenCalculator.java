package ca.ulaval.glo4002.booking.domain.oxygen2;

public class OxygenCalculator {

    public static int calculateNumberOfBatchesRequired(int minQuantityToProduce, int batchSize) {
        return minQuantityToProduce % batchSize + 1;
    }
}
