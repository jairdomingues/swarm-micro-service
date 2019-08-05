package br.com.smartcarweb.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.smartcarweb.api.excecoes.ErroAcc;
import br.com.smartcarweb.api.jwt.filter.LoggedIn;
import br.com.smartcarweb.api.util.Util;
import br.com.smartcarweb.dao.ResponsavelDAO;
import br.com.smartcarweb.model.Responsavel;
import br.com.smartcarweb.model.Usuario;
import br.com.smartcarweb.service.dto.ResponsavelCreateDTO;

@Stateless
public class ProcessoService {

	@Inject
	@LoggedIn
	Usuario currentUser;
	
	@Inject
	ResponsavelDAO responsavelDAO;
	
	public void createResponsavel(ResponsavelCreateDTO responsavelCreateDTO) {
		
		if (!Util.isValidCPF(responsavelCreateDTO.getCpf())) {
			throw new ErroAcc("CPF inválido.");
		}
		Responsavel responsavel = responsavelDAO.consultarPorCpf(responsavelCreateDTO.getCpf());
		if (responsavel != null) {
			throw new ErroAcc("CPF já existe.");
		}
		responsavel = ResponsavelCreateDTO.createEntity(responsavelCreateDTO);
		responsavel.setUsuarioInclusao("admin");
		responsavelDAO.inserir(responsavel);
	}
	
}