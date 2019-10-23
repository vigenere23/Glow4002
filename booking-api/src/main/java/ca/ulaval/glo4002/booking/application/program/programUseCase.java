package ca.ulaval.glo4002.booking.application.program;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester2;
import ca.ulaval.glo4002.booking.domain.transport.TransportRequester;

public class programUseCase {
    private final TransportRequester transportRequester;
    private final OxygenRequester2 oxygenRequester;

    public programUseCase(TransportRequester transportRequester, OxygenRequester2 oxygenRequester) {
        this.transportRequester = transportRequester;
        this.oxygenRequester = oxygenRequester;
    }

    public void orchestProgramCreation() {
        // TODO
    }
}
