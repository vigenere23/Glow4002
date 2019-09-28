package ca.ulaval.glo4002.booking.domain.passOrdering.passes;

import java.time.LocalDateTime;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories.PackagePassFactory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories.SinglePassFactory;

public abstract class PassFactory {

    public static Pass newPass(PassCategory category) {
        return new PackagePassFactory().create(category);
    }

    public static Pass newPass(PassCategory category, LocalDateTime eventDate) {
        return new SinglePassFactory().create(category, eventDate);
    }
}
