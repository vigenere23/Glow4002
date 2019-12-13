package ca.ulaval.glo4002.booking.domain.program;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.booking.domain.artists.Artist;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.exceptions.ItemNotFound;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassRepository;
import ca.ulaval.glo4002.booking.domain.program.exceptions.InvalidProgramException;

@ExtendWith(MockitoExtension.class)
public class ProgramDayFactoryTest {

    private final static String ARTIST_NAME = "John pekc";
    private static final LocalDate EVENT_DATE = LocalDate.now();
    private static final Activity ACTIVITY = Activity.CARDIO;
    private static final int NUMBER_OF_ATTENDEES = 12;
    
    @Mock List<Pass> attendees;
    @Mock Artist artist;
    @Mock PassRepository passRepository;
    @Mock ArtistRepository artistRepository;
    @InjectMocks ProgramDayFactory programDayFactory;

    @Test
    public void givenNoArtistFound_whenCreating_itThrowsAnInvalidProgramException() {
        doThrow(ItemNotFound.class).when(artistRepository).findByName(ARTIST_NAME);
        assertThatExceptionOfType(InvalidProgramException.class).isThrownBy(() -> {
            programDayFactory.create(EVENT_DATE, ARTIST_NAME, ACTIVITY);
        });
    }

    @Test
    public void whenCreating_itCreatesProgramDayWithRetreivedArtistFromRepository() {
        programDayFactory.create(EVENT_DATE, ARTIST_NAME, ACTIVITY);
        verify(artistRepository).findByName(ARTIST_NAME);
    }

    @Test
    public void whenCreating_itCreatesProgramDayWithRetreivedNumberOfAttendeesFromRepository() {
        programDayFactory.create(EVENT_DATE, ARTIST_NAME, ACTIVITY);
        verify(passRepository).findAttendingAtDate(EVENT_DATE);
    }

    @Test
    public void whenCreating_itReturnsAProgramDayWithPassedParams() {
        when(attendees.size()).thenReturn(NUMBER_OF_ATTENDEES);
        when(passRepository.findAttendingAtDate(EVENT_DATE)).thenReturn(attendees);
        when(artistRepository.findByName(ARTIST_NAME)).thenReturn(artist);
        
        ProgramDay programDay = programDayFactory.create(EVENT_DATE, ARTIST_NAME, ACTIVITY);

        assertThat(programDay.getArtist()).isEqualTo(artist);
        assertThat(programDay.getDate()).isEqualTo(EVENT_DATE);
        
    }
}
