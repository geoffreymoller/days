package com.geoffreymoller.vision.days.service.command;

import com.codahale.metrics.MetricRegistry;
import com.geoffreymoller.vision.days.service.domain.Day;
import com.google.common.base.Optional;

public class PostDayCommand {

    public PostDayCommand(MetricRegistry metricsRegistry, long userId, long date) {

    }

    public Optional<Day> execute() {
        return Optional.absent();
    }

}
