package ca.ulaval.glo4002.booking.domain;

import java.time.LocalDate;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.enumeration.PassCategory;

public interface PassInformation {
	PassCategory getPassCategory();
	List<LocalDate> getDates();
}


