package ca.ulaval.glo4002.booking.domain.passOrdering.orders;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.passOrdering.orders.orderTypes.PackagePassOrder;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.orderTypes.SinglePassOrder;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassOption;


public class OrderFactoryTests {

    private OffsetDateTime orderDate;
    private OrderFactory orderFactory;
    private PassOrder order;
    private List<OffsetDateTime> eventDates;
    
    @BeforeEach
	public void setUp() {
        orderFactory = new OrderFactory();
        eventDates = new ArrayList<>();
        orderDate = OffsetDateTime.now();
    }

	@Test
	public void givenPackagePassOption_whenOrderCreated_itShouldCreateInstanceOfPackagePassOrder() {
        order = orderFactory.createOrder(orderDate, PassOption.PACKAGE, PassCategory.NEBULA, eventDates);

        assertTrue(order.getClass().equals(PackagePassOrder.class));
    }
    
    @Test
	public void givenSinglePassOption_whenOrderCreated_itShouldCreateInstanceOfSinglePassOrder() {
        order = orderFactory.createOrder(orderDate, PassOption.SINGLE_PASS, PassCategory.NEBULA, eventDates);

        assertTrue(order.getClass().equals(SinglePassOrder.class));
	}
}