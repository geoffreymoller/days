package com.geoffreymoller.vision.days.service.resources;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.annotation.Timed;
import com.geoffreymoller.vision.days.service.command.GetDayCommand;
import com.geoffreymoller.vision.days.service.command.PostDayCommand;
import com.geoffreymoller.vision.days.service.configuration.DaysConfiguration;
import com.geoffreymoller.vision.days.service.domain.Day;
import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/day/{user_id}/{date}")
@Produces(MediaType.APPLICATION_JSON)
public class DaysResource {

    private static final Logger LOG = LoggerFactory.getLogger(DaysResource.class);
    private final MetricRegistry metricsRegistry;
    private final DaysConfiguration configuration;

    public DaysResource(DaysConfiguration configuration, MetricRegistry metricRegistry) {
        this.configuration = configuration;
        this.metricsRegistry = metricRegistry;
    }

    @Timed
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDay(@PathParam("user_id") long userId,
                           @PathParam("date") long date)  {
        Optional<Day> dayOptional = new GetDayCommand(metricsRegistry, userId, date).execute();
        return doResponse(dayOptional);
    }

    @Timed
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postDay(@PathParam("user_id") long userId,
                           @PathParam("date") long date,
                           @Valid Day day)  {
        Optional<Day> dayOptional = new PostDayCommand(metricsRegistry, userId, date).execute();
        return doResponse(dayOptional);
    }

    @Timed
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putDay(@PathParam("user_id") long userId,
                            @PathParam("date") long date,
                            @Valid Day day)  {
        Optional<Day> dayOptional = new PostDayCommand(metricsRegistry, userId, date).execute();
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

