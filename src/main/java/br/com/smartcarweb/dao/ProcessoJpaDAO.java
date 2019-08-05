package br.com.smartcarweb.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.smartcarweb.api.util.persistence.ejb3.ModeloDao;
import br.com.smartcarweb.model.Processo;

@Stateless
public class ProcessoJpaDAO extends ModeloDao<Processo> implements ProcessoDAO {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "myh2")
	private EntityManager entityManager;

	@Override
	public Class<Processo> getDomainClass() {
		return Processo.class;
	}

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

}