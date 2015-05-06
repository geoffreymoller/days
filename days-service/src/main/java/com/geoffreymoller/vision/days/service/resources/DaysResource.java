package com.geoffreymoller.vision.days.service.resources;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.annotation.Timed;
import com.geoffreymoller.vision.days.service.command.GetDayCommand;
import com.geoffreymoller.vision.days.service.command.PostDayCommand;
import com.geoffreymoller.vision.days.service.command.UpdateDayCommand;
import com.geoffreymoller.vision.days.service.configuration.DaysConfiguration;
import com.geoffreymoller.vision.days.service.domain.Day;
import com.geoffreymoller.vision.days.service.repository.DayJdbiDao;
import com.google.common.base.Optional;
import io.dropwizard.jersey.params.DateTimeParam;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

@Path("/api/v1/day")
@Produces(MediaType.APPLICATION_JSON)
public class DaysResource {

    private static final Logger LOG = LoggerFactory.getLogger(DaysResource.class);
    private final MetricRegistry metricsRegistry;
    private final DaysConfiguration configuration;
    private final DayJdbiDao dao;
    private final DBI jdbi;

    public DaysResource(DBI jdbi, DaysConfiguration configuration, MetricRegistry metricRegistry) {
        this.jdbi = jdbi;
        this.dao = new DayJdbiDao(jdbi);
        this.configuration = configuration;
        this.metricsRegistry = metricRegistry;
    }

    @Timed
    @GET
    @Path("/{user_id}/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDay(@PathParam("user_id") long userId,
                           @PathParam("date") DateTimeParam date)  {
        Optional<Day> dayOptional = new GetDayCommand(metricsRegistry, dao, userId, date.get()).execute();
        return doResponse(dayOptional);
    }

    @Timed
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postDay(@Valid Day day)  {
        Optional<Day> dayOptional = new PostDayCommand(metricsRegistry, dao, day).execute();
        return doResponse(dayOptional);
    }

    @Timed
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDay(@Valid Day day)  {
        Optional<Day> dayOptional = new UpdateDayCommand(metricsRegistry, dao, day).execute();
        return doResponse(dayOptional);
    }

    private Response doResponse(Optional<Day> dayOptional) {
        if(dayOptional.isPresent()){
            return Response.status(Response.Status.OK).entity(dayOptional.get()).build();
        } else {
            return Response.serverError().build();
        }
    }

}

