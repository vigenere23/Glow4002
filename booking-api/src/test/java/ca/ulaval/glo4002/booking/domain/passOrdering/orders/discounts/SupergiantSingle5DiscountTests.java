package ca.ulaval.glo4002.booking.domain.passOrdering.orders.discounts;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.passOrdering.orders.discounts.SupergiantSingle5Discount;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories.SinglePassFactory;

public class SupergiantSingle5DiscountTests {

    SinglePassFactory singlePassFactory = new SinglePassFactory();
    int supergiantDiscountPassesNumber;
    Money totalPriceWithoutDiscount;
    Money finalPrice;
    SupergiantSingle5Discount discount = new SupergiantSingle5Discount();
    List<Pass> passes = new ArrayList<Pass>();
    
    @Test
    public void given2NebulaPassesInPassList_thenShouldReturnSameTotalPrice() {
        supergiantDiscountPassesNumber = 2;
        totalPriceWithoutDiscount = Money.of(CurrencyUnit.CAD, 200000);

        for (int i = 0; i < supergiantDiscountPassesNumber; i++) {
            passes.add(singlePassFactory.create(PassCategory.SUPERGIANT, OffsetDateTime.now()));
        }

        assertTrue(discount.priceAfterDiscounts(passes, totalPriceWithoutDiscount).isEqual(totalPriceWithoutDiscount));
    }

    @Test
    public void given4NebulaPassesInPassList_thenShouldReturnLowerTotalPrice() {
        supergiantDiscountPassesNumber = 5;
        totalPriceWithoutDiscount = Money.of(CurrencyUnit.CAD, 500000);
        finalPrice = Money.of(CurrencyUnit.CAD, 450000);

        for (int i = 0; i < supergiantDiscountPassesNumber; i++) {
            passes.add(singlePassFactory.create(PassCategory.SUPERGIANT, OffsetDateTime.now()));
        }

        assertTrue(discount.priceAfterDiscounts(passes, totalPriceWithoutDiscount).isEqual(finalPrice));
    }
}