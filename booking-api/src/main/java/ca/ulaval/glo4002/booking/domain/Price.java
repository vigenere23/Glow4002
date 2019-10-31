package ca.ulaval.glo4002.booking.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public class Price {

    private Money price;

    public Price(Price other) {
        this(other.getAmount());
    }

    public Price(double amount) {
        price = Money.of(CurrencyUnit.CAD, amount);
    }

    public Price(BigDecimal amount) {
        price = Money.of(CurrencyUnit.CAD, amount);
    }

    public BigDecimal getAmount() {
        return price.getAmount();
    }

    public boolean equals(Price other) {
        return price.equals(other.price);
    }

    public Price plus(Price other) {
        return new Price(price.plus(other.price).getAmount());
    }

    public Price minus(Price other) {
        return new Price(price.minus(other.price).getAmount());
    }

    public Price multipliedBy(double multiplier) {
        return new Price(price.multipliedBy(multiplier, RoundingMode.HALF_UP).getAmount());
    }

    public Price multipliedBy(Price other) {
        return new Price(price.multipliedBy(other.getAmount(), RoundingMode.HALF_UP).getAmount());
    }

    public Price dividedBy(double multiplier) {
        return new Price(price.dividedBy(multiplier, RoundingMode.HALF_UP).getAmount());
    }

    public Price dividedBy(Price other) {
        return new Price(price.dividedBy(other.getAmount(), RoundingMode.HALF_UP).getAmount());
    }

    public static Price sum(List<Price> prices) {
        Price total = new Price(0.0);

        for (Price price : prices) {
            total = total.plus(price);
        }

        return total;
    }
}
