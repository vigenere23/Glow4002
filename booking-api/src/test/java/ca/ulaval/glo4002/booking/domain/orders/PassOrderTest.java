package ca.ulaval.glo4002.booking.domain.orders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.orders.discounts.NebulaSinglePassDiscount;
import ca.ulaval.glo4002.booking.domain.orders.discounts.OrderDiscount;
import ca.ulaval.glo4002.booking.domain.orders.orderNumber.OrderNumber;
import ca.ulaval.glo4002.booking.domain.orders.discounts.OrderDiscountFactory;
import ca.ulaval.glo4002.booking.domain.orders.discounts.SupergiantSinglePassDiscount;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;
import ca.ulaval.glo4002.booking.domain.profit.IncomeSaver;

public class PassOrderTest {

    private final static int NEBULA_SINGLE_PASS_DISCOUNT_QUANTITY = 4;
    private final static Price NEBULA_SINGLE_PASS_PRICE = new Price(50000);
    private final static int SUPERGIANT_SINGLE_PASS_DISCOUNT_QUANTITY = 5;
    private final static Price SUPERGIANT_SINGLE_PASS_PRICE = new Price(100000);
    private final static Price SUPERGIANT_SINGLE_PASS_DISCOUNTED_PRICE = new Price(90000);

    private Pass nebulaSinglePassMock;
    private Pass supergiantSinglePassMock;
    private IncomeSaver incomeSaver;
    private OrderDiscount orderDiscount;
    private OrderNumber orderNumber;
    private List<Pass> passes;

    @BeforeEach
    public void setUpPassOrder() {
        passes = new ArrayList<>();
        incomeSaver = mock(IncomeSaver.class);
        orderDiscount = new OrderDiscountFactory().fromMultipleDiscounts(
            new SupergiantSinglePassDiscount(), new NebulaSinglePassDiscount()
        );

        orderNumber = new OrderNumber(VendorCode.TEAM, 0);

        mockNebulaSinglePass();
        mockSupergiantSinglePass();
    }

    @Test
    public void givenNoPasses_whenGettingPrice_itShouldBeZero() {
        PassOrder passOrder = new PassOrder(orderNumber, new ArrayList<>(), orderDiscount);

        Price actualPrice = passOrder.getPrice();

        Price expectedPrice = Price.zero();
        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void givenOneNebulaPass_whenGettingPrice_thenShouldBeTheNebulaPassPrice() {
        initNebulaPasses(1);
        PassOrder passOrder = new PassOrder(orderNumber, passes, orderDiscount);
        assertEquals(NEBULA_SINGLE_PASS_PRICE, passOrder.getPrice());
    }

    @Test
    public void givenTwoNebulaPasses_whenGettingPrice_thenShouldBeDoubleTheNebulaPassPrice() {
        initNebulaPasses(2);
        PassOrder passOrder = new PassOrder(orderNumber, passes, orderDiscount);
        
        Price actualPrice = passOrder.getPrice();

        Price twoTimesNebulaSinglePassPrice = NEBULA_SINGLE_PASS_PRICE.multipliedBy(2);
        assertEquals(twoTimesNebulaSinglePassPrice, actualPrice);
    }

    @Test
    public void givenOverThreeNebulaSinglePasses_whenGettingPrice_thenItShouldHaveTenPercentDiscount() {
        initNebulaPasses(NEBULA_SINGLE_PASS_DISCOUNT_QUANTITY);
        PassOrder passOrder = new PassOrder(orderNumber, passes, orderDiscount);

        Price actualPrice = passOrder.getPrice();

        Price priceBeforeDiscount = NEBULA_SINGLE_PASS_PRICE.multipliedBy(NEBULA_SINGLE_PASS_DISCOUNT_QUANTITY);
        Price priceAfterDiscount = priceBeforeDiscount.multipliedBy(0.9);
        assertEquals(priceAfterDiscount, actualPrice);
    }

    @Test
    public void givenAtLeastFiveSupergiantSinglePasses_whenGettingPrice_thenItShouldHaveDiscountOnEachPass() {
        initSupergiantPasses(SUPERGIANT_SINGLE_PASS_DISCOUNT_QUANTITY);
        PassOrder passOrder = new PassOrder(orderNumber, passes, orderDiscount);

        Price actualPrice = passOrder.getPrice();

        Price priceAfterDiscount = SUPERGIANT_SINGLE_PASS_DISCOUNTED_PRICE.multipliedBy(SUPERGIANT_SINGLE_PASS_DISCOUNT_QUANTITY);
        assertEquals(priceAfterDiscount, actualPrice);
    }

    @Test
    public void givenNebulaPass_whenSaveIncome_thenItShouldCallMethodSaveIncomeFromIncomeSaverWithNebulaPrice() {
        initNebulaPasses(1);
        PassOrder passOrder = new PassOrder(orderNumber, passes, orderDiscount);

        passOrder.saveIncome(incomeSaver);
        
        verify(incomeSaver).saveIncome(NEBULA_SINGLE_PASS_PRICE);
    }
    
    private void initNebulaPasses(int numberOfPasses) {
        for (int i = 0; i < numberOfPasses; i++) {
            passes.add(nebulaSinglePassMock);
        }
    }

    private void initSupergiantPasses(int numberOfPasses) {
        for (int i = 0; i < numberOfPasses; i++) {
            passes.add(supergiantSinglePassMock);
        }
    }

    private void mockSupergiantSinglePass() {
        nebulaSinglePassMock = mock(Pass.class);
        when(nebulaSinglePassMock.getPrice()).thenReturn(NEBULA_SINGLE_PASS_PRICE);
        when(nebulaSinglePassMock.isOfType(PassOption.SINGLE_PASS, PassCategory.NEBULA)).thenReturn(true);
    }

    private void mockNebulaSinglePass() {
        supergiantSinglePassMock = mock(Pass.class);
        when(supergiantSinglePassMock.getPrice()).thenReturn(SUPERGIANT_SINGLE_PASS_PRICE);
        when(supergiantSinglePassMock.isOfType(PassOption.SINGLE_PASS, PassCategory.SUPERGIANT)).thenReturn(true);
    }

}
