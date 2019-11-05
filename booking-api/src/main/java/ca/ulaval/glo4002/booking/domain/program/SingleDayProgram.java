package ca.ulaval.glo4002.booking.domain.program;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.api.dtos.program.Activity;

public class SingleDayProgram {
    private Activity am;
    private String pm;
    private LocalDate date;

    public SingleDayProgram(Activity am, String artists, LocalDate date) {}

	public boolean validateIfAmAndPm() {
        boolean allActivityAndShow = true;
        if(am == null || pm.equals(null)) {
            allActivityAndShow = false; 
        }
        return allActivityAndShow;
	}

	public boolean validateActivityOnlyOnAm() {
        boolean onlyActivities = false;
        if(Activity.contains(am)) {
            onlyActivities = true;
        }
        return onlyActivities;
	}


}