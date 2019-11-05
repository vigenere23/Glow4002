package ca.ulaval.glo4002.booking.domain.application;

import java.util.List;

import ca.ulaval.glo4002.booking.api.dtos.program.SingleDayProgramRequest;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;

public class ProgramValidator {

    private FestivalDates glow4002Dates;

    public ProgramValidator(FestivalDates glow4002Dates) {
        this.glow4002Dates = glow4002Dates;
    }

	public void validateProgram(List<SingleDayProgramRequest> program) {
        validateEventDates(program);
        validateIfAmAndPm(program);
        validateArtistOnlyOnPm(program);
        validateActivityOnlyOnAm(program);
        validateArtistDifferentOnEachDay(program);
    }
    
    private void validateEventDates(List<SingleDayProgramRequest> program) {
        //TODO => vérifie s'il y a deux fois la même date ou s'il manque une date
        //En général, vérifie que ce soit bel et bien les dates du festival
    }

    private void validateIfAmAndPm(List<SingleDayProgramRequest> program) {
        //TODO => vérifie si deux fois le champ am ou pm
    }

    private void validateArtistOnlyOnPm(List<SingleDayProgramRequest> program) {
        //TODO => vérifie si un artiste est programmé le matin
    }

    private void validateActivityOnlyOnAm(List<SingleDayProgramRequest> program) {
        //TODO => vérifie si une activité est programmé le soir
    }

    private void validateArtistDifferentOnEachDay(List<SingleDayProgramRequest> program) {
        //TODO => vérifie si un artiste est présent 2 fois
    }

}