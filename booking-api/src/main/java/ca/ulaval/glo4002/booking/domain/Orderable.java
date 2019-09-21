package ca.ulaval.glo4002.booking.domain;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.enumeration.PassCategory;

public interface Orderable extends PassInformation {
	LocalDate getOrderDate();
}
