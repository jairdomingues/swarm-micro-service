package br.com.smartcarweb.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.smartcarweb.api.util.persistence.ejb3.ModeloDao;
import br.com.smartcarweb.model.Papel;
import br.com.smartcarweb.model.Wallet;
import br.com.smartcarweb.model.Papel.NomeDoPapel;

@Stateless
public class WalletJpaDAO extends ModeloDao<Wallet> implements WalletDAO {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "myh2")
	private EntityManager entityManager;

	@Override
	public Class<Wallet> getDomainClass() {
		return Wallet.class;
	}

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	public Wallet consultarPorCliente(Long idUsuario) {
		return (Wallet) getEntityManager().createNamedQuery("Wallet.consultarPorCliente")
				.setParameter("idUsuario", idUsuario).getSingleResult();
	}
	
}