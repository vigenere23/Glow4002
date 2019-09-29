package ca.ulaval.glo4002.booking.domain.passOrdering.passes;

import java.time.OffsetDateTime;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories.PackagePassFactory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories.SinglePassFactory;

public abstract class PassFactory {

    public static Pass newPass(PassCategory category) {
        return new PackagePassFactory().create(category);
    }

    public static Pass newPass(PassCategory category, OffsetDateTime eventDate) {
        return new SinglePassFactory().create(category, eventDate);
    }
}
