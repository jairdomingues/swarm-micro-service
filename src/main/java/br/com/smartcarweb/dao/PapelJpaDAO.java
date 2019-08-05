package br.com.smartcarweb.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.smartcarweb.api.util.persistence.ejb3.ModeloDao;
import br.com.smartcarweb.model.Papel;
import br.com.smartcarweb.model.Papel.NomeDoPapel;

@Stateless
public class PapelJpaDAO extends ModeloDao<Papel> implements PapelDAO {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "myh2")
	private EntityManager entityManager;

	@Override
	public Class<Papel> getDomainClass() {
		return Papel.class;
	}

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	public Papel consultarPorPapel(NomeDoPapel nomeDoPapel) {
		return (Papel) getEntityManager().createNamedQuery("Papel.consultarPorPapel")
				.setParameter("nomeDoPapel", nomeDoPapel).getSingleResult();
	}

}