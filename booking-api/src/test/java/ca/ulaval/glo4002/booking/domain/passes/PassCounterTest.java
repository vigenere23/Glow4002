package ca.ulaval.glo4002.booking.domain.passes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class PassCounterTest {

    private final static LocalDate SOME_DATE = LocalDate.of(2050, 07, 22);

    private PassCounter passCounter = new PassCounter();
    private List<Pass> passes = new ArrayList<>();

    @BeforeEach
    public void setUpPassCounter() {
        Pass pass = mock(Pass.class);
        passes.add(pass);

        when(pass.isOfDate(SOME_DATE)).thenReturn(true);
    }

    @Test
    public void givenPassWithOneEventDate_whenCountingAttendees_thenReturnOneAttendee() {
        int attendees = passCounter.countFestivalAttendeesForOneDay(passes, SOME_DATE);
        int expectedAttendees = 1;

        assertEquals(expectedAttendees, attendees);
    }
}