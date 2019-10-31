package ca.ulaval.glo4002.booking.domain.orders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.NebulaSinglePass;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.SupergiantSinglePass;

public class PassOrderTest {

    private static final VendorCode VENDOR_CODE = VendorCode.TEAM;
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
    public void setUp() {
        passes = new ArrayList<>();

        nebulaSinglePassMock = mock(NebulaSinglePass.class);
        when(nebulaSinglePassMock.getPrice()).thenReturn(NEBULA_SINGLE_PASS_PRICE);

        supergiantSinglePassMock = mock(SupergiantSinglePass.class);
        when(supergiantSinglePassMock.getPrice()).thenReturn(SUPERGIANT_SINGLE_PASS_PRICE);
    }

    @Test
    public void whenCreatingOrderWithNoPasses_thenThePriceShouldBeZero() {
        PassOrder passOrder = new PassOrder(VENDOR_CODE, new ArrayList<Pass>());
        assertThat(passOrder.getPrice().getAmount()).isEqualTo(Price.zero().getAmount());
    }

    @Test
    public void whenCreatingOrderWithOnePass_thenThePriceShouldBeThePassPrice() {
        initNebulaPasses(1);
        PassOrder passOrder = new PassOrder(VENDOR_CODE, passes);
        assertThat(passOrder.getPrice().getAmount()).isEqualTo(NEBULA_SINGLE_PASS_PRICE.getAmount());
    }

    @Test
    public void whenCreatingOrderWithTwoPasses_thenThePriceShouldBeDoubleThePassPrice() {
        initNebulaPasses(2);
        PassOrder passOrder = new PassOrder(VENDOR_CODE, passes);
        assertThat(passOrder.getPrice().getAmount()).isEqualTo(NEBULA_SINGLE_PASS_PRICE.multipliedBy(2).getAmount());
    }

    @Test
    public void givenOverThreeNebulaPasses_whenSinglePassOrderCreated_itShouldHaveTenPercentDiscount() {
        initNebulaPasses(NEBULA_SINGLE_PASS_DISCOUNT_QUANTITY);
        PassOrder passOrder = new PassOrder(VENDOR_CODE, passes);

        Price priceBeforeDiscount = NEBULA_SINGLE_PASS_PRICE.multipliedBy(NEBULA_SINGLE_PASS_DISCOUNT_QUANTITY);
        Price priceAfterDiscount = priceBeforeDiscount.multipliedBy(0.9);

        assertThat(passOrder.getPrice().getAmount()).isEqualTo(priceAfterDiscount.getAmount());
    }

    @Test
    public void givenAtLeastFiveSupergiantPasses_whenSinglePassOrderCreated_itShouldHaveDiscountOnEachPass() {
        initSupergiantPasses(SUPERGIANT_SINGLE_PASS_DISCOUNT_QUANTITY);
        PassOrder passOrder = new PassOrder(VENDOR_CODE, passes);

        Price priceAfterDiscount = SUPERGIANT_SINGLE_PASS_DISCOUNTED_PRICE.multipliedBy(SUPERGIANT_SINGLE_PASS_DISCOUNT_QUANTITY);

        assertThat(passOrder.getPrice().getAmount()).isEqualTo(priceAfterDiscount.getAmount());
    }
}
