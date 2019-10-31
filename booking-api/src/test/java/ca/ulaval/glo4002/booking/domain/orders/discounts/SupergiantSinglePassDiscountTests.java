package ca.ulaval.glo4002.booking.domain.orders.discounts;

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

public class SupergiantSinglePassDiscountTests {

	private static final int SUPERGIANT_SINGLE_PASS_DISCOUNT_QUANTITY = 5;
	private static final Money PRICE_WITHOUT_DISCOUNT = Money.of(CurrencyUnit.CAD, 50000);
	private static final Money DISCOUNT_PER_PASS = Money.of(CurrencyUnit.CAD, 10000);

	private SupergiantSinglePassDiscount supergiantSinglePassDiscount;
	private List<Pass> passes;

	@BeforeEach
	public void setUp() {
		supergiantSinglePassDiscount = new SupergiantSinglePassDiscount();
		passes = new ArrayList<Pass>();
	}
	
	@Test
	public void givenTwoSupergiantPasses_thenThereIsNoDiscount() {
		initPasses(2);
		assertThat(getPriceAfterDiscount()).isEqualTo(PRICE_WITHOUT_DISCOUNT);
	}

	@Test
	public void givenFourSupergiantPasses_whenCalculatingPrice_thenItReturnsADiscount() {
		initPasses(SUPERGIANT_SINGLE_PASS_DISCOUNT_QUANTITY);
		Money expectedPriceAfterDiscount = PRICE_WITHOUT_DISCOUNT.minus(DISCOUNT_PER_PASS.multipliedBy(SUPERGIANT_SINGLE_PASS_DISCOUNT_QUANTITY));
		assertThat(getPriceAfterDiscount()).isEqualTo(expectedPriceAfterDiscount);
	}
	
	private void initPasses(int numberOfPasses) {
		Pass pass = mock(Pass.class);
		when(pass.getPassOption()).thenReturn(PassOption.SINGLE_PASS);
		when(pass.getPassCategory()).thenReturn(PassCategory.SUPERGIANT);
		
		for (int i = 0; i < numberOfPasses; i++) {
			passes.add(pass);
		}
	}

	private Money getPriceAfterDiscount() {
		return supergiantSinglePassDiscount.priceAfterDiscounts(passes, PRICE_WITHOUT_DISCOUNT);
	}
}
