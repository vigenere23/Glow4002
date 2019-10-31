package ca.ulaval.glo4002.booking.domain.orders.discounts;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.NebulaSinglePass;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class NebulaSinglePassDiscountTests {

    private static final int NEBULA_SINGLE_PASS_DISCOUNT_QUANTITY = 4;
    private static final Price PRICE_WITHOUT_DISCOUNT = new Price(50000);
    private static final double PERCENTAGE_DISCOUNT = 0.1;

    private NebulaSinglePassDiscount nebulaSinglePassDiscount;
    private List<Pass> passes;

    @BeforeEach
    public void setUp() {
        nebulaSinglePassDiscount = new NebulaSinglePassDiscount();
        passes = new ArrayList<Pass>();
    }
    
    @Test
    public void givenTwoNebulaPasses_thenThereIsNoDiscount() {
        initPasses(2);
        assertThat(getPriceAfterDiscount().getAmount()).isEqualTo(PRICE_WITHOUT_DISCOUNT.getAmount());
    }

    @Test
    public void givenFourNebulaPasses_whenCalculatingPrice_thenItReturnsATenPercentDiscount() {
        initPasses(NEBULA_SINGLE_PASS_DISCOUNT_QUANTITY);
        Price expectedPriceAfterDiscount = PRICE_WITHOUT_DISCOUNT.multipliedBy(1 - PERCENTAGE_DISCOUNT);
        assertThat(getPriceAfterDiscount().getAmount()).isEqualTo(expectedPriceAfterDiscount.getAmount());
    }
    
    private void initPasses(int numberOfPasses) {
        for (int i = 0; i < numberOfPasses; i++) {
            passes.add(mock(NebulaSinglePass.class));
        }
    }

    private Price getPriceAfterDiscount() {
        return nebulaSinglePassDiscount.priceAfterDiscounts(passes, PRICE_WITHOUT_DISCOUNT);
    }
}
