package ca.ulaval.glo4002.booking.domain.pass_ordering.passes;

import java.time.LocalDateTime;

import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.factories.PackagePassFactory;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.factories.SinglePassFactory;

public abstract class PassFactory {

    public static Pass newPass(PassCategory category) {
        return new PackagePassFactory().newPass(category);
    }

    public static Pass newPass(PassCategory category, LocalDateTime eventDate) {
        return new SinglePassFactory().newPass(category, eventDate);
    }
}
