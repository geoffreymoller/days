package com.geoffreymoller.vision.days.service.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class Day {

    @NotNull
    @JsonProperty("date")
    private DateTime date;

    @JsonProperty("journal")
    private String journal;

    @Valid
    @JsonProperty("activities")
    private List<Activity> activities;

    public DateTime getDate() {
        return date;
    }

    public String getJournal() {
        return journal;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    @JsonCreator
    public Day(@JsonProperty("date") DateTime date, @JsonProperty("journal") String journal, @JsonProperty("activities") List<Activity> activities) {
        this.date = date;
        this.journal = journal;
        this.activities = activities;
    }

}
