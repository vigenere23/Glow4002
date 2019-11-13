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

public class SupergiantSinglePassDiscountTests {

    private static final int SUPERGIANT_SINGLE_PASS_DISCOUNT_QUANTITY = 5;
    private static final Price PRICE_WITHOUT_DISCOUNT = new Price(1000000);
    private static final Price DISCOUNT_PER_PASS = new Price(10000);

    private SupergiantSinglePassDiscount supergiantSinglePassDiscount;
    private List<Pass> passes;

    @BeforeEach
    public void setUpSuperGriantSinglePassDiscount() {
        supergiantSinglePassDiscount = new SupergiantSinglePassDiscount();
        passes = new ArrayList<>();
    }
    
    @Test
    public void givenTwoSupergiantPasses_thenThereIsNoDiscount() {
        initPasses(2);
        assertEquals(PRICE_WITHOUT_DISCOUNT, getPriceAfterDiscount());
    }

    @Test
    public void givenFourSupergiantPasses_whenCalculatingPrice_thenItReturnsADiscount() {
        initPasses(SUPERGIANT_SINGLE_PASS_DISCOUNT_QUANTITY);
        Price expectedPriceAfterDiscount = PRICE_WITHOUT_DISCOUNT.minus(DISCOUNT_PER_PASS.multipliedBy(SUPERGIANT_SINGLE_PASS_DISCOUNT_QUANTITY));
        assertEquals(expectedPriceAfterDiscount, getPriceAfterDiscount());
    }
    
    private void initPasses(int numberOfPasses) {
        Pass pass = mock(Pass.class);
        when(pass.isOfType(PassOption.SINGLE_PASS, PassCategory.SUPERGIANT)).thenReturn(true);
        
        for (int i = 0; i < numberOfPasses; i++) {
            passes.add(pass);
        }
    }

    private Price getPriceAfterDiscount() {
        return supergiantSinglePassDiscount.getPriceAfterDiscounts(passes, PRICE_WITHOUT_DISCOUNT);
    }
}
