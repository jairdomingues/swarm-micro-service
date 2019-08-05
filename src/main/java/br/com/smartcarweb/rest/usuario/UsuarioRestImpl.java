package br.com.smartcarweb.rest.usuario;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.com.smartcarweb.api.excecoes.ErroAcc;
import br.com.smartcarweb.api.excecoes.ErroBase;
import br.com.smartcarweb.api.jwt.filter.JWTUtil;
import br.com.smartcarweb.service.usuario.Autenticado;
import br.com.smartcarweb.service.usuario.Balance;
import br.com.smartcarweb.service.usuario.ClienteDTO;
import br.com.smartcarweb.service.usuario.UsuarioDTO;
import br.com.smartcarweb.service.usuario.UsuarioService;

public class UsuarioRestImpl implements UsuarioRest {

	@Inject
	UsuarioService usuarioService;

	public Response listarUsuarios() {
		return Response.ok(usuarioService.listaUsuarios()).build();
	}

	public Response consultarUsuario(Long idUsuario) {
		UsuarioDTO usuarioDTO = usuarioService.consultarUsuario(idUsuario);
		return Response.ok(usuarioDTO).build();
	}

	public Response adicionarUsuario(UsuarioDTO usuarioDTO) {
		try {
			Autenticado autenticado = usuarioService.adicionarUsuario(usuarioDTO);
			return Response.status(Response.Status.CREATED).entity(autenticado).build();
		} catch (ErroBase | ErroAcc erro) {
			throw erro.converterParaRest();
		} catch (EJBTransactionRolledbackException e) {
			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity(converterParaRest(e))
		            .type(MediaType.APPLICATION_JSON).build());
		}
	}

	public Response atualizarUsuario(Long idUsuario, UsuarioDTO usuarioDTO) {
		try {
			usuarioService.atualizarUsuario(idUsuario, usuarioDTO);
			return Response.ok().build();
		} catch (ErroAcc | ErroBase e) {
			throw e.converterParaRest();
		} catch (EJBTransactionRolledbackException e) {
			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity(converterParaRest(e))
		            .type(MediaType.APPLICATION_JSON).build());
		}
	}

	public Response removerUsuario(Long idUsuario) {
		try {
			usuarioService.removerUsuario(idUsuario);
			return Response.status(Response.Status.OK).build();
		} catch (ErroAcc | ErroBase e) {
			throw e.converterParaRest();
		} catch (EJBTransactionRolledbackException e) {
			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity(converterParaRest(e))
		            .type(MediaType.APPLICATION_JSON).build());
		}
	}

	public Response login(Credencial credencial) {
		// String senhaMd5 = utilRn.gerarHashMd5(credencial.getSenha());

		Autenticado autenticado;

		try {
			autenticado = usuarioService.login(credencial.getUsername(), credencial.getPassword(),
					credencial.getFusohorario());
		} catch (ErroAcc | ErroBase erro) {
			throw erro.converterParaRest();
		}
		
		String JWT = JWTUtil.create(autenticado.getEmail());
		
        //response autorizacao token
		return Response.status(Response.Status.CREATED).entity(autenticado).header(AUTHORIZATION, JWTUtil.TOKEN_PREFIX + " " + JWT).build();
	}

	public Response consultarEmailExiste(String email) {
		Boolean existe = usuarioService.consultarPorEmailExiste(email);
		return Response.ok(existe).build();
	}

	private String converterParaRest(EJBTransactionRolledbackException e) {
		String json = "";
	    Throwable t = e.getCause();
	    while ((t != null) && !(t instanceof PersistenceException)) {
	        t = t.getCause();
	    }
		Gson gson = new Gson();
	    if (t instanceof PersistenceException) {
			json = gson.toJson(t.getCause().getCause().getMessage());
			return json;
	    }
	    json = gson.toJson("Erro Desconhecido!!!");
	    return json;
	}

	public Response adicionarCliente(Long idUsuario, ClienteDTO clienteDTO) {
		try {
			usuarioService.adicionarCliente(idUsuario, clienteDTO);
			return Response.status(Response.Status.CREATED).build();
		} catch (ErroAcc | ErroBase e) {
			throw e.converterParaRest();
		} catch (EJBTransactionRolledbackException e) {
			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity(converterParaRest(e))
		            .type(MediaType.APPLICATION_JSON).build());
		}
	}

	public Response consultarCarteira(Long idUsuario) {
		Balance balance = usuarioService.consultarCarteira(idUsuario);
		return Response.ok(balance).build();
	}
	
	public Response consultarCliente(Long idUsuario) {
		ClienteDTO clienteDTO = usuarioService.consultarCliente(idUsuario);
		return Response.ok(clienteDTO).build();
	}

}
