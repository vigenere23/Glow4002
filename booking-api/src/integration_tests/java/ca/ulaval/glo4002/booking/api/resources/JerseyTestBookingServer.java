package ca.ulaval.glo4002.booking.interfaces.rest.resources;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.booking.BookingServer;
import com.google.gson.Gson;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

public class JerseyTestBookingServer extends JerseyTest {
    protected static final String ORDERS_URL = "/orders";
    protected static final String SINGLE_PASS_OPTION = "singlePass";
    protected static final String PACKAGE_PASS_OPTION = "package";

    static
    {
        System.setProperty("jersey.config.test.container.port", "8181");
    }

    @BeforeEach
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @AfterEach
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected Application configure() {
        return new BookingServer().generateResourceConfig();
    }

    protected Response postSinglePassOrder(String orderDate, String passCategory, List<String> eventDates) {
        return postPassOrderWithEventDates(orderDate, passCategory, eventDates, SINGLE_PASS_OPTION);
    }

    protected Response postPackagePassOrder(String orderDate, String passCategory) {
        return target(ORDERS_URL).request().post(Entity.json("{\"orderDate\":\"" + orderDate + "\",\"vendorCode\":\"TEAM\",\"passes\":"+
                "{\"passCategory\":\"" + passCategory + "\",\"passOption\":\"package\"}}"));
    }

    protected Response postPassOrderWithEventDates(String orderDate, String passCategory, List<String> eventDates, String passOption) {
        String jsonEventDates = new Gson().toJson(eventDates);
        return target(ORDERS_URL).request().post(Entity.json("{\"orderDate\":\"" + orderDate + "\",\"vendorCode\":\"TEAM\",\"passes\":"+
                "{\"passCategory\":\"" + passCategory + "\",\"passOption\":\"" + passOption + "\",\"eventDates\":" + jsonEventDates + "}}"));
    }
}
