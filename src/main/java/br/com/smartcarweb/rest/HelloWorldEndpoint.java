package br.com.smartcarweb.rest;

import java.util.Date;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.smartcarweb.service.ScheduleBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
 
@Path("/time")
@Api(value = "/time", description = "Get the time", tags = "time")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldEndpoint {
 
	@Inject
    private ScheduleBean scheduleBean;
	 
    @GET
    @Path("/now")
    @ApiOperation(value = "Get the current time",
            notes = "Returns the time as a string",
            response = String.class
    )
    @Produces(MediaType.APPLICATION_JSON)
    public String get() {
    	scheduleBean.getAllSchedules();
        return String.format("{\"value\" : \"The time is %s\"}", new Date());
    }
}