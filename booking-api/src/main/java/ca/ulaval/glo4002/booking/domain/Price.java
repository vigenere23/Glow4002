package ca.ulaval.glo4002.booking.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public class Price {

    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    private Money price;

    public static Price zero() {
        return new Price(0);
    }

    public Price(Price other) {
        this(other.getAmount());
    }

    public Price(double amount) {
        this(BigDecimal.valueOf(amount));
    }

    public Price(BigDecimal amount) {
        price = Money.of(CurrencyUnit.CAD, amount);
    }

    public BigDecimal getAmount() {
        return price.getAmount();
    }

    public BigDecimal getRoundedAmount(int numberOfDecimals) {
        return price.rounded(numberOfDecimals, ROUNDING_MODE).getAmount();
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
        return new Price(price.multipliedBy(multiplier, ROUNDING_MODE).getAmount());
    }

    public Price multipliedBy(Price other) {
        return new Price(price.multipliedBy(other.getAmount(), ROUNDING_MODE).getAmount());
    }

    public Price dividedBy(double multiplier) {
        return new Price(price.dividedBy(multiplier, ROUNDING_MODE).getAmount());
    }

    public Price dividedBy(Price other) {
        return new Price(price.dividedBy(other.getAmount(), ROUNDING_MODE).getAmount());
    }

    public static Price sum(List<Price> prices) {
        return prices
            .stream()
            .reduce(Price.zero(), (subtotal, price) -> subtotal.plus(price));
    }
}
