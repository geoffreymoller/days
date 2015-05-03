package com.geoffreymoller.vision.days.service;

import com.codahale.metrics.MetricRegistry;
import com.geoffreymoller.vision.days.service.configuration.DaysConfiguration;
import com.geoffreymoller.vision.days.service.resources.DaysResource;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

public class DaysApplication extends Application<DaysConfiguration> {

    public static void main(String[] args) throws Exception {
        new DaysApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<DaysConfiguration> bootstrap) {

    }

    @Override
    public void run(DaysConfiguration daysConfiguration, Environment environment) throws Exception {
        final MetricRegistry metricRegistry = environment.metrics();

        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, daysConfiguration.getDataSourceFactory(), "mysql");

        environment.jersey().register(new DaysResource(jdbi, daysConfiguration, metricRegistry));
    }

}
