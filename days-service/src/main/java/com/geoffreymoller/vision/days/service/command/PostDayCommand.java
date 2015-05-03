package com.geoffreymoller.vision.days.service.command;

import com.codahale.metrics.MetricRegistry;
import com.geoffreymoller.vision.days.service.domain.Day;
import com.geoffreymoller.vision.days.service.repository.DayJdbiDao;
import com.google.common.base.Optional;
import com.netflix.hystrix.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostDayCommand extends HystrixCommand<Optional<Day>> {

    private static final Logger LOG = LoggerFactory.getLogger(PostDayCommand.class);

    private final MetricRegistry metricsRegistry;
    private final DayJdbiDao dao;
    private final Day day;

    public PostDayCommand(MetricRegistry metricsRegistry, DayJdbiDao dao, Day day) {
        super(VisionDependencyKey.POST_DAY);
        this.metricsRegistry = metricsRegistry;
        this.dao = dao;
        this.day = day;
    }

    @Override
    protected Optional<Day> run() throws Exception {
        LOG.info("Posting day for user: " + day.getUserId());
        return Optional.of(dao.save(day));
    }

    @Override
    protected Optional<Day> getFallback() {
        LOG.error("Unable to post day for user. Resorting to fallback", getFailedExecutionException());
        return Optional.absent();
    }

}
