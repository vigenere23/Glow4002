package ca.ulaval.glo4002.booking.application;

import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;

public class ShuttleUseCase {
    private final ShuttleRepository shuttleRepository;

    // TODO create and bind
    public ShuttleUseCase(ShuttleRepository shuttleRepository) {
        this.shuttleRepository = shuttleRepository;
    }

    public void getShuttles() {
        // TODO
    }
}
