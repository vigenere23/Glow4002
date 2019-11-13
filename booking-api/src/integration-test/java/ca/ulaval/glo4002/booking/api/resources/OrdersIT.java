package ca.ulaval.glo4002.booking.api.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.booking.api.resources.orders.PackagePassOrder;
import ca.ulaval.glo4002.booking.api.resources.orders.SinglePassOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class OrdersIT extends MockedBookingServer {
    private static final String LOCAL_HOST = "http://localhost:8181";
    private static final String ORDERS_FULL_URL = LOCAL_HOST + ORDERS_URL + "/";
    private static final int HTTP_VALID_REQUEST = 201;
    private static final int HTTP_BAD_REQUEST = 400;
    private static final String SOME_PASS_CATEGORY = "supergiant";
    private static final String SUPERGIANT_PASS_CATEGORY = "supergiant";
    private static final String SUPERNOVA_PASS_CATEGORY = "supernova";
    private static final String NEBULA_PASS_CATEGORY = "nebula";
    private static final String SOME_INVALID_PASS_CATEGORY = "somethingWrong";
    private static final String SOME_VALID_ORDER_DATE = "2050-05-21T15:23:20.142Z";
    private static final String SOME_INVALID_ORDER_DATE = "2050-07-17T15:23:20.142Z";
    private static final String INVALID_ORDER_DATE_ERROR = "INVALID_ORDER_DATE";
    private static final String INVALID_ORDER_DATE_MESSAGE = "order date should be between January 1 2050 and July 16 2050";
    private static final String INVALID_EVENT_DATE_ERROR = "INVALID_EVENT_DATE";
    private static final String INVALID_EVENT_DATE_MESSAGE = "event date should be between July 17 2050 and July 24 2050";
    private static final String INVALID_REQUEST_ERROR = "INVALID_FORMAT";
    private static final String INVALID_REQUEST_MESSAGE = "invalid format";
    private static float NEBULA_PACKAGE_PASS_COST = 250000;
    private static float NEBULA_SINGLE_PASS_COST = 50000;
    private static float NEBULA_SINGLE_PASS_COST_DISCOUNT = (float) (NEBULA_SINGLE_PASS_COST * 0.9);
    private static float SUPERNOVA_PACKAGE_PASS_COST = 700000;
    private static float SUPERNOVA_SINGLE_PASS_COST = 150000;
    private static float SUPERGIANT_PACKAGE_PASS_COST = 500000;
    private static float SUPERGIANT_SINGLE_PASS_COST = 100000;
    private static float SUPERGIANT_SINGLE_PASS_COST_DISCOUNT = 90000;
    private List<String> someValidEventDates = new ArrayList<>();
    private List<String> someInvalidEventDates = new ArrayList<>();
    private List<String> moreThanFiveEventDates = new ArrayList<>();

    @BeforeEach
    public void setUpEventDates() {
        initializeValidEventDates();
        initializeInvalidEventDates();
        initializeMoreThanFiveValidEventDates();
    }

    @Override
    protected Application configure() {
        MockedBookingServer mockedBookingServer = new MockedBookingServer();
        return mockedBookingServer.configure();
    }

    @Test
    public void whenInvalidOrderDateInvalid_thenBadRequest() {
        Response response = postSinglePassOrder(SOME_INVALID_ORDER_DATE, SOME_PASS_CATEGORY, someValidEventDates);

        assertEquals(HTTP_BAD_REQUEST, response.getStatus());
    }

    @Test
    public void whenInvalidOrderDateInvalid_thenRightError() {
        Response response = postSinglePassOrder(SOME_INVALID_ORDER_DATE, SOME_PASS_CATEGORY, someValidEventDates);
        String errorResponse = response.readEntity(String.class);

        String expectedResponse = getErrorResponse(INVALID_ORDER_DATE_ERROR, INVALID_ORDER_DATE_MESSAGE);
        assertEquals(expectedResponse, errorResponse);
    }

    @Test
    public void whenInvalidEventDateInvalid_thenBadRequest() {
        Response response = postSinglePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY, someInvalidEventDates);

        assertEquals(HTTP_BAD_REQUEST, response.getStatus());
    }

    @Test
    public void whenInvalidEventDate_thenRightError() {
        Response response = postSinglePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY, someInvalidEventDates);
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
    public void whenInvalidPassCategory_thenRightError() {
        Response response = postSinglePassOrder(SOME_VALID_ORDER_DATE, SOME_INVALID_PASS_CATEGORY, someValidEventDates);
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
    public void whenOrderSinglePass_thenAnswerHasRightNumberOfFields() {
        Response postResponse = postSinglePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY, someValidEventDates);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        List passes = (List) response.get("passes");
        Map somePass = (Map) passes.get(0);

        assertEquals(2, response.size());
        assertEquals(4, somePass.size());
    }

    @Test
    public void whenOrderSinglePass_thenAnswerHasRightFields() {
        Response postResponse = postSinglePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY, someValidEventDates);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        List passes = (List) response.get("passes");
        Map somePass = (Map) passes.get(0);

        assertTrue(response.containsKey("orderPrice"));
        assertTrue(somePass.containsKey("passCategory"));
        assertTrue(somePass.containsKey("passNumber"));
        assertTrue(somePass.containsKey("passOption"));
        assertTrue(somePass.containsKey("eventDate"));
    }

    @Test
    public void whenOrderSinglePass_thenAnswerHasRightFormat() {
        Response postResponse = postSinglePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY, someValidEventDates);
        String orderNumber = getPassNumber(postResponse);

        SinglePassOrder response = getSinglePassOrderResponse(orderNumber);

        assertNotNull(response);
    }

    @Test
    public void whenOrderNebulaSinglePass_thenAnswerHasRightPrice() {
        Response postResponse = postSinglePassOrder(SOME_VALID_ORDER_DATE, NEBULA_PASS_CATEGORY, someValidEventDates);
        String orderNumber = getPassNumber(postResponse);

        SinglePassOrder response = getSinglePassOrderResponse(orderNumber);

        float expectedPrice = someValidEventDates.size() * NEBULA_SINGLE_PASS_COST;
        assertEquals(expectedPrice, response.orderPrice);
    }


    @Test
    public void whenOrderMoreThanThreeNebulaSinglePass_thenAnswerHasRightPrice() {
        Response postResponse = postSinglePassOrder(SOME_VALID_ORDER_DATE, NEBULA_PASS_CATEGORY, moreThanFiveEventDates);
        String orderNumber = getPassNumber(postResponse);

        SinglePassOrder response = getSinglePassOrderResponse(orderNumber);

        float expectedPrice = moreThanFiveEventDates.size() * NEBULA_SINGLE_PASS_COST_DISCOUNT;
        assertEquals(expectedPrice, response.orderPrice);
    }

    @Test
    public void whenOrderSupergiantSinglePass_thenAnswerHasRightPrice() {
        Response postResponse = postSinglePassOrder(SOME_VALID_ORDER_DATE, SUPERGIANT_PASS_CATEGORY, someValidEventDates);
        String orderNumber = getPassNumber(postResponse);

        SinglePassOrder response = getSinglePassOrderResponse(orderNumber);

        float expectedPrice = someValidEventDates.size() * SUPERGIANT_SINGLE_PASS_COST;
        assertEquals(expectedPrice, response.orderPrice);
    }


    @Test
    public void whenOrderMoreThanThreeSupergiantSinglePass_thenAnswerHasRightPrice() {
        Response postResponse = postSinglePassOrder(SOME_VALID_ORDER_DATE, SUPERGIANT_PASS_CATEGORY, moreThanFiveEventDates);
        String orderNumber = getPassNumber(postResponse);

        SinglePassOrder response = getSinglePassOrderResponse(orderNumber);

        float expectedPrice = moreThanFiveEventDates.size() * SUPERGIANT_SINGLE_PASS_COST_DISCOUNT;
        assertEquals(expectedPrice, response.orderPrice);
    }

    @Test
    public void whenOrderSupernovaSinglePass_thenAnswerHasRightPrice() {
        Response postResponse = postSinglePassOrder(SOME_VALID_ORDER_DATE, SUPERNOVA_PASS_CATEGORY, someValidEventDates);
        String orderNumber = getPassNumber(postResponse);

        SinglePassOrder response = getSinglePassOrderResponse(orderNumber);

        float expectedPrice = someValidEventDates.size() * SUPERNOVA_SINGLE_PASS_COST;
        assertEquals(expectedPrice, response.orderPrice);
    }

    @Test
    public void whenOrderPackagePassWithEventDates_thenRightError() {
        Response response = postPassOrderWithEventDates(SOME_VALID_ORDER_DATE, SOME_INVALID_PASS_CATEGORY, someValidEventDates, "package");
        String errorResponse = response.readEntity(String.class);

        String expectedResponse = getErrorResponse(INVALID_REQUEST_ERROR, INVALID_REQUEST_MESSAGE);
        assertEquals(expectedResponse, errorResponse);
    }

    @Test
    public void whenOrderPackagePass_thenRightLocationHeader() {
        Response response = postPackagePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY);

        String locationHeader = response.getHeaders().get("Location").get(0).toString();

        assertTrue(locationHeader.startsWith(ORDERS_FULL_URL));
    }

    @Test
    public void whenOrderPackagePass_thenSuccessfulResponseCode() {
        Response response = postPackagePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY);

        assertEquals(HTTP_VALID_REQUEST, response.getStatus());
    }

    @Test
    public void whenOrderPackagePass_thenAnswerHasRightNumberOfFields() {
        Response postResponse = postPackagePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY);
        String orderNumber = getPassNumber(postResponse);

        Map response = getMapPostResponse(orderNumber);

        List passes = (List) response.get("passes");
        Map somePass = (Map) passes.get(0);

        assertEquals(2, response.size());
        assertEquals(3, somePass.size());
    }

    @Test
    public void whenOrderPackagePass_thenAnswerHasRightFields() {
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
    public void whenOrderPackagePass_thenAnswerHasRightFormat() {
        Response postResponse = postPackagePassOrder(SOME_VALID_ORDER_DATE, SOME_PASS_CATEGORY);
        String orderNumber = getPassNumber(postResponse);

        PackagePassOrder response = getPackagePassOrderResponse(orderNumber);

        assertNotNull(response);
    }

    @Test
    public void whenOrderNebulaPackagePass_thenAnswerHasRightPrice() {
        Response postResponse = postPackagePassOrder(SOME_VALID_ORDER_DATE, NEBULA_PASS_CATEGORY);
        String orderNumber = getPassNumber(postResponse);

        SinglePassOrder response = getSinglePassOrderResponse(orderNumber);

        assertEquals(NEBULA_PACKAGE_PASS_COST, response.orderPrice);
    }

    @Test
    public void whenOrderSupergiantPackagePass_thenAnswerHasRightPrice() {
        Response postResponse = postPackagePassOrder(SOME_VALID_ORDER_DATE, SUPERGIANT_PASS_CATEGORY);
        String orderNumber = getPassNumber(postResponse);

        SinglePassOrder response = getSinglePassOrderResponse(orderNumber);

        assertEquals(SUPERGIANT_PACKAGE_PASS_COST, response.orderPrice);
    }

    @Test
    public void whenOrderSupernovaPackagePass_thenAnswerHasRightPrice() {
        Response postResponse = postPackagePassOrder(SOME_VALID_ORDER_DATE, SUPERNOVA_PASS_CATEGORY);
        String orderNumber = getPassNumber(postResponse);

        SinglePassOrder response = getSinglePassOrderResponse(orderNumber);

        assertEquals(SUPERNOVA_PACKAGE_PASS_COST, response.orderPrice);
    }

    private void initializeValidEventDates() {
        someValidEventDates.add("2050-07-17");
        someValidEventDates.add("2050-07-18");
    }

    private void initializeInvalidEventDates() {
        someInvalidEventDates.add("2050-07-17");
        someInvalidEventDates.add("2020-07-18");
    }

    private void initializeMoreThanFiveValidEventDates() {
        String someValidDate = "2050-07-19";
        for (int i = 0; i < 6; i++) {
            moreThanFiveEventDates.add(someValidDate);
        }
    }

    private String getErrorResponse(String errorName, String description) {
        return "{\"error\":\"" + errorName + "\",\"description\":\"" + description + "\"}";
    }

    private String getPassNumber(Response response) {
        String locationHeader = response.getHeaders().get("Location").get(0).toString();
        return locationHeader.replace(ORDERS_FULL_URL, "");
    }

    private Map getMapPostResponse(String orderNumber) {
        return target(ORDERS_URL + "/" + orderNumber).request().get(Map.class);
    }

    private SinglePassOrder getSinglePassOrderResponse(String orderNumber) {
        return target(ORDERS_URL + "/" + orderNumber).request().get(SinglePassOrder.class);
    }

    private PackagePassOrder getPackagePassOrderResponse(String orderNumber) {
        return target(ORDERS_URL + "/" + orderNumber).request().get(PackagePassOrder.class);
    }
}
