package ca.ulaval.glo4002.booking.domain.pass_ordering.passes.pass_types;

import java.time.LocalDateTime;

public class SuperNovaSinglePass extends SinglePass {

    public SuperNovaSinglePass(LocalDateTime eventDate) {
        super(eventDate);
        this.price = 150000;
    }
}
