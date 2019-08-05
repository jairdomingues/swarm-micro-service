package br.com.smartcarweb.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import br.com.smartcarweb.api.util.persistence.ejb3.ModeloDao;
import br.com.smartcarweb.model.Cliente;

@Stateless
public class ClienteJpaDAO extends ModeloDao<Cliente> implements ClienteDAO {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "myh2")
	private EntityManager entityManager;

	@Override
	public Class<Cliente> getDomainClass() {
		return Cliente.class;
	}

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	public Cliente consultarPorUsuario(Long idUsuario) {
		Cliente cliente = null;
		try {
			return (Cliente) getEntityManager().createNamedQuery("Cliente.consultarPorUsuario")
					.setParameter("idUsuario", idUsuario).getSingleResult();
		} catch (PersistenceException erro) {
			return cliente;
		}
	}
	
}