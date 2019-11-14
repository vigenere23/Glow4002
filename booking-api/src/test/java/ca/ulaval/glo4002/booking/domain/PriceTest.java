package ca.ulaval.glo4002.booking.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.Test;

public class PriceTest {

    private static final BigDecimal POSITIVE_AMOUNT_WITH_DECIMALS_1 = BigDecimal.valueOf(26.889062);
    private static final BigDecimal POSITIVE_AMOUNT_WITH_DECIMALS_2 = BigDecimal.valueOf(5.23654);

    @Test
    public void givenNormalAmount_whenGettingAmount_itShouldReturnSameAmount() {
        BigDecimal decimalAmount = BigDecimal.valueOf(165.85);
        Price price = new Price(decimalAmount);
        assertEquals(decimalAmount, price.getAmount());
    }

    @Test
    public void givenLongDecimalAmount_whenGettingAmount_itShouldReturnSameAmount() {
        BigDecimal decimalAmount = BigDecimal.valueOf(0.230654065406540);
        Price price = new Price(decimalAmount);

        assertEquals(decimalAmount, price.getAmount());
    }

    @Test
    public void givenNegativeAmount_whenGettingAmount_itShouldReturnSameAmount() {
        BigDecimal decimalAmount = BigDecimal.valueOf(-97.253156);
        Price price = new Price(decimalAmount);

        assertEquals(decimalAmount, price.getAmount());
    }

    @Test
    public void givenLargeAmount_whenGettingAmount_itShouldReturnSameAmount() {
        BigDecimal decimalAmount = BigDecimal.valueOf(564654065);
        Price price = new Price(decimalAmount);

        assertEquals(decimalAmount, price.getAmount());
    }

    @Test
    public void givenDoubleNormalAmount_whenGettingAmount_itShouldReturnBigDecimalEquivalentAmount() {
        double doubleDecimalAmount = 165.85;

        Price price = new Price(doubleDecimalAmount);

        BigDecimal decimalAmount = BigDecimal.valueOf(165.85);
        assertEquals(decimalAmount, price.getAmount());
    }

    @Test
    public void givenDoubleLongDecimalAmount_whenGettingAmount_itShouldReturnBigDecimalEquivalentAmount() {
        double doubleDecimalAmount = 0.230654065406540;
        
        Price price = new Price(doubleDecimalAmount);
        
        BigDecimal decimalAmount = BigDecimal.valueOf(0.230654065406540);
        assertEquals(decimalAmount, price.getAmount());
    }

    @Test
    public void givenDoubleNegativeAmount_whenGettingAmount_itShouldReturnBigDecimalEquivalentAmount() {
        double doubleDecimalAmount = -97.253156;

        Price price = new Price(doubleDecimalAmount);
        
        BigDecimal decimalAmount = BigDecimal.valueOf(-97.253156);
        assertEquals(decimalAmount, price.getAmount());
    }

    @Test
    public void givenDoubleLargeAmount_whenGettingAmount_itShouldReturnBigDecimalEquivalentAmount() {
        double doubleDecimalValue = 564654065;

        Price price = new Price(doubleDecimalValue);

        BigDecimal decimalAmount = BigDecimal.valueOf(564654065);
        assertEquals(decimalAmount, price.getAmount());
    }

    @Test
    public void givenZeroAmount_whenGettingAmount_itShouldBeZero() {
        Price zeroAmountPrice = Price.zero();
        BigDecimal zeroAmount = BigDecimal.valueOf(0.0);

        assertEquals(zeroAmount,zeroAmountPrice.getAmount());
    }

    @Test
    public void givenDecimalAmountBelowHalfUnit_whenGettingRoundedAmount_itReturnsTheFlooredAmount() {
        Price price = new Price(0.004);

        float roundedAmount = price.getRoundedAmount(2);
        float expectedRoundedAmount = 0.0f;

        assertEquals(expectedRoundedAmount, roundedAmount);
    }

    @Test
    public void givenDecimalAmountAtHalfUnit_whenGettingRoundedAmount_itReturnsTheCeiledAmount() {
        Price price = new Price(0.005);

        float roundedAmount = price.getRoundedAmount(2);
        float expectedRoundedAmount = 0.01f;

        assertEquals(expectedRoundedAmount, roundedAmount);
    }

    @Test
    public void givenTwoPositivePrices_whenAdding_itReturnsTheAddedAmounts() {
        Price price1 = new Price(POSITIVE_AMOUNT_WITH_DECIMALS_1);
        Price price2 = new Price(POSITIVE_AMOUNT_WITH_DECIMALS_2);

        Price addedPrices = price1.plus(price2);
        BigDecimal expectedAddedPrices = POSITIVE_AMOUNT_WITH_DECIMALS_1
            .add(POSITIVE_AMOUNT_WITH_DECIMALS_2);

        assertEquals(expectedAddedPrices, addedPrices.getAmount());
    }

    @Test
    public void givenTwoPositivePrices_whenSubtracting_itReturnsTheSubtractedAmounts() {
        Price price1 = new Price(POSITIVE_AMOUNT_WITH_DECIMALS_1);
        Price price2 = new Price(POSITIVE_AMOUNT_WITH_DECIMALS_2);

        Price subtractedPrices = price1.minus(price2);
        BigDecimal expectedSubtractedPrices = POSITIVE_AMOUNT_WITH_DECIMALS_1
            .subtract(POSITIVE_AMOUNT_WITH_DECIMALS_2);

        assertEquals(expectedSubtractedPrices, subtractedPrices.getAmount());
    }

    @Test
    public void givenAPositivePrice_whenMultiplyingByDouble_itReturnsNewInstanceWithMultipliedAmount() {
        double multiplicator = 0.176;    
        Price price = new Price(POSITIVE_AMOUNT_WITH_DECIMALS_1);    

        Price multipliedPrice = price.multipliedBy(multiplicator);    
        BigDecimal expectedMultipliedPrice = POSITIVE_AMOUNT_WITH_DECIMALS_1    
            .multiply(BigDecimal.valueOf(multiplicator));    

        assertEquals(expectedMultipliedPrice, multipliedPrice.getAmount());    
    }

    @Test
    public void givenAPositivePrice_whenMultiplyingByInteger_itReturnsTheMultipliedAmount() {
        double multiplicator = 3;    
        Price price = new Price(POSITIVE_AMOUNT_WITH_DECIMALS_1);    

        Price multipliedPrice = price.multipliedBy(multiplicator);    
        BigDecimal expectedMultipliedPrice = POSITIVE_AMOUNT_WITH_DECIMALS_1    
            .multiply(BigDecimal.valueOf(multiplicator));    

        assertEquals(expectedMultipliedPrice, multipliedPrice.getAmount());    
    }

    @Test
    public void givenAPositivePrice_whenDividingByScalar_itReturnsTheDividedAmount() {
        double divisor = 5.25;
        Price price = new Price(POSITIVE_AMOUNT_WITH_DECIMALS_1);

        Price dividedPrice = price.dividedBy(divisor);
        BigDecimal expectedDividedPrice = POSITIVE_AMOUNT_WITH_DECIMALS_1
            .divide(BigDecimal.valueOf(divisor), RoundingMode.HALF_UP);

        assertEquals(expectedDividedPrice, dividedPrice.getAmount());    
    }
}
