package com.geoffreymoller.vision.days.service.command;

import com.codahale.metrics.MetricRegistry;
import com.geoffreymoller.vision.days.service.domain.Day;
import com.geoffreymoller.vision.days.service.repository.DayJdbiDao;
import com.google.common.base.Optional;
import com.netflix.hystrix.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateDayCommand extends HystrixCommand<Optional<Day>> {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateDayCommand.class);

    private final MetricRegistry metricsRegistry;
    private final DayJdbiDao dao;
    private final Day day;

    public UpdateDayCommand(MetricRegistry metricsRegistry, DayJdbiDao dao, Day day) {
        super(VisionDependencyKey.UPDATE_DAY);
        this.metricsRegistry = metricsRegistry;
        this.dao = dao;
        this.day = day;
    }

    public Optional<Day> run() {
        LOG.info("Updating day for user: " + day.getUserId());
        return Optional.of(dao.update(day));
    }

    @Override
    protected Optional<Day> getFallback() {
        LOG.error("Unable to update day for user. Resorting to fallback", getFailedExecutionException());
        return Optional.absent();
    }

}


