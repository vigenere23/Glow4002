package ca.ulaval.glo4002.booking.domain.passOrdering.orders;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
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

	private List<LocalDateTime> eventDates;

	@BeforeEach
	public void setUp() {
		orderFactory = new OrderFactory();
		eventDates = new ArrayList<>();
		singleNebulaPassOrder = orderFactory.createOrder(
			singlePassFactory, PassOption.SINGLE_PASS, PassCategory.NEBULA,eventDates
		);
		singleSupergiantPassOrder = orderFactory.createOrder(
			singlePassFactory, PassOption.SINGLE_PASS, PassCategory.SUPERGIANT, eventDates
		);
		singlePassFactory = new SinglePassFactory();
	}

	@Test
	public void singlePassOrderWithOver3NebulaPassesShouldHaveRebateOnTotalPrice() {
		for (int i = 0; i < 4; i++) {
			singleNebulaPassOrder.passes.add(singlePassFactory.create(PassCategory.NEBULA, LocalDateTime.now()));
		}

		Money rabais = Money.of(CurrencyUnit.CAD, 20000);
		singleNebulaPassOrder.updateTotalPrice();

		assertTrue(singleNebulaPassOrder.calculateRebates().compareTo(rabais) == 0);
	}

	@Test
	public void singlePassOrderWith5SupergiantPassesShouldHaveRebateOnEachPass() {

		for (int i = 0; i < 5; i++) {
			singleSupergiantPassOrder
				.passes
				.add(singlePassFactory.create(PassCategory.SUPERGIANT, LocalDateTime.now()));
		}

		Money rabais = Money.of(CurrencyUnit.CAD, 50000);
		singleSupergiantPassOrder.updateTotalPrice();

		assertTrue(singleSupergiantPassOrder.calculateRebates().compareTo(rabais) == 0);
	}
}
