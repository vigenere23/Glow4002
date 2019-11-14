package ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dtoMappers;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.artists.ArtistProgramInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingInformation;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dto.ArtistDto;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dto.ArtistInformationMapper;

public class ArtistInformationMapperTest {

    private final static int SOME_ARTIST_ID = 1;
    private final static String SOME_ARTIST_NAME = "Ginette Reno";
    private final static int SOME_NUMBER_OF_PEOPLE = 1;
    private final static String SOME_MUSIC_STYLE = "Pop";
    private final static float SOME_PRICE = 50000;
    private final static int SOME_POPULARITY_RANK = 1;
    private final static Object SOME_AVAILABILITY = new Object();

    private ArtistInformationMapper artistInformationMapper = new ArtistInformationMapper();
    private ArtistProgramInformation programInformation;
    private ArtistRankingInformation rankingInformation;
    private ArtistDto artistDto;

    @BeforeEach
    public void setupArtistInformationMapper() {

        artistDto = new ArtistDto(SOME_ARTIST_ID, SOME_ARTIST_NAME, SOME_NUMBER_OF_PEOPLE, SOME_MUSIC_STYLE, SOME_PRICE, 
        SOME_POPULARITY_RANK, SOME_AVAILABILITY);
    }

    @Test
    public void whenMappingProgramFromArtistDto_thenReturnEquivalentArtistProgramInformation() {
        programInformation = artistInformationMapper.programFromDto(artistDto);

        assertEquals(SOME_ARTIST_NAME, programInformation.getArtistName());
        assertEquals(SOME_ARTIST_ID, programInformation.getPassengerNumber().getValue());
        assertEquals(SOME_NUMBER_OF_PEOPLE, programInformation.getGroupSize());
    }

    @Test
    public void whenMappingRankingFromArtistDto_thenReturnEquivalentArtistRankingInformation() {
        rankingInformation = artistInformationMapper.rankingFromDto(artistDto);

        assertEquals(SOME_ARTIST_NAME, rankingInformation.getArtistName());
        assertEquals(SOME_POPULARITY_RANK, rankingInformation.getPopularity());
        assertEquals(SOME_PRICE, rankingInformation.getPrice(), 0);
    }
}