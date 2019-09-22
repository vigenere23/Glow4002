package ca.ulaval.glo4002.booking.domain;

import java.time.LocalDate;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.enumeration.PassCategory;

public class Pass implements Orderable {
    private LocalDate orderDate;
    private PassCategory passCategory;
    private List<LocalDate> dates;

    public Pass(LocalDate orderDate, PassCategory passCategory, List<LocalDate> dates) {
	this.orderDate = orderDate;
	this.passCategory = passCategory;
	this.dates = dates;
    }

    public LocalDate getOrderDate() {
	return orderDate;
    }

    public PassCategory getPassCategory() {
	return passCategory;
    }

    public List<LocalDate> getDates() {
	return dates;
    }
}
