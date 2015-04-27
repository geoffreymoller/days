package com.geoffreymoller.vision.days.service.command;

import com.codahale.metrics.MetricRegistry;
import com.geoffreymoller.vision.days.service.domain.Day;
import com.google.common.base.Optional;

public class UpdateDayCommand {

    public UpdateDayCommand(MetricRegistry metricsRegistry, long userId, long date, Day day) {

    }

    public Optional<Day> execute() {
        return Optional.absent();
    }

}
