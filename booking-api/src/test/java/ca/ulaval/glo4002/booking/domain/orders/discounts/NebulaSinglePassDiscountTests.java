package ca.ulaval.glo4002.booking.domain.orders.discounts;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NebulaSinglePassDiscountTests {

    private static final int NEBULA_SINGLE_PASS_DISCOUNT_QUANTITY = 4;
    private static final Price PRICE_WITHOUT_DISCOUNT = new Price(1000000);
    private static final double PERCENTAGE_DISCOUNT = 0.1;

    private NebulaSinglePassDiscount nebulaSinglePassDiscount;
    private List<Pass> passes;

    @BeforeEach
    public void setUpNebulaSinglePassDiscount() {
        nebulaSinglePassDiscount = new NebulaSinglePassDiscount();
        passes = new ArrayList<>();
    }
    
    @Test
    public void givenTwoNebulaPasses_thenThereIsNoDiscount() {
        initializePassesWithMockPass(2);
        assertEquals(PRICE_WITHOUT_DISCOUNT, getPriceAfterDiscount());
    }

    @Test
    public void givenFourNebulaPasses_whenCalculatingPrice_thenItReturnsATenPercentDiscount() {
        initializePassesWithMockPass(NEBULA_SINGLE_PASS_DISCOUNT_QUANTITY);
        Price expectedPriceAfterDiscount = PRICE_WITHOUT_DISCOUNT.multipliedBy(1 - PERCENTAGE_DISCOUNT);
        assertEquals(expectedPriceAfterDiscount, getPriceAfterDiscount());
    }
    
    private void initializePassesWithMockPass(int numberOfPasses) {
        Pass pass = mock(Pass.class);
        when(pass.isOfType(PassOption.SINGLE_PASS, PassCategory.NEBULA)).thenReturn(true);

        for (int i = 0; i < numberOfPasses; i++) {
            passes.add(pass);
        }
    }

    private Price getPriceAfterDiscount() {
        return nebulaSinglePassDiscount.getPriceAfterDiscounts(passes, PRICE_WITHOUT_DISCOUNT);
    }
}
