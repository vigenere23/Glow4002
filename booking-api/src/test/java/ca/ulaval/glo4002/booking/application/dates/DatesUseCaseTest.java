package ca.ulaval.glo4002.booking.application.dates;

import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.booking.domain.dates.FestivalDates;

@ExtendWith(MockitoExtension.class)
public class DatesUseCaseTest {

    private final static LocalDate START_DATE = LocalDate.now();
    private final static LocalDate END_DATE = START_DATE.plusDays(4);

    @Mock FestivalDates festivalDates;
    @InjectMocks DatesUseCase datesUseCase;

    @Test
    public void whenUpdatingFestivalDates_itUpdatesFestivalStartDate() {
        datesUseCase.updateFestivalDates(START_DATE, END_DATE);
        verify(festivalDates).updateStartDate(START_DATE);
    }

    @Test
    public void whenUpdatingFestivalDates_itUpdatesFestivalEndDate() {
        datesUseCase.updateFestivalDates(START_DATE, END_DATE);
        verify(festivalDates).updateEndDate(END_DATE);
    }
}
