package ca.ulaval.glo4002.booking.domain.program;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.api.dtos.program.Activity;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;

public class SingleDayProgram {
    private Activity activity;
    private String artist;
    private LocalDate date;

    public SingleDayProgram(Activity activity, String artist, LocalDate date) {
        this.activity = activity;
        this.artist = artist;
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getArtist() {
        return artist;
    }

	public boolean validateIfAmAndPm() {
        boolean allActivityAndShow = true;
        if(activity == null || artist.equals(null)) {
            allActivityAndShow = false; 
        }
        return allActivityAndShow;
	}

	public boolean validateActivityOnlyOnAm() {
        boolean onlyActivities = false;
        if(Activity.contains(activity)) {
            onlyActivities = true;
        }
        return onlyActivities;
    }

	public boolean isDuringFestivalDate(FestivalDates glow4002Dates) {
        return glow4002Dates.isDuringEventTime(date);
	}
}