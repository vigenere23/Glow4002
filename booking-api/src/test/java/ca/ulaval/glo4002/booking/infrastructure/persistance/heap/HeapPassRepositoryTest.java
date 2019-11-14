package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import static org.junit.Assert.assertEquals;
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
import ca.ulaval.glo4002.booking.domain.passes.passNumber.PassNumber;
import ca.ulaval.glo4002.booking.domain.passes.passNumber.PassNumberFactory;

public class HeapPassRepositoryTest {

    private final static LocalDate SOME_DATE = LocalDate.now();

    private PassRepository passRepository;
    private PassNumberFactory passNumberFactory;

    @BeforeEach
    public void setupPassRepository() {
        passRepository = new HeapPassRepository();
        passNumberFactory = new PassNumberFactory(new AtomicLong(0));
    }

    @Test
    public void givenNoPasses_whenFindingByAttendedDate_itShouldReturnNoResult() {
        List<Pass> passes = passRepository.findAttendingAtDate(SOME_DATE);
        assertEquals(passes.size(), 0);
    }

    @Test
    public void givenOnlyPassesOfDate_whenFindingByAttendedDateWithThatDate_itShouldReturnAllPasses() {
        int numberOfPasses = 10;
        addPasses(numberOfPasses, true);

        List<Pass> passes = passRepository.findAttendingAtDate(SOME_DATE);

        assertEquals(passes.size(), numberOfPasses);
    }

    @Test
    public void givenPassesOfDateAndNotOfDate_whenFindingByAttendedDateWithThatDate_itShouldReturnOnlyThePassesOfDate() {
        int numberOfPassesOfDate = 10;
        int numberOfPassesNotOfDate = 5;
        addPasses(numberOfPassesOfDate, true);
        addPasses(numberOfPassesNotOfDate, false);

        List<Pass> passes = passRepository.findAttendingAtDate(SOME_DATE);

        assertEquals(passes.size(), numberOfPassesOfDate);
    }

    private void addPasses(int numberOfPasses, boolean isAttendingAtDate) {
        for (int i = 0; i < numberOfPasses; i++) {
            Pass pass = mock(Pass.class);
            PassNumber passNumber = passNumberFactory.create();
            when(pass.getPassNumber()).thenReturn(passNumber);
            when(pass.isAttendingAtDate(any(LocalDate.class))).thenReturn(isAttendingAtDate);
            passRepository.save(pass);
        }
    }
}
