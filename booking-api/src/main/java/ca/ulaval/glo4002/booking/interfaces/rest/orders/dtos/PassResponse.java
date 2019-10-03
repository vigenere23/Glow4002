package ca.ulaval.glo4002.booking.interfaces.rest.orders.dtos;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassOption;

public class PassResponse {

    public final long passNumber;
    public final String passOption;
    public final String passCategory;

    public PassResponse(long passNumber, PassOption passOption, PassCategory passCategory) {
        this.passNumber = passNumber;
        this.passOption = passOption.toString();
        this.passCategory = passCategory.toString();
    }
}
