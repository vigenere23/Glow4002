package ca.ulaval.glo4002.booking.api.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class OrdersIT extends JerseyTest {
    private static final String LOCAL_HOST = "http://localhost:8181";
    private static final String ORDERS_URL = "/orders";
    private static final int HTTP_VALID_REQUEST = 201;
    private static final int HTTP_BAD_REQUEST = 400;
    private static final String VENDOR_CODE = "TEAM";
    private static final String SINGLE_PASS_OPTION = "singlePass";
    private static final String SOME_PASS_CATEGORY = "supergiant";
    private static final String SOME_INVALID_PASS_CATEGORY = "somethingWrong";
    private static final String SOME_VALID_ORDER_DATE = "2050-05-21T15:23:20.142Z";
    private static final String SOME_INVALID_ORDER_DATE = "2050-07-17T15:23:20.142Z";
    private static final String INVALID_ORDER_DATE_ERROR = "INVALID_ORDER_DATE";
    private static final String INVALID_ORDER_DATE_MESSAGE = "order date should be between January 1 2050 and July 16 2050";
    private static final String INVALID_EVENT_DATE_ERROR = "INVALID_EVENT_DATE";
    private static final String INVALID_EVENT_DATE_MESSAGE = "event date should be between July 17 2050 and July 24 2050";
    private static final String INVALID_REQUEST_ERROR = "INVALID_FORMAT";
    private static final String INVALID_REQUEST_MESSAGE = "invalid format";
    private static final String FIRST_ORDER_NUMBER = VENDOR_CODE + "-0";
    private List<String> someValidEventDates = new ArrayList<>();
    private List<String> someInvalidEventDates = new ArrayList<>();

    static
    {
        System.setProperty("jersey.config.test.container.port", "8181");
    }

    @BeforeEach
    @Override
    public void setUp() throws Exception {
        super.setUp();
        initializeEventDates();
    }

    @AfterEach
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected Application configure() {
        MockedBookingServer mockedBookingServer = new MockedBookingServer();
        return mockedBookingServer.configure();
    }

    @Test
    public void whenOrderSinglePass_thenRightLocationHeader() throws Exception {
        Response response = postPassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY, SINGLE_PASS_OPTION, someValidEventDates);

        String locationHeader = response.getHeaders().get("Location").get(0).toString();
        String expectedLocation = LOCAL_HOST + ORDERS_URL + "/" + FIRST_ORDER_NUMBER;

        assertEquals(expectedLocation, locationHeader);
    }

    @Test
    public void whenOrderSinglePass_thenSuccessfulResponseCode() throws Exception {
        Response response = postPassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY, SINGLE_PASS_OPTION, someValidEventDates);

        assertEquals(HTTP_VALID_REQUEST, response.getStatus());
    }

    @Test
    public void whenInvalidOrderDateInvalid_thenBadRequest() throws Exception {
        Response response = postPassOrder(SOME_INVALID_ORDER_DATE, SOME_PASS_CATEGORY, SINGLE_PASS_OPTION, someValidEventDates);

        assertEquals(HTTP_BAD_REQUEST, response.getStatus());
    }

    @Test
    public void whenInvalidOrderDateInvalid_thenRightErrorType() throws Exception {
        Response response = postPassOrder(SOME_INVALID_ORDER_DATE, SOME_PASS_CATEGORY, SINGLE_PASS_OPTION, someValidEventDates);
        Map errorResponse = response.readEntity(Map.class);

        assertEquals(INVALID_ORDER_DATE_ERROR, errorResponse.get("error"));
    }

    @Test
    public void whenInvalidOrderDate_thenRightMessage() throws Exception {
        Response response = postPassOrder(SOME_INVALID_ORDER_DATE, SOME_PASS_CATEGORY, SINGLE_PASS_OPTION, someValidEventDates);
        Map errorResponse = response.readEntity(Map.class);

        assertEquals(INVALID_ORDER_DATE_MESSAGE, errorResponse.get("description"));
    }

    @Test
    public void whenInvalidEventDateInvalid_thenBadRequest() throws Exception {
        Response response = postPassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY, SINGLE_PASS_OPTION, someInvalidEventDates);

        assertEquals(HTTP_BAD_REQUEST, response.getStatus());
    }

    @Test
    public void whenInvalidEventDate_thenRightErrorType() throws Exception {
        Response response = postPassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY, SINGLE_PASS_OPTION, someInvalidEventDates);
        Map errorResponse = response.readEntity(Map.class);

        assertEquals(INVALID_EVENT_DATE_ERROR, errorResponse.get("error"));
    }

    @Test
    public void whenInvalidEventDate_thenRightMessage() throws Exception {
        Response response = postPassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY, SINGLE_PASS_OPTION, someInvalidEventDates);
        Map errorResponse = response.readEntity(Map.class);

        assertEquals(INVALID_EVENT_DATE_MESSAGE, errorResponse.get("description"));
    }

    @Test
    public void whenInvaliRequest_thenBadRequest() throws Exception {
        Response response = postPassOrder(SOME_VALID_ORDER_DATE, SOME_INVALID_PASS_CATEGORY, SINGLE_PASS_OPTION, someValidEventDates);

        assertEquals(HTTP_BAD_REQUEST, response.getStatus());
    }

    @Test
    public void whenInvaliRequest_thenRightErrorType() throws Exception {
        Response response = postPassOrder(SOME_VALID_ORDER_DATE, SOME_INVALID_PASS_CATEGORY, SINGLE_PASS_OPTION, someValidEventDates);
        Map errorResponse = response.readEntity(Map.class);

        assertEquals(INVALID_REQUEST_ERROR, errorResponse.get("error"));
    }

    @Test
    public void whenInvalidRequest_thenRightMessage() throws Exception {
        Response response = postPassOrder(SOME_VALID_ORDER_DATE, SOME_INVALID_PASS_CATEGORY, SINGLE_PASS_OPTION, someValidEventDates);
        Map errorResponse = response.readEntity(Map.class);

        assertEquals(INVALID_REQUEST_MESSAGE, errorResponse.get("description"));
    }

    @Test
    public void whenOrderSinglePass_thenAnswerHasRightFields() throws Exception {
        postPassOrder(SOME_VALID_ORDER_DATE, SOME_INVALID_PASS_CATEGORY, SINGLE_PASS_OPTION, someValidEventDates);
//        String response = getPostOrdersResponse(FIRST_ORDER_NUMBER);
        // TODO
    }

    private void initializeEventDates() {
        someValidEventDates.add("2050-07-17");
        someValidEventDates.add("2050-07-18");

        someInvalidEventDates.add("2050-07-17");
        someInvalidEventDates.add("2020-07-18");
    }

    private String getPostOrdersResponse(String orderNumber) {
        return target(ORDERS_URL + "/" + orderNumber).request().get(String.class);
    }

    public Response postPassOrder(String orderDate, String passCategory, String passOption, List<String> eventDates) throws Exception {
        String jsonEventDates = new Gson().toJson(eventDates);
        return target(ORDERS_URL).request().post(Entity.json("{\"orderDate\": \"" + orderDate + "\",\"vendorCode\": \"TEAM\",\"passes\":"+
                " {\"passCategory\": \"" + passCategory + "\",\"passOption\": \"" + passOption + "\",\"eventDates\": " + jsonEventDates + "}}"));
    }
}
