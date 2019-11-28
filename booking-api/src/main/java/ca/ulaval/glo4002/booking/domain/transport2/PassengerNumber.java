package ca.ulaval.glo4002.booking.domain.transport2;

import ca.ulaval.glo4002.booking.domain.passes.pass_number.PassNumber;

public class PassengerNumber {
    
    private long value;

    public PassengerNumber(long value) {
        this.value = value;
    }

    public PassengerNumber(PassNumber passNumber) {
        this(passNumber.getValue());
    }

    public long getValue() {
        return value;
    }
}
