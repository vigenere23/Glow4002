package ca.ulaval.glo4002.booking.domain.passes;

import java.util.concurrent.atomic.AtomicLong;

public class PassNumber {

    private long value;
    private static final AtomicLong incrementor = new AtomicLong(0); 

    public PassNumber() {
        value = incrementor.getAndIncrement();
    }

    private PassNumber(long value) {
        this.value = value;
    }

    public static PassNumber of(long value) {
        return new PassNumber(value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof PassNumber)) return false;

        PassNumber otherPassNumber = (PassNumber) other;
        return value == otherPassNumber.value;
    }

    public long getValue() {
        return value;
    }
}
