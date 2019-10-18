package ca.ulaval.glo4002.booking.application.program;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.transport.TransportRequester;

public class programUseCase {
    private final TransportRequester transportRequester;
    private final OxygenRequester oxygenRequester;

    public programUseCase(TransportRequester transportRequester, OxygenRequester oxygenRequester) {
        this.transportRequester = transportRequester;
        this.oxygenRequester = oxygenRequester;
    }

    public void orchestProgramCreation() {
        // TODO
    }
}
