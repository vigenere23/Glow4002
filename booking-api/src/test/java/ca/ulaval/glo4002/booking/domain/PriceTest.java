package ca.ulaval.glo4002.booking.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.Test;

public class PriceTest {

    private static final BigDecimal POSITIVE_AMOUNT_WITH_DECIMALS_1 = BigDecimal.valueOf(26.889062);
    private static final BigDecimal POSITIVE_AMOUNT_WITH_DECIMALS_2 = BigDecimal.valueOf(5.23654);

    @Test
    public void givenDecimalAmount_whenGettingAmount_itShouldReturnSameDecimalAmount() {
        BigDecimal decimalAmount = BigDecimal.valueOf(0.230654065406540);
        Price price = new Price(decimalAmount);
        assertThat(price.getAmount()).isEqualTo(decimalAmount);
    }

    @Test
    public void givenNegativeAmount_whenGettingAmount_itShouldReturnSameNegativeAmount() {
        BigDecimal decimalAmount = BigDecimal.valueOf(-97.253156);
        Price price = new Price(decimalAmount);
        assertThat(price.getAmount()).isEqualTo(decimalAmount);
    }

    @Test
    public void givenLargeAmount_whenGettingAmount_itShouldReturnSameLargeAmount() {
        BigDecimal decimalAmount = BigDecimal.valueOf(564654065);
        Price price = new Price(decimalAmount);
        assertThat(price.getAmount()).isEqualTo(decimalAmount);
    }

    @Test
    public void givenZeroAmount_whenGettingAmount_itShouldBeZero() {
        Price zeroAmountPrice = Price.zero();
        BigDecimal zeroAmount = BigDecimal.valueOf(0.0);
        assertThat(zeroAmountPrice.getAmount()).isEqualTo(zeroAmount);
    }

    @Test
    public void givenDecimalAmountBelowHalfUnit_whenGettingRoundedAmount_itFloorsTheAmount() {
        Price price = new Price(0.004);

        double roundedAmount = price.getRoundedAmount(2);
        double expectedRoundedAmount = 0.0;

        assertThat(roundedAmount).isEqualTo(expectedRoundedAmount);
    }

    @Test
    public void givenDecimalAmountAtHalfUnit_whenGettingRoundedAmount_itCeilsTheAmount() {
        Price price = new Price(0.005);

        double roundedAmount = price.getRoundedAmount(2);
        double expectedRoundedAmount = 0.01;

        assertThat(roundedAmount).isEqualTo(expectedRoundedAmount);
    }

    @Test
    public void givenTwoPositivePrices_whenAdditioning_itReturnsTheAdditionedAmounts() {
        Price price1 = new Price(POSITIVE_AMOUNT_WITH_DECIMALS_1);
        Price price2 = new Price(POSITIVE_AMOUNT_WITH_DECIMALS_2);

        Price additionedPrices = price1.plus(price2);
        BigDecimal expectedAdditionedPrices = POSITIVE_AMOUNT_WITH_DECIMALS_1
            .add(POSITIVE_AMOUNT_WITH_DECIMALS_2);

        assertThat(additionedPrices.getAmount()).isEqualTo(expectedAdditionedPrices);
    }

    @Test
    public void givenTwoPositivePrices_whenSubstracting_itReturnsTheSubstractedAmounts() {
        Price price1 = new Price(POSITIVE_AMOUNT_WITH_DECIMALS_1);
        Price price2 = new Price(POSITIVE_AMOUNT_WITH_DECIMALS_2);

        Price substractedPrices = price1.minus(price2);
        BigDecimal expectedSubstractedPrices = POSITIVE_AMOUNT_WITH_DECIMALS_1
            .subtract(POSITIVE_AMOUNT_WITH_DECIMALS_2);

        assertThat(substractedPrices.getAmount()).isEqualTo(expectedSubstractedPrices);
    }

    @Test
    public void givenAPositivePrice_whenMultiplying_itReturnsTheMultipliedAmount() {
        double multiplicator = 0.176;
        Price price = new Price(POSITIVE_AMOUNT_WITH_DECIMALS_1);

        Price multipliedPrice = price.multipliedBy(multiplicator);
        BigDecimal expectedMultipliedPrice = POSITIVE_AMOUNT_WITH_DECIMALS_1
            .multiply(BigDecimal.valueOf(multiplicator));

        assertThat(multipliedPrice.getAmount()).isEqualTo(expectedMultipliedPrice);
    }

    @Test
    public void givenAPositivePrice_whenDividingByScalar_itReturnsTheDividedAmount() {
        double divisor = 5.25;
        Price price = new Price(POSITIVE_AMOUNT_WITH_DECIMALS_1);

        Price dividedPrice = price.dividedBy(divisor);
        BigDecimal expectedDividedPrice = POSITIVE_AMOUNT_WITH_DECIMALS_1
            .divide(BigDecimal.valueOf(divisor), RoundingMode.HALF_UP);

        assertThat(dividedPrice.getAmount()).isEqualTo(expectedDividedPrice);
    }
}
