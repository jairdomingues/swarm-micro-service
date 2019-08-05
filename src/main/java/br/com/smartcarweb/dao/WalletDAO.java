package br.com.smartcarweb.dao;

import br.com.smartcarweb.api.util.persistence.ejb3.BaseDao;
import br.com.smartcarweb.model.Wallet;

public interface WalletDAO extends BaseDao<Wallet> {

	public abstract Wallet consultarPorCliente(Long idUsuario);

}
