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
import ca.ulaval.glo4002.booking.domain.orders.orderNumber.OrderNumber;
import ca.ulaval.glo4002.booking.domain.orders.discounts.OrderDiscountFactory;
import ca.ulaval.glo4002.booking.domain.orders.discounts.SupergiantSinglePassDiscount;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;
import ca.ulaval.glo4002.booking.domain.profit.IncomeSaver;

public class PassOrderTest {

    private static final OrderNumber SOME_ORDER_NUMBER = new OrderNumber(VendorCode.TEAM, 0);
    private static final int NEBULA_SINGLE_PASS_DISCOUNT_QUANTITY = 4;
    private static final Price NEBULA_SINGLE_PASS_PRICE = new Price(50000);
    private static final int SUPERGIANT_SINGLE_PASS_DISCOUNT_QUANTITY = 5;
    private static final Price SUPERGIANT_SINGLE_PASS_PRICE = new Price(100000);
    private static final Price SUPERGIANT_SINGLE_PASS_DISCOUNTED_PRICE = new Price(90000);

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

        orderNumber = new OrderNumber(VENDOR_CODE);

        nebulaSinglePassMock = mock(Pass.class);
        when(nebulaSinglePassMock.getPrice()).thenReturn(NEBULA_SINGLE_PASS_PRICE);
        when(nebulaSinglePassMock.isOfType(PassOption.SINGLE_PASS, PassCategory.NEBULA)).thenReturn(true);

        supergiantSinglePassMock = mock(Pass.class);
        when(supergiantSinglePassMock.getPrice()).thenReturn(SUPERGIANT_SINGLE_PASS_PRICE);
        when(supergiantSinglePassMock.isOfType(PassOption.SINGLE_PASS, PassCategory.SUPERGIANT)).thenReturn(true);
    }

    @Test
    public void NoPasses_whenGettingPrice_itShouldBeZero() {
        PassOrder passOrder = new PassOrder(orderNumber, new ArrayList<>(), orderDiscount);
        Price nullPrice = Price.zero();
        assertThat(passOrder.getPrice()).isEqualTo(nullPrice);
    }

    @Test
    public void givenOnePass_whenGettingPrice_itShouldBeThePassPrice() {
        initNebulaPasses(1);
        PassOrder passOrder = new PassOrder(orderNumber, passes, orderDiscount);
        assertThat(passOrder.getPrice()).isEqualTo(NEBULA_SINGLE_PASS_PRICE);
    }

    @Test
    public void givenTwoPasses_whenGettingPrice_itShouldBeDoubleThePassPrice() {
        initNebulaPasses(2);
        PassOrder passOrder = new PassOrder(orderNumber, passes, orderDiscount);

        Price twoTimesNebulaSinglePassPrice = NEBULA_SINGLE_PASS_PRICE.multipliedBy(2);
        assertThat(passOrder.getPrice()).isEqualTo(twoTimesNebulaSinglePassPrice);
    }

    @Test
    public void givenOverThreeNebulaSinglePasses_whenGettingPrice_itShouldHaveTenPercentDiscount() {
        initNebulaPasses(NEBULA_SINGLE_PASS_DISCOUNT_QUANTITY);
        PassOrder passOrder = new PassOrder(orderNumber, passes, orderDiscount);

        Price priceBeforeDiscount = NEBULA_SINGLE_PASS_PRICE.multipliedBy(NEBULA_SINGLE_PASS_DISCOUNT_QUANTITY);
        Price priceAfterDiscount = priceBeforeDiscount.multipliedBy(0.9);

        assertThat(passOrder.getPrice()).isEqualTo(priceAfterDiscount);
    }

    @Test
    public void givenAtLeastFiveSupergiantSinglePasses_whenGettingPrice_itShouldHaveDiscountOnEachPass() {
        initSupergiantPasses(SUPERGIANT_SINGLE_PASS_DISCOUNT_QUANTITY);
        PassOrder passOrder = new PassOrder(orderNumber, passes, orderDiscount);

        Price priceAfterDiscount = SUPERGIANT_SINGLE_PASS_DISCOUNTED_PRICE.multipliedBy(SUPERGIANT_SINGLE_PASS_DISCOUNT_QUANTITY);

        assertThat(passOrder.getPrice()).isEqualTo(priceAfterDiscount);
    }

    @Test
    public void whenSaveIncome_itShouldCallMethodSaveIncomeFromIncomeSaver() {
        initNebulaPasses(1);
        PassOrder passOrder = new PassOrder(VENDOR_CODE, passes, incomeSaver);

        passOrder.saveIncome();
        
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

    @BeforeEach
    public void setUpPassOrder() {
        passes = new ArrayList<>();
        incomeSaver = mock(IncomeSaver.class);

        nebulaSinglePassMock = mock(Pass.class);
        when(nebulaSinglePassMock.getPrice()).thenReturn(NEBULA_SINGLE_PASS_PRICE);
        when(nebulaSinglePassMock.isOfType(PassOption.SINGLE_PASS, PassCategory.NEBULA)).thenReturn(true);

        supergiantSinglePassMock = mock(Pass.class);
        when(supergiantSinglePassMock.getPrice()).thenReturn(SUPERGIANT_SINGLE_PASS_PRICE);
        when(supergiantSinglePassMock.isOfType(PassOption.SINGLE_PASS, PassCategory.SUPERGIANT)).thenReturn(true);
    }

    @Test
    public void NoPasses_whenGettingPrice_itShouldBeZero() {
        PassOrder passOrder = new PassOrder(SOME_ORDER_NUMBER, new ArrayList<>(), incomeSaver);
        Price nullPrice = Price.zero();
        assertEquals(nullPrice, passOrder.getPrice());
    }

    @Test
    public void givenOnePass_whenGettingPrice_itShouldBeThePassPrice() {
        initNebulaPasses(1);
        PassOrder passOrder = new PassOrder(SOME_ORDER_NUMBER, passes, incomeSaver);
        assertEquals(NEBULA_SINGLE_PASS_PRICE, passOrder.getPrice());
    }

    @Test
    public void whenSaveIncome_itShouldCallMethodSaveIncomeFromIncomeSaver() {
        initNebulaPasses(1);
        PassOrder passOrder = new PassOrder(SOME_ORDER_NUMBER, passes, incomeSaver);

        passOrder.saveIncome();
        
        verify(incomeSaver).saveIncome(NEBULA_SINGLE_PASS_PRICE);
    }

    @Test
    public void givenTwoPasses_whenGettingPrice_itShouldBeDoubleThePassPrice() {
        initNebulaPasses(2);
        PassOrder passOrder = new PassOrder(VENDOR_CODE, passes, incomeSaver);

        Price twoTimesNebulaSinglePassPrice = NEBULA_SINGLE_PASS_PRICE.multipliedBy(2);
        assertEquals(twoTimesNebulaSinglePassPrice, passOrder.getPrice());
    }

    @Test
    public void givenOverThreeNebulaSinglePasses_whenGettingPrice_itShouldHaveTenPercentDiscount() {
        initNebulaPasses(NEBULA_SINGLE_PASS_DISCOUNT_QUANTITY);
        PassOrder passOrder = new PassOrder(VENDOR_CODE, passes, incomeSaver);

        Price priceBeforeDiscount = NEBULA_SINGLE_PASS_PRICE.multipliedBy(NEBULA_SINGLE_PASS_DISCOUNT_QUANTITY);
        Price priceAfterDiscount = priceBeforeDiscount.multipliedBy(0.9);

        assertEquals(priceAfterDiscount, passOrder.getPrice());
    }

    @Test
    public void givenAtLeastFiveSupergiantSinglePasses_whenGettingPrice_itShouldHaveDiscountOnEachPass() {
        initSupergiantPasses(SUPERGIANT_SINGLE_PASS_DISCOUNT_QUANTITY);
        PassOrder passOrder = new PassOrder(SOME_ORDER_NUMBER, passes, incomeSaver);

        Price priceAfterDiscount = SUPERGIANT_SINGLE_PASS_DISCOUNTED_PRICE.multipliedBy(SUPERGIANT_SINGLE_PASS_DISCOUNT_QUANTITY);

        assertEquals(priceAfterDiscount, passOrder.getPrice());
    }
}
