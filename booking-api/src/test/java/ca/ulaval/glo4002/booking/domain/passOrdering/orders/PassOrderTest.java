package ca.ulaval.glo4002.booking.domain.passOrdering.orders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.NebulaSinglePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.SupergiantSinglePass;

public class PassOrderTest {

    private static final int NEBULA_SINGLE_PASS_DISCOUNT_QUANTITY = 4;
    private static final Money NEBULA_SINGLE_PASS_PRICE = Money.of(CurrencyUnit.CAD, 50000);
    private static final int SUPERGIANT_SINGLE_PASS_DISCOUNT_QUANTITY = 5;
    private static final Money SUPERGIANT_SINGLE_PASS_PRICE = Money.of(CurrencyUnit.CAD, 100000);
    private static final Money SUPERGIANT_SINGLE_PASS_DISCOUNTED_PRICE = Money.of(CurrencyUnit.CAD, 90000);

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
        PassOrder passOrder = new PassOrder(new ArrayList<Pass>());
        assertThat(passOrder.getPrice()).isEqualTo(Money.zero(CurrencyUnit.CAD));
    }

    @Test
    public void whenCreatingOrderWithOnePass_thenThePriceShouldBeThePassPrice() {
        initNebulaPasses(1);
        PassOrder passOrder = new PassOrder(passes);
        assertThat(passOrder.getPrice()).isEqualTo(NEBULA_SINGLE_PASS_PRICE);
    }

    @Test
    public void whenCreatingOrderWithTwoPasses_thenThePriceShouldBeDoubleThePassPrice() {
        initNebulaPasses(2);
        PassOrder passOrder = new PassOrder(passes);
        assertThat(passOrder.getPrice()).isEqualTo(NEBULA_SINGLE_PASS_PRICE.multipliedBy(2));
    }

    @Test
    public void givenOverThreeNebulaPasses_whenSinglePassOrderCreated_itShouldHaveTenPercentDiscount() {
        initNebulaPasses(NEBULA_SINGLE_PASS_DISCOUNT_QUANTITY);
        PassOrder passOrder = new PassOrder(passes);

        Money priceBeforeDiscount = NEBULA_SINGLE_PASS_PRICE.multipliedBy(NEBULA_SINGLE_PASS_DISCOUNT_QUANTITY);
        Money priceAfterDiscount = priceBeforeDiscount.multipliedBy(0.9, RoundingMode.HALF_UP);

        assertThat(passOrder.getPrice()).isEqualTo(priceAfterDiscount);
    }

    @Test
    public void givenAtLeastFiveSupergiantPasses_whenSinglePassOrderCreated_itShouldHaveDiscountOnEachPass() {
        initSupergiantPasses(SUPERGIANT_SINGLE_PASS_DISCOUNT_QUANTITY);
        PassOrder passOrder = new PassOrder(passes);

        Money priceAfterDiscount = SUPERGIANT_SINGLE_PASS_DISCOUNTED_PRICE.multipliedBy(SUPERGIANT_SINGLE_PASS_DISCOUNT_QUANTITY);

        assertThat(passOrder.getPrice()).isEqualTo(priceAfterDiscount);
    }
}
