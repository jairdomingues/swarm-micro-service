package br.com.smartcarweb.rest.usuario;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.smartcarweb.service.dto.ResponsavelCreateDTO;

@Path(ResponsavelRest.PATH)
@Produces(MediaType.APPLICATION_JSON)
public interface ResponsavelRest {

	public static final String PATH = "/responsaveis";

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createResponsavel(ResponsavelCreateDTO responsavelCreateDTO);
	
}
