package br.com.smartcarweb.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.smartcarweb.api.util.persistence.ejb3.ModeloDao;
import br.com.smartcarweb.model.Address;
import br.com.smartcarweb.model.Cliente;

@Stateless
public class AddressJpaDAO extends ModeloDao<Address> implements AddressDAO {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "myh2")
	private EntityManager entityManager;

	@Override
	public Class<Address> getDomainClass() {
		return Address.class;
	}

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

}