package ca.ulaval.glo4002.booking.domain.passOrdering.orders;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.passOrdering.orders.OrderFactory;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassOption;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories.SinglePassFactory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.SinglePass;

public class PassOrderTest {

	private OrderFactory orderFactory;
	private SinglePassFactory singlePassFactory;
	private PassOrder packagePassOrder;
	private PassOrder singlePassOrder;

	@BeforeEach
	public void setUp() {
		orderFactory = new OrderFactory();
		singlePassFactory = new SinglePassFactory();

		SinglePass singleNebulaPass = singlePassFactory.create(PassCategory.NEBULA, OffsetDateTime.now());
		SinglePass singleNebulaPass2 = singlePassFactory.create(PassCategory.NEBULA, OffsetDateTime.now());

		ArrayList<OffsetDateTime> eventDates = new ArrayList<OffsetDateTime>();
		eventDates.add(singleNebulaPass.getDate());
		eventDates.add(singleNebulaPass2.getDate());

		singlePassOrder = orderFactory.createOrder(OffsetDateTime.now(), PassOption.SINGLE_PASS, PassCategory.NEBULA, eventDates);
		packagePassOrder = orderFactory.createOrder(OffsetDateTime.now(), PassOption.PACKAGE, PassCategory.NEBULA, eventDates);
	}
	
	@Test
	public void givenSinglePassOrderCreated_itShouldUpdateTotalPriceCorrectly() {
		
		Money totalPrice = Money.of(CurrencyUnit.CAD, 100000);

		assertTrue(singlePassOrder.getPrice().isEqual(totalPrice));
	}

	@Test
	public void givenPackagePassOrderCreated_itShouldUpdateTotalPriceCorrectly() {
		
		Money totalPrice = Money.of(CurrencyUnit.CAD, 250000);

		System.out.println(packagePassOrder.getPrice());

		assertTrue(packagePassOrder.getPrice().isEqual(totalPrice));
	}
}