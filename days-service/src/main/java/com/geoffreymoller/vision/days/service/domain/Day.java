package com.geoffreymoller.vision.days.service.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class Day extends EntityWithLongId {

    @NotNull
    @JsonProperty("id")
    private long id;

    @NotNull
    @JsonProperty("user_id")
    private long userId;

    @NotNull
    @JsonProperty("date")
    private Date date;

    @JsonProperty("journal")
    private String journal;

    @Valid
    @JsonProperty("activities")
    private List<Activity> activities;


    @Override
    public Long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public Date getDate() {
        return date;
    }

    public String getJournal() {
        return journal;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    @JsonCreator
    public Day(@JsonProperty("id") long id, @JsonProperty("user_id") long userId, @JsonProperty("date") Date date, @JsonProperty("journal") String journal) {
        checkNotNull(id, "id can't be null");
        checkNotNull(userId, "userId can't be null");
        checkNotNull(date, "date can't be null");
        checkNotNull(journal, "journal can't be null");
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.journal = journal;
    }

    @JsonCreator
    public Day(@JsonProperty("id") long id, @JsonProperty("user_id") long userId,
               @JsonProperty("date") Date date, @JsonProperty("journal") String journal, @JsonProperty("activities") List<Activity> activities) {
        checkNotNull(id, "id can't be null");
        checkNotNull(userId, "userId can't be null");
        checkNotNull(date, "date can't be null");
        checkNotNull(journal, "journal can't be null");
        checkNotNull(activities, "activities can't be null");
        this.id = id;
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.journal = journal;
        this.activities = activities;
    }

}
