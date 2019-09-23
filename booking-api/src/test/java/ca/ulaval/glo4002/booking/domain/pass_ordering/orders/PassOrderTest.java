package ca.ulaval.glo4002.booking.domain.pass_ordering.orders;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.booking.domain.pass_ordering.order_types.PackagePassOrder;
import ca.ulaval.glo4002.booking.domain.pass_ordering.order_types.SinglePassOrder;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.PassFactory;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.PassOption;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.factories.SinglePassFactory;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.pass_types.NebulaSinglePass;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.pass_types.SinglePass;

public class PassOrderTest {

	private OrderFactory orderFactory;
	private SinglePassFactory singlePassFactory;
	
	private PassOrder singlePassOrder;
	private PassOrder packagePassOrder;

	@Before
	public void setUp() {
		orderFactory = new OrderFactory();
		singlePassOrder = orderFactory.newOrder(PassOption.SINGLE_PASS);
		packagePassOrder = orderFactory.newOrder(PassOption.PACKAGE);
		
		singlePassFactory = new SinglePassFactory();
	}

	@Test
	public void orderFactoryShouldCreateNewSinglePass() {
		SinglePassOrder order = new SinglePassOrder();
		
		assertTrue(singlePassOrder.getClass().equals(order.getClass()));
	}

	@Test
	public void orderFactoryShouldCreateNewPackagePass() {
		PackagePassOrder order = new PackagePassOrder();
		
		assertTrue(packagePassOrder.getClass().equals(order.getClass()));
	}
	
	@Test
	public void singlePassOrderWithOver3NebulaPassesShouldHaveRebateOnTotalPrice() {
		
		for (int i = 0; i < 4; i++) {
			singlePassOrder.passes.add(singlePassFactory.newPass(PassCategory.NEBULA, LocalDateTime.now()));
		}
		
		double rabais = 20000;
		singlePassOrder.updateTotalPrice();
		
		assertTrue(singlePassOrder.calculateRebates() == rabais);
	}

}