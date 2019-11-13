package ca.ulaval.glo4002.booking.domain.passes.passNumber;

public class PassNumber {

    private long value;

    public PassNumber(long value) {
        this.value = value;
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
