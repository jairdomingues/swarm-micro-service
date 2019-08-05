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
import br.com.smartcarweb.service.BeaconService;
import br.com.smartcarweb.service.dto.BeaconListDTO;

public class BeaconRestImpl implements BeaconRest {

	@Inject
	BeaconService beaconService;

	public Response getBeacon(String uuid) {
		beaconService.getBeacon(uuid);
		return Response.ok().build();
	}
	
	public Response postBeacon(Long idUsuario,BeaconListDTO beaconList) {
		try {
			beaconService.postBeacons(idUsuario, beaconList);
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
