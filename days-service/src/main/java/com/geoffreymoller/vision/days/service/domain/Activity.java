package com.geoffreymoller.vision.days.service.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;

import java.sql.Date;

import static com.google.common.base.Preconditions.checkNotNull;

public class Activity {

    @NotNull
    @JsonProperty("id")
    private long id;

    @NotNull
    @JsonProperty("user_id")
    private long userId;

    @NotNull
    @JsonProperty("day_id")
    private long dayId;

    @NotNull
    @JsonProperty("date")
    private DateTime date;

    @NotEmpty
    @JsonProperty("name")
    private String name;

    @JsonProperty("tags")
    private final String tags;

    @JsonProperty("duration")
    private String duration;

    @JsonProperty("durationMs")
    private long durationMs;

    @JsonProperty("count")
    private long count;

    public String getNotes() {
        return notes;
    }

    public long getCount() {
        return count;
    }

    public long getDurationMs() {
        return durationMs;
    }

    public String getDuration() {
        return duration;
    }

    public String getTags() {
        return tags;
    }

    public String getName() {
        return name;
    }

    public DateTime getDate() {
        return date;
    }

    public long getDayId() {
        return dayId;
    }

    public long getUserId() {
        return userId;
    }

    public long getId() {
        return id;
    }

    @JsonProperty("notes")
    private String notes;

    public Activity(
            @JsonProperty("id") long id,
            @JsonProperty("user_id") long userId,
            @JsonProperty("day_id") long dayId,
            @JsonProperty("date") Date date,
            @JsonProperty("name") String name,
            @JsonProperty("tags") String tags,
            @JsonProperty("duration") String duration,
            @JsonProperty("durationMs") long durationMs,
            @JsonProperty("count") long count,
            @JsonProperty("notes") String notes) {
        checkNotNull(id, "id can't be null");
        checkNotNull(userId, "userId can't be null");
        checkNotNull(dayId, "dayId can't be null");
        checkNotNull(date, "date can't be null");
        checkNotNull(name, "name can't be null");
        this.id = id;
        this.userId = userId;
        this.dayId = dayId;
        this.date = new DateTime(date);
        this.name = name;
        this.tags = tags;
        this.duration = duration;
        this.durationMs = durationMs;
        this.count = count;
        this.notes = notes;
    }

}
