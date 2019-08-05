package br.com.smartcarweb.rest.usuario;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.smartcarweb.api.jwt.filter.JWTToken;
import br.com.smartcarweb.service.BeaconListDTO;

@Path(BeaconRest.PATH)
@Produces(MediaType.APPLICATION_JSON)
public interface BeaconRest {

	public static final String PATH = "/beacons";

	@GET
	@Path("/{uuid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBeacon(@PathParam("uuid") String uuid);
	
	@POST
	@Path("/{id}/locate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postBeacon(@PathParam("id") Long idUsuario, BeaconListDTO beaconList);
	
}

