package br.com.smartcarweb.api.util.persistence.ejb3;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import br.com.smartcarweb.api.excecoes.ErroBase;

@Local
public interface BaseDao<T extends PersistentObject> extends Serializable {

    Class<T> getDomainClass();
    
    long contar();
    
    List<T> listarTodos();    
    
    T consultarPorId(Long id);
    
    T consultarPorUuid(String uuid) throws ErroBase;
    
    void inserir(T object) throws ErroBase;
    
    void atualizar(T object) throws ErroBase;
    
    void remover(T object);
    
    T merge(T object);
    
    List<T> listar(Class<T> classeEntidade, Integer pagina, Integer limite,String[] listaCamposId, 
            String filtro, String ... orderBy) throws ErroBase;
    
    Long contarRegistros(Class<T> classeEntidade, String filtro, String[] campos) throws ErroBase;
}
