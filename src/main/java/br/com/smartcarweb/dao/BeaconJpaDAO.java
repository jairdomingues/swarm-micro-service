package br.com.smartcarweb.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import br.com.smartcarweb.api.util.persistence.ejb3.ModeloDao;
import br.com.smartcarweb.model.Beacon;
import br.com.smartcarweb.model.Processo;
import br.com.smartcarweb.model.Responsavel;

@Stateless
public class BeaconJpaDAO extends ModeloDao<Beacon> implements BeaconDAO {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "myh2")
	private EntityManager entityManager;

	@Override
	public Class<Beacon> getDomainClass() {
		return Beacon.class;
	}

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	public Beacon consultarPorUuidBeacon(String uuid) {
		Beacon beacon = null;
		try {
			return (Beacon) getEntityManager().createNamedQuery("Beacon.consultarPorUuidBeacon")
					.setParameter("uuid", uuid).getSingleResult();
		} catch (PersistenceException erro) {
			return beacon;
		}
	}
	
}