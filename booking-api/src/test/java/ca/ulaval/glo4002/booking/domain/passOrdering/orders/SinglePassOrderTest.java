package ca.ulaval.glo4002.booking.domain.passOrdering.orders;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.passOrdering.orders.OrderFactory;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassOption;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories.SinglePassFactory;

public class SinglePassOrderTest {

	private SinglePassFactory singlePassFactory;
	private OrderFactory orderFactory;

	private PassOrder singleNebulaPassOrder;
	private PassOrder singleSupergiantPassOrder;

	private List<OffsetDateTime> eventDates;
	private int nebulaDiscountPassesNumber = 4;
	private int supergiantDiscountPassesNumber = 5;

	@BeforeEach
	public void setUp() {
		orderFactory = new OrderFactory();
		singlePassFactory = new SinglePassFactory();
		eventDates = new ArrayList<>();

		singleNebulaPassOrder = orderFactory.createOrder(PassOption.SINGLE_PASS, PassCategory.NEBULA, eventDates);
		singleSupergiantPassOrder = orderFactory.createOrder(PassOption.SINGLE_PASS, PassCategory.SUPERGIANT, eventDates);	
	}

	@Test
	public void givenOver3NebulaPasses_whenSinglePassOrderCreated_itShouldHaveDiscountOnTotalPrice() {
		for (int i = 0; i < nebulaDiscountPassesNumber; i++) {
			singleNebulaPassOrder.passes.add(singlePassFactory.create(PassCategory.NEBULA, OffsetDateTime.now()));
		}

		Money priceBeforeDiscount = Money.of(CurrencyUnit.CAD, 50000 * nebulaDiscountPassesNumber);
		Money discount = Money.of(CurrencyUnit.CAD, 20000);
		Money priceAfterDiscount = priceBeforeDiscount.minus(discount);

		assertThat(singleNebulaPassOrder.getPrice().getAmount()).isEqualTo(priceAfterDiscount.getAmount());
	}

	@Test
	public void givenAtLeast5SupergiantPasses_whenSinglePassOrderCreated_itShouldHaveDiscountOnEachPass() {

		for (int i = 0; i < supergiantDiscountPassesNumber; i++) {
			singleSupergiantPassOrder
				.passes
				.add(singlePassFactory.create(PassCategory.SUPERGIANT, OffsetDateTime.now()));
		}

		Money priceAfterDiscount = Money.of(CurrencyUnit.CAD, 90000 * supergiantDiscountPassesNumber);

		assertThat(singleSupergiantPassOrder.getPrice().getAmount()).isEqualTo(priceAfterDiscount.getAmount());
	}
}
