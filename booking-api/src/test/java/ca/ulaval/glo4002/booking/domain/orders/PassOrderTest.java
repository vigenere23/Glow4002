package ca.ulaval.glo4002.booking.domain.orders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.orders.orderNumber.OrderNumber;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;

public class PassOrderTest {

    private static final OrderNumber SOME_ORDER_NUMBER = new OrderNumber(VendorCode.TEAM, 0);
    private static final int NEBULA_SINGLE_PASS_DISCOUNT_QUANTITY = 4;
    private static final Price NEBULA_SINGLE_PASS_PRICE = new Price(50000);
    private static final int SUPERGIANT_SINGLE_PASS_DISCOUNT_QUANTITY = 5;
    private static final Price SUPERGIANT_SINGLE_PASS_PRICE = new Price(100000);
    private static final Price SUPERGIANT_SINGLE_PASS_DISCOUNTED_PRICE = new Price(90000);

    private Pass nebulaSinglePassMock;
    private Pass supergiantSinglePassMock;

    private List<Pass> passes;

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
    public void setUpPasses() {
        passes = new ArrayList<>();

        nebulaSinglePassMock = mock(Pass.class);
        when(nebulaSinglePassMock.getPrice()).thenReturn(NEBULA_SINGLE_PASS_PRICE);
        when(nebulaSinglePassMock.isOfType(PassOption.SINGLE_PASS, PassCategory.NEBULA))
            .thenReturn(true);

        supergiantSinglePassMock = mock(Pass.class);
        when(supergiantSinglePassMock.getPrice()).thenReturn(SUPERGIANT_SINGLE_PASS_PRICE);
        when(supergiantSinglePassMock.isOfType(PassOption.SINGLE_PASS, PassCategory.SUPERGIANT))
            .thenReturn(true);
    }

    @Test
    public void givenNoPasses_whenGettingPrice_itShouldBeZero() {
        PassOrder passOrder = new PassOrder(SOME_ORDER_NUMBER, new ArrayList<>());
        Price nullPrice = Price.zero();
        assertThat(passOrder.getPrice()).isEqualTo(nullPrice);
    }

    @Test
    public void givenOnePass_whenGettingPrice_itShouldBeThePassPrice() {
        initNebulaPasses(1);
        PassOrder passOrder = new PassOrder(SOME_ORDER_NUMBER, passes);
        assertThat(passOrder.getPrice()).isEqualTo(NEBULA_SINGLE_PASS_PRICE);
    }

    @Test
    public void givenTwoPasses_whenGettingPrice_itShouldBeDoubleThePassPrice() {
        initNebulaPasses(2);
        PassOrder passOrder = new PassOrder(SOME_ORDER_NUMBER, passes);

        Price twoTimesNebulaSinglePassPrice = NEBULA_SINGLE_PASS_PRICE.multipliedBy(2);
        assertThat(passOrder.getPrice()).isEqualTo(twoTimesNebulaSinglePassPrice);
    }

    @Test
    public void givenOverThreeNebulaSinglePasses_whenGettingPrice_itShouldHaveTenPercentDiscount() {
        initNebulaPasses(NEBULA_SINGLE_PASS_DISCOUNT_QUANTITY);
        PassOrder passOrder = new PassOrder(SOME_ORDER_NUMBER, passes);

        Price priceBeforeDiscount = NEBULA_SINGLE_PASS_PRICE.multipliedBy(NEBULA_SINGLE_PASS_DISCOUNT_QUANTITY);
        Price priceAfterDiscount = priceBeforeDiscount.multipliedBy(0.9);

        assertThat(passOrder.getPrice()).isEqualTo(priceAfterDiscount);
    }

    @Test
    public void givenAtLeastFiveSupergiantSinglePasses_whenGettingPrice_itShouldHaveDiscountOnEachPass() {
        initSupergiantPasses(SUPERGIANT_SINGLE_PASS_DISCOUNT_QUANTITY);
        PassOrder passOrder = new PassOrder(SOME_ORDER_NUMBER, passes);

        Price priceAfterDiscount = SUPERGIANT_SINGLE_PASS_DISCOUNTED_PRICE.multipliedBy(SUPERGIANT_SINGLE_PASS_DISCOUNT_QUANTITY);

        assertThat(passOrder.getPrice()).isEqualTo(priceAfterDiscount);
    }
}
