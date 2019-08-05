package br.com.smartcarweb.dao;

import java.util.List;

import br.com.smartcarweb.api.util.persistence.ejb3.BaseDao;
import br.com.smartcarweb.model.Papel;
import br.com.smartcarweb.model.Usuario;

public interface UsuarioDAO extends BaseDao<Usuario> {

	abstract Usuario consultarPorEmail(String email);
	abstract List<Usuario> listarPorPapel(Papel.NomeDoPapel roleName);
	
}
