package com.geoffreymoller.vision.days.service.command;

import com.codahale.metrics.MetricRegistry;
import com.geoffreymoller.vision.days.service.domain.Day;
import com.geoffreymoller.vision.days.service.repository.DayJdbiDao;
import com.google.common.base.Optional;
import com.netflix.hystrix.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class GetDayCommand extends HystrixCommand<Optional<Day>> {

    private static final Logger LOG = LoggerFactory.getLogger(GetDayCommand.class);

    private final long userId;
    private final Date date;
    private final DayJdbiDao dao;

    public GetDayCommand(MetricRegistry metricsRegistry, DayJdbiDao dao, long userId, Date date) {
        super(VisionDependencyKey.GET_DAY);
        this.dao = dao;
        this.userId = userId;
        this.date = date;
    }

    @Override
    protected Optional<Day> run() throws Exception {
        LOG.info("Retrieving day for user: " + userId);
        return Optional.of(dao.get(userId, date.getTime()));
    }

    @Override
    protected Optional<Day> getFallback() {
        LOG.error("Unable to retrieve day for user. Resorting to fallback", getFailedExecutionException());
        return Optional.absent();
    }

}
