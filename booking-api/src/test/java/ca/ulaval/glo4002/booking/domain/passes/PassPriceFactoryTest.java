package ca.ulaval.glo4002.booking.domain.passes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Price;

public class PassPriceFactoryTest {

    private PassPriceFactory passPriceFactory;

    @BeforeEach
    public void setupPassPriceFactory() {
        passPriceFactory = new PassPriceFactory();
    }

    @Test
    public void givenNebulaSinglePass_whenCreatingPrice_itReturnsPriceOf50000() {
        Price price = passPriceFactory.create(PassOption.SINGLE_PASS, PassCategory.NEBULA);
        Price expectedPrice = new Price(50000);
        
        assertEquals(expectedPrice, price);
    }

    @Test
    public void givenNebulaPackagePass_whenCreatingPrice_itReturnsPriceOf250000() {
        Price price = passPriceFactory.create(PassOption.PACKAGE, PassCategory.NEBULA);
        Price expectedPrice = new Price(250000);

        assertEquals(expectedPrice, price);
    }

    @Test
    public void givenSupergiantSinglePass_whenCreatingPrice_itReturnsPriceOf100000() {
        Price price = passPriceFactory.create(PassOption.SINGLE_PASS, PassCategory.SUPERGIANT);
        Price expectedPrice = new Price(100000);

        assertEquals(expectedPrice, price);
    }

    @Test
    public void givenSupergiantPackagePass_whenCreatingPrice_itReturnsPriceOf500000() {
        Price price = passPriceFactory.create(PassOption.PACKAGE, PassCategory.SUPERGIANT);
        Price expectedPrice = new Price(500000);

        assertEquals(expectedPrice, price);
    }

    @Test
    public void givenSupernovaSinglePass_whenCreatingPrice_itReturnsPriceOf150000() {
        Price price = passPriceFactory.create(PassOption.SINGLE_PASS, PassCategory.SUPERNOVA);
        Price expectedPrice = new Price(150000);

        assertEquals(expectedPrice, price);
    }

    @Test
    public void givenSupernovaPackagePass_whenCreatingPrice_itReturnsPriceOf700000() {
        Price price = passPriceFactory.create(PassOption.PACKAGE, PassCategory.SUPERNOVA);
        Price expectedPrice = new Price(700000);

        assertEquals(expectedPrice, price);
    }
}
