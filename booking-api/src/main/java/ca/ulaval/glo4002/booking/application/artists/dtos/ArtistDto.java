package ca.ulaval.glo4002.booking.application.artists.dtos;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.transport.PassengerNumber;

public class ArtistDto {

    public String name;
    public int popularity;
    public float price;
    public long passengerNumber;
    public int groupSize;

    public ArtistDto(String name, int popularity, Price price, PassengerNumber passengerNumber, int groupSize) {
        this.name = name;
        this.popularity = popularity;
        this.price = price.getRoundedAmountFromCurrencyScale();
        this.passengerNumber = passengerNumber.getValue();
        this.groupSize = groupSize;
    }
}
