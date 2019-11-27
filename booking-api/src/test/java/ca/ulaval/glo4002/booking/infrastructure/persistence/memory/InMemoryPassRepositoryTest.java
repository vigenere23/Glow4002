package ca.ulaval.glo4002.booking.infrastructure.persistence.memory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassRepository;
import ca.ulaval.glo4002.booking.domain.passes.pass_number.PassNumber;
import ca.ulaval.glo4002.booking.domain.passes.pass_number.PassNumberFactory;

public class InMemoryPassRepositoryTest {

    private final static LocalDate SOME_DATE = LocalDate.now();

    private PassRepository passRepository;
    private PassNumberFactory passNumberFactory;

    @BeforeEach
    public void setupPassRepository() {
        passRepository = new InMemoryPassRepository();
        passNumberFactory = new PassNumberFactory(new AtomicLong(0));
    }

    @Test
    public void givenNoPasses_whenFindingByAttendedDate_itShouldReturnNoResult() {
        List<Pass> passes = passRepository.findAttendingAtDate(SOME_DATE);
        assertEquals(0, passes.size());
    }

    @Test
    public void givenOnlyPassesAttendingAtADate_whenFindingByAttendedDateWithThatDate_itShouldReturnAllPasses() {
        int numberOfPasses = 10;
        addPasses(numberOfPasses, true);

        List<Pass> passes = passRepository.findAttendingAtDate(SOME_DATE);

        assertEquals(numberOfPasses, passes.size());
    }

    @Test
    public void givenOnlyPassesNotAttendingAtADate_whenFindingByAttendedDateWithThatDate_itShouldReturnNoResult() {
        int numberOfPassesNotOfDate = 10;
        addPasses(numberOfPassesNotOfDate, false);

        List<Pass> passes = passRepository.findAttendingAtDate(SOME_DATE);

        assertEquals(0, passes.size());
    }

    @Test
    public void givenPassesAttendingAndNotAttendingAtADate_whenFindingByAttendedDateWithThatDate_itShouldReturnOnlyThePassesOfThatDate() {
        int numberOfPassesOfDate = 10;
        int numberOfPassesNotOfDate = 5;
        addPasses(numberOfPassesOfDate, true);
        addPasses(numberOfPassesNotOfDate, false);

        List<Pass> passes = passRepository.findAttendingAtDate(SOME_DATE);

        assertEquals(numberOfPassesOfDate, passes.size());
    }

    private void addPasses(int numberOfPasses, boolean isAttendingAtDate) {
        for (int i = 0; i < numberOfPasses; i++) {
            Pass pass = mock(Pass.class);
            PassNumber passNumber = passNumberFactory.create();
            when(pass.getPassNumber()).thenReturn(passNumber);
            when(pass.isAttendingAtDate(any(LocalDate.class))).thenReturn(isAttendingAtDate);
            passRepository.add(pass);
        }
    }
}
