package ca.ulaval.glo4002.booking.domain.pass_ordering.passes.pass_types;

import java.time.LocalDateTime;

public class NebulaSinglePass extends SinglePass {

    public NebulaSinglePass(LocalDateTime eventDate) {
        super(eventDate);
        this.price = 50000;
    }
}
