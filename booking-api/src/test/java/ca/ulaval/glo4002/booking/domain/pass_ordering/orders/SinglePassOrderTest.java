package ca.ulaval.glo4002.booking.domain.pass_ordering.orders;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.joda.money.Money;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.booking.domain.pass_ordering.orders.OrderFactory;
import ca.ulaval.glo4002.booking.domain.pass_ordering.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.PassOption;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.factories.SinglePassFactory;

public class SinglePassOrderTest {
	
		private SinglePassFactory singlePassFactory;
		private OrderFactory orderFactory;
		
		private PassOrder singleNebulaPassOrder;
		private PassOrder singleSupergiantPassOrder;

		private List<LocalDateTime> eventDates;
	
		@Before
		public void setUp() {
			orderFactory = new OrderFactory();
			eventDates = new ArrayList<>();
			singleNebulaPassOrder = orderFactory.createOrder(singlePassFactory, PassOption.SINGLE_PASS, PassCategory.NEBULA, eventDates);
			singleSupergiantPassOrder = orderFactory.createOrder(singlePassFactory, PassOption.SINGLE_PASS, PassCategory.SUPERGIANT, eventDates);

			singlePassFactory = new SinglePassFactory();
		}
		
		@Test
		public void singlePassOrderWithOver3NebulaPassesShouldHaveRebateOnTotalPrice() {
		
		for (int i = 0; i < 4; i++) {
			singleNebulaPassOrder.passes.add(singlePassFactory.newPass(PassCategory.NEBULA, LocalDateTime.now()));
		}
		
		Money rabais = Money.parse("USD 20000");
		System.out.println(singleNebulaPassOrder.calculateRebates());
		singleNebulaPassOrder.updateTotalPrice();
		
		assertTrue(singleNebulaPassOrder.calculateRebates().compareTo(rabais) == 0);
		}
		
		@Test
		public void singlePassOrderWith5SupergiantPassesShouldHaveRebateOnEachPass() {
		
		for (int i = 0; i < 5; i++) {
			singleSupergiantPassOrder.passes.add(singlePassFactory.newPass(PassCategory.SUPERGIANT, LocalDateTime.now()));
		}
		
		Money rabais = Money.parse("USD 50000");
		singleSupergiantPassOrder.updateTotalPrice();
		
		assertTrue(singleSupergiantPassOrder.calculateRebates().compareTo(rabais) == 0);
		}

}
