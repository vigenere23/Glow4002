package ca.ulaval.glo4002.booking.domain.pass_ordering.orders;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.joda.money.Money;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.booking.domain.pass_ordering.orders.order_types.PackagePassOrder;
import ca.ulaval.glo4002.booking.domain.pass_ordering.orders.order_types.SinglePassOrder;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.PassOption;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.factories.SinglePassFactory;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.pass_types.SinglePass;

public class PassOrderTest {

	private OrderFactory orderFactory;
	private SinglePassFactory singlePassFactory;
	
	private PassOrder singlePassOrder;
	private PassOrder packagePassOrder;
	
	private SinglePass singleNebulaPass;
	private SinglePass singleNebulaPass2;

	@Before
	public void setUp() {
		orderFactory = new OrderFactory();
		singlePassFactory = new SinglePassFactory();
		SinglePass singleNebulaPass = singlePassFactory.newPass(PassCategory.NEBULA, LocalDateTime.now());
		SinglePass singleNebulaPass2 = singlePassFactory.newPass(PassCategory.NEBULA, LocalDateTime.now());
		ArrayList<LocalDateTime> eventDates = new ArrayList<LocalDateTime>();
		eventDates.add(singleNebulaPass.getDate());
		eventDates.add(singleNebulaPass2.getDate());
		
		singlePassOrder = orderFactory.createOrder(singlePassFactory, PassOption.SINGLE_PASS, PassCategory.NEBULA, eventDates);
		packagePassOrder = orderFactory.createOrder(singlePassFactory, PassOption.PACKAGE, PassCategory.NEBULA, eventDates);
	}
	
	@Test
	public void shouldUpdateTotalPriceWhenUpdateTotalPriceIsCalledOnCreationOfOrder() {
		
		Money totalPrice = Money.parse("USD 100000");
		
		System.out.println(singlePassOrder.getPrice());
		System.out.println(totalPrice);
		assertTrue(singlePassOrder.getPrice().compareTo(totalPrice) == 0);
	}

}