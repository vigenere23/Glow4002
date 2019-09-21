package ca.ulaval.glo4002.booking.domain;

import org.joda.money.Money;

public interface Priceable {
    public Money getPrice();
}
