package ca.ulaval.glo4002.booking.domain;

import java.time.LocalDate;

public interface OrderableDeprecated extends PassInformationDeprecated {
    LocalDate getOrderDate();
}
