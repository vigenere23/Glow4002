package ca.ulaval.glo4002.booking.domain.passOrdering.orders.discounts;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.passOrdering.orders.discounts.NebulaSingle4Discount;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories.SinglePassFactory;

public class NebulaSingle4DiscountTests {

    SinglePassFactory singlePassFactory = new SinglePassFactory();
    int nebulaDiscountPassesNumber;
    Money totalPriceWithoutDiscount;
    Money finalPrice;
    NebulaSingle4Discount discount = new NebulaSingle4Discount();
    List<Pass> passes = new ArrayList<Pass>();
    
    @Test
    public void given2NebulaPassesInPassList_thenShouldReturnSameTotalPrice() {
        nebulaDiscountPassesNumber = 2;
        totalPriceWithoutDiscount = Money.of(CurrencyUnit.CAD, 100000);

        for (int i = 0; i < nebulaDiscountPassesNumber; i++) {
            passes.add(singlePassFactory.create(PassCategory.NEBULA, OffsetDateTime.now()));
        }

        assertTrue(discount.priceAfterDiscounts(passes, totalPriceWithoutDiscount).isEqual(totalPriceWithoutDiscount));
    }

    @Test
    public void given4NebulaPassesInPassList_thenShouldReturnLowerTotalPrice() {
        nebulaDiscountPassesNumber = 4;
        totalPriceWithoutDiscount = Money.of(CurrencyUnit.CAD, 200000);
        finalPrice = Money.of(CurrencyUnit.CAD, 180000);

        for (int i = 0; i < nebulaDiscountPassesNumber; i++) {
            passes.add(singlePassFactory.create(PassCategory.NEBULA, OffsetDateTime.now()));
        }

        assertTrue(discount.priceAfterDiscounts(passes, totalPriceWithoutDiscount).isEqual(finalPrice));
    }
}