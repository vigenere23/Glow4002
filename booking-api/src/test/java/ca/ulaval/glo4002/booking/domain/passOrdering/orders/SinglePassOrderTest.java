package ca.ulaval.glo4002.booking.domain.passOrdering.orders;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
	private int nebulaPassesNumber = 4;
	private int supergiantPassesNumber = 5;

	@BeforeEach
	public void setUp() {
		orderFactory = new OrderFactory();
		singlePassFactory = new SinglePassFactory();
		eventDates = new ArrayList<>();

		singleNebulaPassOrder = orderFactory.createOrder(PassOption.SINGLE_PASS, PassCategory.NEBULA, eventDates);
		singleSupergiantPassOrder = orderFactory.createOrder(PassOption.SINGLE_PASS, PassCategory.SUPERGIANT, eventDates);	
	}

	@Test
	public void givenOver3NebulaPasses_whenSinglePassOrderCreated_itShouldHaveRebateOnTotalPrice() {
		for (int i = 0; i < nebulaPassesNumber; i++) {
			singleNebulaPassOrder.passes.add(singlePassFactory.create(PassCategory.NEBULA, OffsetDateTime.now()));
		}

		Money rabais = Money.of(CurrencyUnit.CAD, 20000);
		singleNebulaPassOrder.updateTotalPrice();

		assertTrue(singleNebulaPassOrder.calculateRebates().compareTo(rabais) == 0);
	}

	@Test
	public void givenAtLeast5SupergiantPasses_whenSinglePassOrderCreated_itShouldHaveRebateOnEachPass() {

		for (int i = 0; i < supergiantPassesNumber; i++) {
			singleSupergiantPassOrder
				.passes
				.add(singlePassFactory.create(PassCategory.SUPERGIANT, OffsetDateTime.now()));
		}

		Money rabais = Money.of(CurrencyUnit.CAD, 50000);
		singleSupergiantPassOrder.updateTotalPrice();

		assertTrue(singleSupergiantPassOrder.calculateRebates().compareTo(rabais) == 0);
	}
}
