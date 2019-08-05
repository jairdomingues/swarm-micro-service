package br.com.smartcarweb.api.util.persistence.ejb3;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.smartcarweb.api.excecoes.ErroBase;
import br.com.smartcarweb.api.jwt.filter.AgValidacao;
import br.com.smartcarweb.api.jwt.filter.LoggedIn;
import br.com.smartcarweb.model.Usuario;

public abstract class ModeloDao<T extends PersistentObject> implements BaseDao<T> {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(ModeloDao.class.getCanonicalName());

    protected abstract EntityManager getEntityManager();

	@Inject
	@LoggedIn
	Usuario currentUser;

    protected T retornarUnicoRegistro(CriteriaQuery<T> cq, Object ... params) throws ErroBase  {
        // Looks for a single result. Throws a checked exception if the entity is not found or in case of multiple results.
        try {
            T result = getEntityManager().createQuery(cq).getSingleResult();
            return result;
        } catch (NoResultException e) {
            logger.log(Level.WARNING, "NoResultException thrown for params: " + params, e);
            throw new ErroBase(ErroBase.Codigos.BASE_E_26);
        } catch (NonUniqueResultException e) {
            logger.log(Level.WARNING, "NonUniqueResultException thrown for params: " + params, e);
            throw new ErroBase(ErroBase.Codigos.BASE_E_25);
        }
    }

    @Override
    public long contar() {
        // Using the entity manager, create a criteria query to retrieve the object count.
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> rt = cq.from(getDomainClass());
        cq.select(cb.count(rt));
        Query query = em.createQuery(cq);

        // Retrieve the value and return.
        long count = ((Long) query.getSingleResult()).longValue();
        logger.log(Level.INFO, "Retrieved count for {0}: {1}", new Object[] { getDomainClass().getName(), count });
        return count;
    }

    @Override
    public List<T> listarTodos() {
        logger.log(Level.FINER, "Retrieving all objects of class \"{0}\"...", getDomainClass().getName());

        // Using the entity manager, create a criteria query to retrieve all objects of the domain class.
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(getDomainClass());
        Root<T> root = cq.from(getDomainClass());
        cq.select(root);

        // Return the list of objects.
        List<T> result = em.createQuery(cq).getResultList();
        logger.log(Level.INFO, "Retrieve all for class \"{0}\" returned \"{1}\" objects", 
                new Object[] { getDomainClass().getName(), result.size() });
        return result;
    }

    @Override
    public T consultarPorId(Long id) {
        logger.log(Level.FINER, "Retrieving object of class \"{0}\" with id {1}...", 
                new Object[] { getDomainClass().getName(), id });

        // Uses the Persistence Context to retrieve an object given its id.
        EntityManager em = getEntityManager();
        T result = (T) em.find(getDomainClass(), id);
        logger.log(Level.INFO, "Retrieve object of class {0} with id {1} returned \"{2}\"", 
                new Object[] { getDomainClass().getName(), id, result });
        return result;
    }

    @Override
    public T consultarPorUuid(String uuid) throws ErroBase {
        logger.log(Level.FINER, "Retrieving object of class \"{0}\" with UUID {1}...", 
                new Object[] { getDomainClass().getName(), uuid });

        // Constructs the query over the PersistentObject class.
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(getDomainClass());
        Root<T> root = cq.from(getDomainClass());

        // Filters the query with the name.
        cq.where(cb.equal(root.get("uuid"), uuid));
        T result = retornarUnicoRegistro(cq, uuid);
        return result;
    } 

    /**
     * Insere um objeto entidade JPA.
     * @param object.
     * @throws ErroBase encapsula PersistenceException e ConstraintViolationException 
     */
    //@AgValidacao()
    public void inserir(T object) throws ErroBase {
        logger.log(Level.FINER, "Saving an object of class {0}: \"{1}\"...", new Object[] { getDomainClass().getName(), object });

        BaseEntity a = (BaseEntity) object;
        
        if(a.getUsuarioInclusao()==null) {
            throw new ErroBase("Usuário de inclusão e/ou alteração não foi informado.");
        }
        // Utiliza o contexto de persistência para salvar um objeto.
        EntityManager em = getEntityManager();

       	em.persist(a);

    }

    /**
     * atualiza um objeto entidade JPA.
     * @param object.
     * @throws ErroBase encapsula PersistenceException e ConstraintViolationException 
     */
//    @AgValidacao(operacao = OperacaoValidacaoEnum.ATUALIZAR)
    public void atualizar(T object) {
        logger.log(Level.FINER, "Saving an object of class {0}: \"{1}\"...", new Object[] { getDomainClass().getName(), object });

        BaseEntity a = (BaseEntity) object;
        
        if(a.getUsuarioAlteracao()==null) {
            throw new ErroBase("Usuário de inclusão e/ou alteração não foi informado.");
        }
        
        // Utiliza o contexto de persistência para salvar um objeto. 
        EntityManager em = getEntityManager();

        try {
            em.merge(object);
        } catch (Exception e) {
            throw new ErroBase(ErroBase.Codigos.BASE_E_20);
        }
    }

    /**
     * Método para excluir um objeto.
     */
//    @AgValidacao(operacao = OperacaoValidacaoEnum.REMOVER)
    public void remover(T object) {
        logger.log(Level.FINER, "Deleting an object of class {0}: \"{1}\"...", new Object[] { getDomainClass().getName(), object });

        // Uses the Persistence Context to delete an object.
        EntityManager em = getEntityManager();
        em.remove(em.merge(object));
    }

    /**
     * Este método será descontinuado em função das validações das entidades. 
     * @deprecated use método atualizar
     */
    @Deprecated()
    public T merge(T object) {
        logger.log(Level.FINER, "Merging an object of class {0}: \"{1}\"...", new Object[] { getDomainClass().getName(), object });

        // Uses the Persistence Context to merge an object.
        EntityManager em = getEntityManager();
        return em.merge(object);
    }

    /**
     *  Metodo para listar todos os elementos de uma determina entidade paginada de acordo com filtros e ordenacao.
     *  
     * @param classeEntidade entidade.
     * @param pagina inicio.
     * @param limite maximo de resultados.
     * @param listaCamposId campos para os filtros.
     * @param filtro valore para o campo do filtro.
     * @param orderBy campos para ordenacao.
     * @return retorna uma lista que atende aos filtros e a paginacao.
     * @throws ErroBase erro de objetos nao encontrados.
     */
    @SuppressWarnings("unchecked")
    public List<T> listar(Class<T> classeEntidade, Integer pagina, Integer limite, 
            String[] listaCamposId, String filtro, String ... orderBy) throws ErroBase {
        EntityManager em = getEntityManager();

        /* WHERE */
        StringBuilder where = new StringBuilder();
        for (int i = 0; i < listaCamposId.length; i++) {
            where.append(where.length() > 0 ? " OR " : "");
            where.append("lower(" + listaCamposId[i] + ")").append(" LIKE ");
   //         if (StringUtils.hasText(filtro)) {
   //             where.append("'%" + filtro.toLowerCase() + "%'");
   //         } else {
   //             where.append("'%%'");
   //         }
        }

        /* ORDER BY */
        StringBuilder ordem = new StringBuilder("");
        if (orderBy != null && orderBy[0] != null) {
            for (String ord : orderBy) {
             //   if (StringUtils.hasText(ord) && ("-").equals(ord.substring(0, 1))) {
             //       ord = ord.replaceFirst("-", "") + " DESC";
             //   } 
                ordem.append(ordem.length() > 0 ? ", " : " ORDER BY ").append(ord);
            }
        }

        /* MONTAGEM DA HQL */
        StringBuilder hql = new StringBuilder("FROM ").append(classeEntidade.getSimpleName()).append(" WHERE ")
                .append(where).append(ordem);

        em.setFlushMode(FlushModeType.COMMIT);
        Query query = em.createQuery(hql.toString());

        /* INTERVALO DE RESULTADOS */
        List<T> modelos = null;
        try {
            if (pagina > 0) {
                query.setFirstResult((pagina - 1) * limite);
            }
            if (limite > 0) {
                query.setMaxResults(limite);
            }
            modelos = query.getResultList();
        } catch (Exception erro) {
            logger.log(Level.ALL, "Erro ao consultar objetos <" + classeEntidade.getName() + ">.", erro);
            throw new ErroBase(ErroBase.Codigos.BASE_E_24);
        }

        return modelos;
    }

    /**
     * Método para contar o número de registros segundo a condição like dos campos informados.
     */
    public Long contarRegistros(Class<T> classeEntidade, String filtro, String[] campos) throws ErroBase {
        EntityManager em = getEntityManager();

        /* WHERE */
        StringBuilder where = new StringBuilder();
        for (int i = 0; i < campos.length; i++) {
            where.append(where.length() > 0 ? " OR " : "");
            where.append("LOWER(" + campos[i] + ")").append(" LIKE ");

            //if (StringUtils.hasText(filtro)) {
            //    where.append("'%" + filtro.toLowerCase() + "%'");
            //} else {
            //    where.append("'%%'");
            //}
        }

        StringBuilder hql = new StringBuilder("SELECT COUNT(*) FROM ").append(classeEntidade.getSimpleName()).append(" WHERE ")
                .append(where);

        em.setFlushMode(FlushModeType.COMMIT);
        Query query = em.createQuery(hql.toString());

        return (Long) query.getSingleResult();

    }

}
