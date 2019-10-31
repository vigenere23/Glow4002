package ca.ulaval.glo4002.booking.domain.orders.discounts;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NebulaSinglePassDiscountTests {

    private static final int NEBULA_SINGLE_PASS_DISCOUNT_QUANTITY = 4;
    private static final Money PRICE_WITHOUT_DISCOUNT = Money.of(CurrencyUnit.CAD, 50000);
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
        assertThat(getPriceAfterDiscount()).isEqualTo(PRICE_WITHOUT_DISCOUNT);
    }

    @Test
    public void givenFourNebulaPasses_whenCalculatingPrice_thenItReturnsATenPercentDiscount() {
        initPasses(NEBULA_SINGLE_PASS_DISCOUNT_QUANTITY);
        Money expectedPriceAfterDiscount = PRICE_WITHOUT_DISCOUNT.multipliedBy(1 - PERCENTAGE_DISCOUNT, RoundingMode.HALF_UP);
        assertThat(getPriceAfterDiscount()).isEqualTo(expectedPriceAfterDiscount);
    }
    
    private void initPasses(int numberOfPasses) {
        Pass pass = mock(Pass.class);
		when(pass.getPassOption()).thenReturn(PassOption.SINGLE_PASS);
        when(pass.getPassCategory()).thenReturn(PassCategory.NEBULA);

        for (int i = 0; i < numberOfPasses; i++) {
            passes.add(pass);
        }
    }

    private Money getPriceAfterDiscount() {
        return nebulaSinglePassDiscount.priceAfterDiscounts(passes, PRICE_WITHOUT_DISCOUNT);
    }
}
