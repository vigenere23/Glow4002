package ca.ulaval.glo4002.booking.domain;

import java.time.LocalDate;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;

public interface PassInformationDeprecated {
    public PassCategory getPassCategory();

    List<LocalDate> getDates();
}