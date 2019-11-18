package ca.ulaval.glo4002.booking.domain.oxygen2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class OxygenCalculatorTest {

    @Test
    public void givenMinQuantityisZero_whenCalculatingNumberOfBatchesRequired_itReturnsZero() {
        int numberOfBatches = OxygenCalculator.calculateNumberOfBatchesRequired(0, 5);
        assertThat(numberOfBatches).isEqualTo(0);
    }

    @Test
    public void givenMinQuantityisNegative_whenCalculatingNumberOfBatchesRequired_itReturnsZero() {
        int numberOfBatches = OxygenCalculator.calculateNumberOfBatchesRequired(0, 5);
        assertThat(numberOfBatches).isEqualTo(0);
    }

    @Test
    public void givenMinQuantitySmallerThanBatchSize_whenCalculatingNumberOfBatchesRequired_itReturnsOne() {
        int numberOfBatches = OxygenCalculator.calculateNumberOfBatchesRequired(4, 5);
        assertThat(numberOfBatches).isEqualTo(1);
    }

    @Test
    public void givenMinQuantityEqualToBatchSize_whenCalculatingNumberOfBatchesRequired_itReturnsOne() {
        int numberOfBatches = OxygenCalculator.calculateNumberOfBatchesRequired(5, 5);
        assertThat(numberOfBatches).isEqualTo(1);
    }

    @Test
    public void givenMinQuantityEqualToMultipleOfBatchSize_whenCalculatingNumberOfBatchesRequired_itReturnsTheMultiple() {
        int numberOfBatches = OxygenCalculator.calculateNumberOfBatchesRequired(25, 5);
        assertThat(numberOfBatches).isEqualTo(5);
    }

    @Test
    public void givenMinQuantityBetweenMultiplesOfBatchSize_whenCalculatingNumberOfBatchesRequired_itReturnsTheBiggerMultiple() {
        int numberOfBatches = OxygenCalculator.calculateNumberOfBatchesRequired(23, 5);
        assertThat(numberOfBatches).isEqualTo(5);
    }
}
