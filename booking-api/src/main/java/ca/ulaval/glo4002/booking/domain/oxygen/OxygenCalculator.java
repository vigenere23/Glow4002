package ca.ulaval.glo4002.booking.domain.oxygen;

public class OxygenCalculator {

    public static int calculateNumberOfBatchesRequired(int minQuantityToProduce, int batchSize) {
        if (batchSize < 1) throw new IllegalArgumentException("batch size must be > 0");
        if (minQuantityToProduce < 1) return 0;

        int numberOfBatches = minQuantityToProduce / batchSize;
        return minQuantityToProduce % batchSize == 0
            ? numberOfBatches
            : numberOfBatches + 1;
    }
}
