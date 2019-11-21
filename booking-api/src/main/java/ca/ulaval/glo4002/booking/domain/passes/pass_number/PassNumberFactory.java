package ca.ulaval.glo4002.booking.domain.passes.pass_number;

import java.util.concurrent.atomic.AtomicLong;

public class PassNumberFactory {

    private AtomicLong numberGenerator;

    public PassNumberFactory(AtomicLong numberGenerator) {
        this.numberGenerator = numberGenerator;
    }

    public PassNumber create() {
        return new PassNumber(numberGenerator.getAndIncrement());
    }
}
