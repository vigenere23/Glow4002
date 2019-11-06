package ca.ulaval.glo4002.booking.domain.transport;

public enum ShuttleCategory {
    ET_SPACESHIP("ET Spaceship"),
    SPACE_X("SpaceX"),
    MILLENNIUM_FALCON("Millennium Falcon");
    
    private final String text;

    private ShuttleCategory(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static ShuttleCategory artistShuttle(int numberPeople) {
        return numberPeople == 1 ? ET_SPACESHIP : MILLENNIUM_FALCON;
    }
}
