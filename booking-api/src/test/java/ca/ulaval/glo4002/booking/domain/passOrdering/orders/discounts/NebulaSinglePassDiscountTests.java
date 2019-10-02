package ca.ulaval.glo4002.booking.domain.passOrdering.orders.discounts;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.NebulaSinglePass;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class NebulaSinglePassDiscountTests {

    private static final int NEBULA_SINGLE_PASS_DISCOUNT_QUANTITY = 4;
    private static final Money PRICE_WITHOUT_DISCOUNT = Money.of(CurrencyUnit.CAD, 50000);
    private static final double PERCENTAGE_DISCOUNT = 0.1;

    private NebulaSinglePassDiscount nebulaSinglePassDiscount;
    private List<Pass> passes;

    private void initPasses(int numberOfPasses) {
        for (int i = 0; i < numberOfPasses; i++) {
            this.passes.add(mock(NebulaSinglePass.class));
        }
    }

    private Money getPriceAfterDiscount() {
        return this.nebulaSinglePassDiscount.priceAfterDiscounts(this.passes, PRICE_WITHOUT_DISCOUNT);
    }

    @BeforeEach
    public void setUp() {
        this.nebulaSinglePassDiscount = new NebulaSinglePassDiscount();
        this.passes = new ArrayList<Pass>();
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
}
