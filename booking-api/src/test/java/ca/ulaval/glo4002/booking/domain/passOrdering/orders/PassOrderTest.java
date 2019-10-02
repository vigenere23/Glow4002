package ca.ulaval.glo4002.booking.domain.passOrdering.orders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.NebulaSinglePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.SupergiantSinglePass;

public class PassOrderTest {

	private static final int NEBULA_SINGLE_PASS_DISCOUNT_QUANTITY = 4;
	private static final Money NEBULA_SINGLE_PASS_PRICE = Money.of(CurrencyUnit.CAD, 50000);
	private static final int SUPERGIANT_SINGLE_PASS_DISCOUNT_QUANTITY = 5;
	private static final Money SUPERGIANT_SINGLE_PASS_PRICE = Money.of(CurrencyUnit.CAD, 100000);
	private static final Money SUPERGIANT_SINGLE_PASS_DISCOUNTED_PRICE = Money.of(CurrencyUnit.CAD, 90000);

	@Test
	public void whenCreatingOrderWithNoPasses_thenThePriceShouldBeZero() {
		PassOrder passOrder = new PassOrder(OffsetDateTime.now(), "CODE", new ArrayList<Pass>());
		assertThat(passOrder.getPrice()).isEqualTo(Money.zero(CurrencyUnit.CAD));
	}

	@Test
	public void whenCreatingOrderWithOnePass_thenThePriceShouldBeThePassPrice() {
		Pass passMock = mock(NebulaSinglePass.class);
		when(passMock.getPrice()).thenReturn(NEBULA_SINGLE_PASS_PRICE);

		Pass pass = new NebulaSinglePass(OffsetDateTime.now());

		List<Pass> passes = Arrays.asList(pass);

		PassOrder passOrder = new PassOrder(OffsetDateTime.now(), "CODE", passes);
		assertThat(passOrder.getPrice()).isEqualTo(NEBULA_SINGLE_PASS_PRICE);
	}

	@Test
	public void whenCreatingOrderWithTwoPasses_thenThePriceShouldBeDoubleThePassPrice() {
		Pass passMock = mock(NebulaSinglePass.class);
		when(passMock.getPrice()).thenReturn(NEBULA_SINGLE_PASS_PRICE);

		List<Pass> passes = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			passes.add(new NebulaSinglePass(OffsetDateTime.now()));
		}

		PassOrder passOrder = new PassOrder(OffsetDateTime.now(), "CODE", passes);
		assertThat(passOrder.getPrice()).isEqualTo(NEBULA_SINGLE_PASS_PRICE.multipliedBy(2));
	}

	@Test
	public void givenOverThreeNebulaPasses_whenSinglePassOrderCreated_itShouldHaveTenPercentDiscount() {
		Pass passMock = mock(NebulaSinglePass.class);
		when(passMock.getPrice()).thenReturn(NEBULA_SINGLE_PASS_PRICE);

		List<Pass> passes = new ArrayList<>();
		for (int i = 0; i < NEBULA_SINGLE_PASS_DISCOUNT_QUANTITY; i++) {
			passes.add(new NebulaSinglePass(OffsetDateTime.now()));
		}

		PassOrder passOrder = new PassOrder(OffsetDateTime.now(), "CODE", passes);

		Money priceBeforeDiscount = NEBULA_SINGLE_PASS_PRICE.multipliedBy(NEBULA_SINGLE_PASS_DISCOUNT_QUANTITY);
		Money priceAfterDiscount = priceBeforeDiscount.multipliedBy(0.9, RoundingMode.HALF_UP);

		assertThat(passOrder.getPrice()).isEqualTo(priceAfterDiscount);
	}

	@Test
	public void givenAtLeastFiveSupergiantPasses_whenSinglePassOrderCreated_itShouldHaveDiscountOnEachPass() {
		Pass passMock = mock(SupergiantSinglePass.class);
		when(passMock.getPrice()).thenReturn(SUPERGIANT_SINGLE_PASS_PRICE);

		List<Pass> passes = new ArrayList<>();
		for (int i = 0; i < SUPERGIANT_SINGLE_PASS_DISCOUNT_QUANTITY; i++) {
			passes.add(new SupergiantSinglePass(OffsetDateTime.now()));
		}

		PassOrder passOrder = new PassOrder(OffsetDateTime.now(), "CODE", passes);

		Money priceAfterDiscount = SUPERGIANT_SINGLE_PASS_DISCOUNTED_PRICE.multipliedBy(SUPERGIANT_SINGLE_PASS_DISCOUNT_QUANTITY);

		assertThat(passOrder.getPrice()).isEqualTo(priceAfterDiscount);
	}
}
