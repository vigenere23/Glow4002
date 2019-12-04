package ca.ulaval.glo4002.booking.domain.orders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.orders.discounts.OrderDiscount;
import ca.ulaval.glo4002.booking.domain.orders.order_number.OrderNumber;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.profit.IncomeSaver;

public class PassOrder {

    private OrderNumber orderNumber;
    private List<Pass> passes = new ArrayList<>();
    private Optional<OrderDiscount> orderDiscount;

    public PassOrder(OrderNumber orderNumber, List<Pass> passes, Optional<OrderDiscount> orderDiscount) {
        this.orderNumber = orderNumber;
        this.passes = passes;
        this.orderDiscount = orderDiscount;
    }

    public Price getPrice() {
        return calculateTotalPrice();
    }

    private Price calculateTotalPrice() {
        Price priceBeforeDiscounts = Price.sum(getPrices());

        return orderDiscount.isPresent()
            ? orderDiscount.get().getPriceAfterDiscounts(Collections.unmodifiableList(passes), priceBeforeDiscounts)
            : priceBeforeDiscounts;
    }

    public void saveIncome(IncomeSaver incomeSaver) {
        incomeSaver.addIncome(getPrice());
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
