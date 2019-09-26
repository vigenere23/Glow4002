package ca.ulaval.glo4002.booking.interfaces.rest;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ca.ulaval.glo4002.booking.domain.persistanceInteface.Repository;


public class HeartbeatResponse {
    public final String token;
    public final OffsetDateTime time;
    public final String repoInfo;

    @JsonCreator
    public HeartbeatResponse(@JsonProperty(value = "token", required = true) String token, Repository repo) {
        this.token = token;
        this.time = OffsetDateTime.now();
        this.repoInfo = repo.getOxygenPersistance().getInfo();
    }
}
