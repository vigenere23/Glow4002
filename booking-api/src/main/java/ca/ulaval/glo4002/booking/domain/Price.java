package ca.ulaval.glo4002.booking.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.stream.Stream;

import org.joda.money.CurrencyUnit;
import org.joda.money.BigMoney;

public class Price implements Comparable<Price> {

    private static final CurrencyUnit CURRENCY = CurrencyUnit.CAD;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    private final BigMoney price;

    public static Price zero() {
        return new Price(0);
    }

    public Price(double amount) {
        this(BigDecimal.valueOf(amount));
    }

    public Price(BigDecimal amount) {
        price = BigMoney.of(CURRENCY, amount);
    }

    public BigDecimal getAmount() {
        return price.getAmount();
    }

    public float getRoundedAmount(int numberOfDecimals) {
        return price.rounded(numberOfDecimals, ROUNDING_MODE).getAmount().floatValue();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof Price)) return false;

        Price otherPrice = (Price) other;
        return price.equals(otherPrice.price);
    }

    @Override
    public int compareTo(Price other) {
        return price.compareTo(other.price);
    }

    public Price plus(Price other) {
        return new Price(price.plus(other.price).getAmount());
    }

    public Price minus(Price other) {
        return new Price(price.minus(other.price).getAmount());
    }

    public Price multipliedBy(long multiplier) {
        return new Price(price.multipliedBy(multiplier).getAmount());
    }

    public Price multipliedBy(double multiplier) {
        return new Price(price.multipliedBy(multiplier).getAmount());
    }

    public Price dividedBy(double multiplier) {
        return new Price(price.dividedBy(multiplier, ROUNDING_MODE).getAmount());
    }

    public static Price sum(Price ...prices) {
        return sum(Arrays.asList(prices).stream());
    }

    public static Price sum(Stream<Price> prices) {
        return prices.reduce(Price.zero(), (subtotal, price) -> subtotal.plus(price));
    }
}
