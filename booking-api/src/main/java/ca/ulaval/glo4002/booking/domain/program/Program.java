package ca.ulaval.glo4002.booking.domain.program;

import java.util.List;

public class Program {
    private List<SingleDayProgram> program;

    public Program(List<SingleDayProgram> program) {
        this.program = program;
        validateEventDates();
        validateIfAmAndPm();
        validateArtistOnlyOnPm();
        validateActivityOnlyOnAm();
        validateArtistDifferentOnEachDay();
    }
     
    private void validateEventDates() {
        //TODO => vérifie s'il y a deux fois la même date ou s'il manque une date
        //En général, vérifie que ce soit bel et bien les dates du festival
    }

    private void validateIfAmAndPm() {
        for(SingleDayProgram programForOneDay : program) {
            programForOneDay.validateIfAmAndPm();
        }
    }

    private void validateArtistOnlyOnPm() {
        //TODO
    }

    private void validateActivityOnlyOnAm() {
        for(SingleDayProgram programForOneDay : program) {
            programForOneDay.validateActivityOnlyOnAm();
        }
    }

    private void validateArtistDifferentOnEachDay() {
        //TODO => vérifie si un artiste est présent 2 fois
    }
}