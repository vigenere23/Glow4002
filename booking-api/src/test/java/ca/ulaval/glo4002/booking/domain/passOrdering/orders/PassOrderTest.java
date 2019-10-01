package ca.ulaval.glo4002.booking.domain.passOrdering.orders;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories.SinglePassFactory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.NebulaSinglePass;

public class PassOrderTest {

	private SinglePassFactory singlePassFactory;
	private static final int NEBULA_SINGLE_PASS_DISCOUNT_QUANTITY = 4;
	private static final int SUPERGIANT_SINGLE_PASS_DISCOUNT_QUANTITY = 5;

	@BeforeEach
	public void setUp() {
		this.singlePassFactory = new SinglePassFactory();
	}

	@Test
	public void whenCreatingOrderWithNoPasses_thenThePriceShouldBe0() {
		PassOrder passOrder = new PassOrder(OffsetDateTime.now(), "CODE", new ArrayList<Pass>());
		assertThat(passOrder.getPrice()).isEqualTo(Money.zero(CurrencyUnit.CAD));
	}

	@Test
	public void whenCreatingOrderWith2NebulaSinglePasses_thenThePriceShouldBe2timesNebulaSinglePrice() {
		Money nebulaSinglePrice = new NebulaSinglePass(OffsetDateTime.now()).getPrice();

		List<Pass> passes = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			passes.add(singlePassFactory.create(PassCategory.NEBULA, OffsetDateTime.now()));
		}

		PassOrder passOrder = new PassOrder(OffsetDateTime.now(), "CODE", passes);
		assertThat(passOrder.getPrice()).isEqualTo(nebulaSinglePrice.multipliedBy(2));
	}

	@Test
	public void givenOver3NebulaPasses_whenSinglePassOrderCreated_itShouldHaveDiscountOnTotalPrice() {
		List<Pass> passes = new ArrayList<>();

		for (int i = 0; i < NEBULA_SINGLE_PASS_DISCOUNT_QUANTITY; i++) {
			passes.add(singlePassFactory.create(PassCategory.NEBULA, OffsetDateTime.now()));
		}

		PassOrder passOrder = new PassOrder(OffsetDateTime.now(), "CODE", passes);

		Money priceBeforeDiscount = Money.of(CurrencyUnit.CAD, 50000 * NEBULA_SINGLE_PASS_DISCOUNT_QUANTITY);
		Money discount = Money.of(CurrencyUnit.CAD, 20000);
		Money priceAfterDiscount = priceBeforeDiscount.minus(discount);

		assertThat(passOrder.getPrice().getAmount()).isEqualTo(priceAfterDiscount.getAmount());
	}

	@Test
	public void givenAtLeast5SupergiantPasses_whenSinglePassOrderCreated_itShouldHaveDiscountOnEachPass() {
		List<Pass> passes = new ArrayList<>();

		for (int i = 0; i < SUPERGIANT_SINGLE_PASS_DISCOUNT_QUANTITY; i++) {
			passes.add(singlePassFactory.create(PassCategory.SUPERGIANT, OffsetDateTime.now()));
		}

		PassOrder passOrder = new PassOrder(OffsetDateTime.now(), "CODE", passes);

		Money priceAfterDiscount = Money.of(CurrencyUnit.CAD, 90000 * SUPERGIANT_SINGLE_PASS_DISCOUNT_QUANTITY);

		assertThat(passOrder.getPrice().getAmount()).isEqualTo(priceAfterDiscount.getAmount());
	}
}
