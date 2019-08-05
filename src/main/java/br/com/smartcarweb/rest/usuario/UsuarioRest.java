package br.com.smartcarweb.rest.usuario;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.smartcarweb.api.jwt.filter.JWTToken;
import br.com.smartcarweb.service.usuario.ClienteDTO;
import br.com.smartcarweb.service.usuario.UsuarioDTO;

@Path(UsuarioRest.PATH)
@Produces(MediaType.APPLICATION_JSON)
public interface UsuarioRest {

	public static final String PATH = "/usuarios";

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@JWTToken
	public Response listarUsuarios();

	@GET
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@JWTToken
	public Response consultarUsuario(@PathParam("id") Long idUsuario);
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@JWTToken
	public Response adicionarUsuario(UsuarioDTO usuarioDTO);
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	@JWTToken
	Response atualizarUsuario(@PathParam("id") Long idUsuario, UsuarioDTO usuarioDTO);
	
	@DELETE
	@Path("{id}")
	@JWTToken
	public Response removerUsuario(@PathParam("id") Long idUsuario);
	
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/autenticar")
    public Response login(Credencial credencial);
	
	@GET
	@Path("/email")
	@Consumes(MediaType.APPLICATION_JSON)
	@JWTToken
	public Response consultarEmailExiste(@QueryParam("email") String email);

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/adicionar_cliente")
	@JWTToken
	Response adicionarCliente(@PathParam("id") Long idUsuario, ClienteDTO clienteDTO);
	
	@GET
	@Path("{id}/consultar_carteira")
	@Consumes(MediaType.APPLICATION_JSON)
	@JWTToken
	public Response consultarCarteira(@PathParam("id") Long idUsuario);

	@GET
	@Path("{id}/consultar_cliente")
	@Consumes(MediaType.APPLICATION_JSON)
	@JWTToken
	public Response consultarCliente(@PathParam("id") Long idUsuario);

}
