package br.com.smartcarweb.dao;

import br.com.smartcarweb.api.util.persistence.ejb3.BaseDao;
import br.com.smartcarweb.model.Papel;
import br.com.smartcarweb.model.Papel.NomeDoPapel;

public interface PapelDAO extends BaseDao<Papel> {

	public abstract Papel consultarPorPapel(NomeDoPapel nomeDoPapel);

}
