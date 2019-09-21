package ca.ulaval.glo4002.booking.domain.pass_ordering.passes.pass_types;

import java.time.LocalDateTime;

public class SuperGiantSinglePass extends SinglePass {

    public SuperGiantSinglePass(LocalDateTime eventDate) {
        super(eventDate);
        this.price = 100000;
    }
}
