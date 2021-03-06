package br.com.smartcarweb.dao;

import br.com.smartcarweb.api.util.persistence.ejb3.BaseDao;
import br.com.smartcarweb.model.Cliente;

public interface ClienteDAO extends BaseDao<Cliente> {

	public abstract Cliente consultarPorUsuario(Long idUsuario);

}
