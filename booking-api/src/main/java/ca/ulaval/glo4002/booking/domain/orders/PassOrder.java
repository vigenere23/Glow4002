package ca.ulaval.glo4002.booking.domain.orders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.orders.discounts.NebulaSinglePassDiscount;
import ca.ulaval.glo4002.booking.domain.orders.discounts.OrderDiscount;
import ca.ulaval.glo4002.booking.domain.orders.discounts.OrderDiscountFactory;
import ca.ulaval.glo4002.booking.domain.orders.discounts.SupergiantSinglePassDiscount;
import ca.ulaval.glo4002.booking.domain.orders.orderNumber.OrderNumber;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.profit.IncomeSaver;

public class PassOrder {

    private OrderNumber orderNumber;
    private List<Pass> passes = new ArrayList<>();
    private OrderDiscount orderDiscount;
    private IncomeSaver incomeSaver;

    public PassOrder(OrderNumber orderNumber, List<Pass> passes, OrderDiscount orderDiscount, IncomeSaver incomeSaver) {
        this.passes = passes;
        this.incomeSaver = incomeSaver;
        this.orderDiscount = orderDiscount;
        this.orderNumber = orderNumber;
    }

    public Price getPrice() {
        Price priceBeforeDiscounts = Price.sum(getPrices());

        return orderDiscount.getPriceAfterDiscounts(
            Collections.unmodifiableList(passes), priceBeforeDiscounts
        );
    }

    public void saveIncome() {
        incomeSaver.saveIncome(calculateTotalPrice());
	}

    private Stream<Price> getPrices() {
        return passes.stream().map(Pass::getPrice);
    }

    public OrderNumber getOrderNumber() {
        return orderNumber;
    }

    public List<Pass> getPasses() {
        return Collections.unmodifiableList(passes);
    }
}
