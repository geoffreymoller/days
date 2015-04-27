package com.geoffreymoller.vision.days.service.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import static com.google.common.base.Preconditions.checkNotNull;

public class Activity {

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

    @JsonProperty("notes")
    private String notes;

    public String getName() {
        return name;
    }

    public String getTags() {
        return tags;
    }

    public String getDuration() {
        return duration;
    }

    public long getDurationMs() {
        return durationMs;
    }

    public long getCount() {
        return count;
    }

    public String getNotes() {
        return notes;
    }

    public Activity(
            @JsonProperty("name") String name,
            @JsonProperty("tags") String tags,
            @JsonProperty("duration") String duration,
            @JsonProperty("durationMs") long durationMs,
            @JsonProperty("count") long count,
            @JsonProperty("notes") String notes) {
        checkNotNull(name, "name can't be null");
        this.name = name;
        this.tags = tags;
        this.duration = duration;
        this.durationMs = durationMs;
        this.count = count;
        this.notes = notes;
    }

}
