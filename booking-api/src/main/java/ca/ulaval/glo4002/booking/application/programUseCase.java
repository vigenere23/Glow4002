package ca.ulaval.glo4002.booking.application;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenProducer;
import ca.ulaval.glo4002.booking.domain.transport.TransportRequester;

public class programUseCase {
    private final TransportRequester transportRequester;
    private final OxygenProducer oxygenProducer;

    public programUseCase(TransportRequester transportRequester, OxygenProducer oxygenProducer) {
        this.transportRequester = transportRequester;
        this.oxygenProducer = oxygenProducer;
    }

    public void orchestProgramCreation() {
        // TODO
    }
}
