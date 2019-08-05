package br.com.smartcarweb.dao;

import br.com.smartcarweb.api.util.persistence.ejb3.BaseDao;
import br.com.smartcarweb.model.Responsavel;

public interface ResponsavelDAO extends BaseDao<Responsavel> {

	public abstract Responsavel consultarPorCpf(String cpf);
	
}
