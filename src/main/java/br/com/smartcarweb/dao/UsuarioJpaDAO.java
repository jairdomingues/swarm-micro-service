package br.com.smartcarweb.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import br.com.smartcarweb.api.util.persistence.ejb3.ModeloDao;
import br.com.smartcarweb.model.Papel;
import br.com.smartcarweb.model.Usuario;

@Stateless
public class UsuarioJpaDAO extends ModeloDao<Usuario> implements UsuarioDAO {

	private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "myh2")
	private EntityManager entityManager;

	@Override
	public Class<Usuario> getDomainClass() {
		return Usuario.class;
	}

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	public Usuario consultarPorEmail(String email) {
		Usuario usuario = null;
		try {
			return (Usuario) getEntityManager().createNamedQuery("Usuario.consultarPorEmail").setParameter("email", email)
					.getSingleResult();
		} catch (PersistenceException erro) {
			return usuario;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> listarPorPapel(Papel.NomeDoPapel nomeDoPapel) {
		return getEntityManager().createNamedQuery("Usuario.listarPorPapel").setParameter("nomeDoPapel", nomeDoPapel)
				.getResultList();
	}
	

}
