package ca.ulaval.glo4002.booking.interfaces.rest.resources.orders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.booking.interfaces.rest.resources.JerseyTestBookingServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class OrdersResourceIT extends JerseyTestBookingServer {

    private static final String LOCAL_HOST = "http://localhost:8181";
    private static final String ORDERS_FULL_URL = LOCAL_HOST + ORDERS_URL + "/";
    private static final int HTTP_VALID_REQUEST = Response.Status.CREATED.getStatusCode();
    private static final int HTTP_BAD_REQUEST = Response.Status.BAD_REQUEST.getStatusCode();
    private static final String SOME_PASS_CATEGORY = "supergiant";
    private static final String SUPERGIANT_PASS_CATEGORY = "supergiant";
    private static final String SUPERNOVA_PASS_CATEGORY = "supernova";
    private static final String NEBULA_PASS_CATEGORY = "nebula";
    private static final String SOME_INVALID_PASS_CATEGORY = "somethingWrong";
    private static final String SOME_INVALID_PASS_OPTION = "somethingElseWrong";
    private static final String SOME_VALID_ORDER_DATE = "2050-05-21T15:23:20.142Z";
    private static final String SOME_INVALID_ORDER_DATE = "2050-07-17T15:23:20.142Z";
    private static final String INVALID_ORDER_DATE_ERROR = "INVALID_ORDER_DATE";
    private static final String INVALID_ORDER_DATE_MESSAGE = "order date should be between January 18 2050 and July 16 2050";
    private static final String INVALID_EVENT_DATE_ERROR = "INVALID_EVENT_DATE";
    private static final String INVALID_EVENT_DATE_MESSAGE = "event date should be between July 17 2050 and July 24 2050";
    private static final String INVALID_REQUEST_ERROR = "INVALID_FORMAT";
    private static final String INVALID_REQUEST_MESSAGE = "invalid format";
    private static double NEBULA_PACKAGE_PASS_COST = 250000;
    private static double NEBULA_SINGLE_PASS_COST = 50000;
    private static double NEBULA_SINGLE_PASS_COST_DISCOUNT = (NEBULA_SINGLE_PASS_COST * 0.9);
    private static double SUPERNOVA_PACKAGE_PASS_COST = 700000;
    private static double SUPERNOVA_SINGLE_PASS_COST = 150000;
    private static double SUPERGIANT_PACKAGE_PASS_COST = 500000;
    private static double SUPERGIANT_SINGLE_PASS_COST = 100000;
    private static double SUPERGIANT_SINGLE_PASS_COST_DISCOUNT = 90000;
    private List<String> someValidEventDates = new ArrayList<>();
    private List<String> someOutOfFestivalEventDates = new ArrayList<>();
    private List<String> someInvalidEventDates = new ArrayList<>();
    private List<String> threeValidEventDates = new ArrayList<>();
    private List<String> fourValidEventDates = new ArrayList<>();
    private List<String> fiveValidEventDates = new ArrayList<>();

    @BeforeEach
    public void setUpEventDates() {
        initializeValidEventDates();
        initializeOutOfFestivalEventDates();
        initializeInvalidEventDates();
        generateEventDates(threeValidEventDates, 3);
        generateEventDates(fourValidEventDates, 4);
        generateEventDates(fiveValidEventDates, 5);
    }

    @Test
    public void whenInvalidOrderDateInvalid_thenBadRequest() {
        Response response = postSinglePassOrder(SOME_INVALID_ORDER_DATE, SOME_PASS_CATEGORY, someValidEventDates);
        assertEquals(HTTP_BAD_REQUEST, response.getStatus());
    }

    @Test
    public void whenInvalidOrderDate_thenInvalidOrderDateError() {
        Response response = postSinglePassOrder(SOME_INVALID_ORDER_DATE, SOME_PASS_CATEGORY, someValidEventDates);
        String errorResponse = response.readEntity(String.class);

        String expectedResponse = getErrorResponse(INVALID_ORDER_DATE_ERROR, INVALID_ORDER_DATE_MESSAGE);
        assertEquals(expectedResponse, errorResponse);
    }

    @Test
    public void whenOutOfFestivalEventDate_thenBadRequest() {
        Response response = postSinglePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY, someOutOfFestivalEventDates);
        assertEquals(HTTP_BAD_REQUEST, response.getStatus());
    }

    @Test
    public void whenOutOfFestivalEventDate_thenInvalidEventDateError() {
        Response response = postSinglePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY, someOutOfFestivalEventDates);
        String errorResponse = response.readEntity(String.class);

        String expectedResponse = getErrorResponse(INVALID_EVENT_DATE_ERROR, INVALID_EVENT_DATE_MESSAGE);
        assertEquals(expectedResponse, errorResponse);
    }

    @Test
    public void whenInvaliRequest_thenBadRequest() {
        Response response = postSinglePassOrder(SOME_VALID_ORDER_DATE, SOME_INVALID_PASS_CATEGORY, someValidEventDates);
        assertEquals(HTTP_BAD_REQUEST, response.getStatus());
    }

    @Test
    public void whenInvalidEventDate_thenInvalidFormatError() {
        Response response = postSinglePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY, someInvalidEventDates);
        String errorResponse = response.readEntity(String.class);

        String expectedResponse = getErrorResponse(INVALID_REQUEST_ERROR, INVALID_REQUEST_MESSAGE);
        assertEquals(expectedResponse, errorResponse);
    }

    @Test
    public void whenInvalidPassCategory_thenInvalidFormatError() {
        Response response = postSinglePassOrder(SOME_VALID_ORDER_DATE, SOME_INVALID_PASS_CATEGORY, someValidEventDates);
        String errorResponse = response.readEntity(String.class);

        String expectedResponse = getErrorResponse(INVALID_REQUEST_ERROR, INVALID_REQUEST_MESSAGE);
        assertEquals(expectedResponse, errorResponse);
    }

    @Test
    public void whenInvalidPassOption_thenInvalidFormatError() {
        Response response = postPassOrderWithEventDates(SOME_VALID_ORDER_DATE, SOME_INVALID_PASS_CATEGORY, someValidEventDates, SOME_INVALID_PASS_OPTION);
        String errorResponse = response.readEntity(String.class);

        String expectedResponse = getErrorResponse(INVALID_REQUEST_ERROR, INVALID_REQUEST_MESSAGE);
        assertEquals(expectedResponse, errorResponse);
    }

    @Test
    public void whenOrderSinglePass_thenRightLocationHeader() {
        Response response = postSinglePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY, someValidEventDates);

        String locationHeader = response.getHeaders().get("Location").get(0).toString();
        assertTrue(locationHeader.startsWith(ORDERS_FULL_URL));
    }

    @Test
    public void whenOrderSinglePass_thenSuccessfulResponseCode() {
        Response response = postSinglePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY, someValidEventDates);
        assertEquals(HTTP_VALID_REQUEST, response.getStatus());
    }

    @Test
    public void whenOrderSinglePass_thenResponseHasExpectedDefinition() {
        Response postResponse = postSinglePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY, someValidEventDates);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        assertEquals(2, response.size());
    }

    @Test
    public void whenOrderSinglePass_thenResponseHasExpectedPassFormat() {
        Response postResponse = postSinglePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY, someValidEventDates);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        assertTrue(response.get("passes") instanceof List);
    }

    @Test
    public void whenOrderSinglePass_thenResponseHasExpectedSinglePassFormat() {
        Response postResponse = postSinglePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY, someValidEventDates);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        List passes = (List) response.get("passes");
        assertTrue(passes.get(0) instanceof Map);
    }

    @Test
    public void whenOrderSinglePass_thenResponseHasExpectedPassDefinition() {
        Response postResponse = postSinglePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY, someValidEventDates);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        List passes = (List) response.get("passes");
        Map somePass = (Map) passes.get(0);
        assertEquals(4, somePass.size());
    }

    @Test
    public void whenOrderSinglePass_thenResponseHasRightNumberOfPasses() {
        Response postResponse = postSinglePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY, someValidEventDates);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        List passes = (List) response.get("passes");
        int expectedNumberOfPasses = someValidEventDates.size();
        assertEquals(expectedNumberOfPasses, passes.size());
    }

    @Test
    public void whenOrderSinglePass_thenResponseHasRightFields() {
        Response postResponse = postSinglePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY, someValidEventDates);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        assertTrue(response.containsKey("orderPrice"));
        assertTrue(response.containsKey("passes"));
    }

    @Test
    public void whenOrderSinglePass_thenResponseHasRightPassFields() {
        Response postResponse = postSinglePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY, someValidEventDates);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        List passes = (List) response.get("passes");
        Map somePass = (Map) passes.get(0);
        assertTrue(somePass.containsKey("passCategory"));
        assertTrue(somePass.containsKey("passNumber"));
        assertTrue(somePass.containsKey("passOption"));
        assertTrue(somePass.containsKey("eventDate"));
    }

    @Test
    public void whenOrderSinglePass_thenResponseHasRightPassNumberFormat() {
        Response postResponse = postSinglePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY, someValidEventDates);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        List passes = (List) response.get("passes");
        Map somePass = (Map) passes.get(0);
        assertTrue(somePass.get("passNumber") instanceof Integer);
    }

    @Test
    public void whenOrderSinglePass_thenResponseHasRightPassCategory() {
        Response postResponse = postSinglePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY, someValidEventDates);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        List passes = (List) response.get("passes");
        Map somePass = (Map) passes.get(0);
        assertEquals(SOME_PASS_CATEGORY, somePass.get("passCategory"));
    }

    @Test
    public void whenOrderSinglePass_thenResponseHasRightPassOption() {
        Response postResponse = postSinglePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY, someValidEventDates);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        List passes = (List) response.get("passes");
        Map somePass = (Map) passes.get(0);
        assertEquals(SINGLE_PASS_OPTION, somePass.get("passOption"));
    }

    @Test
    public void whenOrderSinglePass_thenResponseHasRightEventDateFormat() {
        Response postResponse = postSinglePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY, someValidEventDates);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        List passes = (List) response.get("passes");
        Map somePass = (Map) passes.get(0);
        assertTrue(somePass.get("eventDate") instanceof String);
    }

    @Test
    public void whenOrderNebulaSinglePass_thenResponseHasRightPrice() {
        Response postResponse = postSinglePassOrder(SOME_VALID_ORDER_DATE, NEBULA_PASS_CATEGORY, threeValidEventDates);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        double expectedPrice = threeValidEventDates.size() * NEBULA_SINGLE_PASS_COST;
        assertEquals(expectedPrice, response.get("orderPrice"));
    }


    @Test
    public void whenOrderMoreThanThreeNebulaSinglePass_thenResponseHasRightPrice() {
        Response postResponse = postSinglePassOrder(SOME_VALID_ORDER_DATE, NEBULA_PASS_CATEGORY, fourValidEventDates);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        double expectedPrice = fourValidEventDates.size() * NEBULA_SINGLE_PASS_COST_DISCOUNT;
        assertEquals(expectedPrice, response.get("orderPrice"));
    }

    @Test
    public void whenOrderSupergiantSinglePass_thenResponseHasRightPrice() {
        Response postResponse = postSinglePassOrder(SOME_VALID_ORDER_DATE, SUPERGIANT_PASS_CATEGORY, someValidEventDates);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        double expectedPrice = someValidEventDates.size() * SUPERGIANT_SINGLE_PASS_COST;
        assertEquals(expectedPrice, response.get("orderPrice"));
    }

    @Test
    public void whenOrderMoreThanFiveSupergiantSinglePass_thenResponseHasRightPrice() {
        Response postResponse = postSinglePassOrder(SOME_VALID_ORDER_DATE, SUPERGIANT_PASS_CATEGORY, fiveValidEventDates);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        double expectedPrice = fiveValidEventDates.size() * SUPERGIANT_SINGLE_PASS_COST_DISCOUNT;
        assertEquals(expectedPrice, response.get("orderPrice"));
    }

    @Test
    public void whenOrderSupernovaSinglePass_thenResponseHasRightPrice() {
        Response postResponse = postSinglePassOrder(SOME_VALID_ORDER_DATE, SUPERNOVA_PASS_CATEGORY, someValidEventDates);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        double expectedPrice = someValidEventDates.size() * SUPERNOVA_SINGLE_PASS_COST;
        assertEquals(expectedPrice, response.get("orderPrice"));
    }

    @Test
    public void whenOrderPackagePassWithEventDates_thenInvalidFormatError() {
        Response response = postPassOrderWithEventDates(SOME_VALID_ORDER_DATE, SOME_INVALID_PASS_CATEGORY, someValidEventDates, PACKAGE_PASS_OPTION);
        String errorResponse = response.readEntity(String.class);

        String expectedResponse = getErrorResponse(INVALID_REQUEST_ERROR, INVALID_REQUEST_MESSAGE);
        assertEquals(expectedResponse, errorResponse);
    }

    @Test
    public void whenOrderPackagePass_thenRightLocationHeader() {
        Response response = postPackagePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY);

        String locationHeader = response.getLocation().toString();

        assertTrue(locationHeader.startsWith(ORDERS_FULL_URL));
    }

    @Test
    public void whenOrderPackagePass_thenSuccessfulResponseCode() {
        Response response = postPackagePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY);
        assertEquals(HTTP_VALID_REQUEST, response.getStatus());
    }

    @Test
    public void whenOrderPackagePass_thenResponseHasExpectedDefinition() {
        Response postResponse = postPackagePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        assertEquals(2, response.size());
    }

    @Test
    public void whenOrderPackagePass_thenResponseHasExpectedPassFormat() {
        Response postResponse = postPackagePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        assertTrue(response.get("passes") instanceof List);
    }

    @Test
    public void whenOrderPackagePass_thenResponseHasExpectedSinglePassFormat() {
        Response postResponse = postPackagePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        List passes = (List) response.get("passes");
        assertTrue(passes.get(0) instanceof Map);
    }

    @Test
    public void whenOrderPackagePass_thenResponseHasExpectedPassDefinition() {
        Response postResponse = postPackagePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        List passes = (List) response.get("passes");
        Map somePass = (Map) passes.get(0);

        assertEquals(3, somePass.size());
    }

    @Test
    public void whenOrderPackagePass_thenResponseHasRightPassNumberFormat() {
        Response postResponse = postPackagePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        List passes = (List) response.get("passes");
        Map somePass = (Map) passes.get(0);
        assertTrue(somePass.get("passNumber") instanceof Integer);
    }

    @Test
    public void whenOrderPackagePass_thenResponseHasRightPassCategory() {
        Response postResponse = postPackagePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        List passes = (List) response.get("passes");
        Map somePass = (Map) passes.get(0);
        assertEquals(SOME_PASS_CATEGORY, somePass.get("passCategory"));
    }

    @Test
    public void whenOrderPackagePass_thenResponseHasRightPassOption() {
        Response postResponse = postPackagePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        List passes = (List) response.get("passes");
        Map somePass = (Map) passes.get(0);
        assertEquals(PACKAGE_PASS_OPTION, somePass.get("passOption"));
    }

    @Test
    public void whenOrderPackagePass_thenResponseHasRightFields() {
        Response postResponse = postPackagePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        List passes = (List) response.get("passes");
        Map somePass = (Map) passes.get(0);

        assertTrue(response.containsKey("orderPrice"));
        assertTrue(somePass.containsKey("passCategory"));
        assertTrue(somePass.containsKey("passNumber"));
        assertTrue(somePass.containsKey("passOption"));
    }

    @Test
    public void whenOrderPackagePass_thenResponseHasRightNumberOfPasses() {
        Response postResponse = postPackagePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        List passes = (List) response.get("passes");
        assertEquals(1, passes.size());
    }

    @Test
    public void whenOrderNebulaPackagePass_thenResponseHasRightPrice() {
        Response postResponse = postPackagePassOrder(SOME_VALID_ORDER_DATE, NEBULA_PASS_CATEGORY);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        assertEquals(NEBULA_PACKAGE_PASS_COST, response.get("orderPrice"));
    }

    @Test
    public void whenOrderSupergiantPackagePass_thenResponseHasRightPrice() {
        Response postResponse = postPackagePassOrder(SOME_VALID_ORDER_DATE, SUPERGIANT_PASS_CATEGORY);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        assertEquals(SUPERGIANT_PACKAGE_PASS_COST, response.get("orderPrice"));
    }

    @Test
    public void whenOrderSupernovaPackagePass_thenResponseHasRightPrice() {
        Response postResponse = postPackagePassOrder(SOME_VALID_ORDER_DATE, SUPERNOVA_PASS_CATEGORY);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        assertEquals(SUPERNOVA_PACKAGE_PASS_COST, response.get("orderPrice"));
    }

    private void initializeValidEventDates() {
        someValidEventDates.add("2050-07-17");
        someValidEventDates.add("2050-07-18");
    }

    private void initializeOutOfFestivalEventDates() {
        someOutOfFestivalEventDates.add("2050-07-17");
        someOutOfFestivalEventDates.add("2020-07-18");
    }

    private void initializeInvalidEventDates() {
        someInvalidEventDates.add("2050-07-17");
        someInvalidEventDates.add("hello");
    }

    private void generateEventDates(List<String> eventDates, int quantity) {
        String someValidDate = "2050-07-19";
        for (int i = 0; i < quantity; i++) {
            eventDates.add(someValidDate);
        }
    }

    private String getErrorResponse(String errorName, String description) {
        return "{\"error\":\"" + errorName + "\",\"description\":\"" + description + "\"}";
    }

    private String getPassNumber(Response response) {
        String locationHeader = response.getLocation().toString();
        return locationHeader.replace(ORDERS_FULL_URL, "");
    }

    private Map getMapPostResponse(String orderNumber) {
        return target(ORDERS_URL + "/" + orderNumber).request().get(Map.class);
    }
}
