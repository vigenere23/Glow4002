package ca.ulaval.glo4002.booking.domain.passes.passTypes;

import java.time.LocalDate;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.passes.PassCategory;

public class SupergiantSinglePass extends SinglePass {

    public SupergiantSinglePass(LocalDate eventDate) {
        super(eventDate);
        price = Money.of(CurrencyUnit.CAD, 100000);
    }

    @Override
    public PassCategory getPassCategory() {
        return PassCategory.SUPERGIANT;
    }
}