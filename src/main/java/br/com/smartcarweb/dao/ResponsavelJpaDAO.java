package br.com.smartcarweb.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import br.com.smartcarweb.api.util.persistence.ejb3.ModeloDao;
import br.com.smartcarweb.model.Cliente;
import br.com.smartcarweb.model.Responsavel;

@Stateless
public class ResponsavelJpaDAO extends ModeloDao<Responsavel> implements ResponsavelDAO {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "myh2")
	private EntityManager entityManager;

	@Override
	public Class<Responsavel> getDomainClass() {
		return Responsavel.class;
	}

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
	public Responsavel consultarPorCpf(String cpf) {
		Responsavel responsavel = null;
		try {
			return (Responsavel) getEntityManager().createNamedQuery("Responsavel.consultarPorCpf")
					.setParameter("cpf", cpf).getSingleResult();
		} catch (PersistenceException erro) {
			return responsavel;
		}
	}

}