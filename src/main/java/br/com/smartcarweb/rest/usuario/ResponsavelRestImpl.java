package br.com.smartcarweb.rest.usuario;

import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.com.smartcarweb.api.excecoes.ErroAcc;
import br.com.smartcarweb.api.excecoes.ErroBase;
import br.com.smartcarweb.service.ProcessoService;
import br.com.smartcarweb.service.ResponsavelCreateDTO;

public class ResponsavelRestImpl implements ResponsavelRest {

	@Inject
	ProcessoService processoService;

	public Response createResponsavel(ResponsavelCreateDTO responsavelCreateDTO) {
		try {
			processoService.createResponsavel(responsavelCreateDTO);
			return Response.status(Response.Status.CREATED).build();
		} catch (ErroBase | ErroAcc erro) {
			throw erro.converterParaRest();
		} catch (EJBTransactionRolledbackException e) {
			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity(converterParaRest(e))
					.type(MediaType.APPLICATION_JSON).build());
		}
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

}
