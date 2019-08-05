package br.com.smartcarweb.api.util;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class InjecaoCdiUtil {

    /**
     * Retorna a instância da classe via CDI.
     * @param clazz classe do bean a ser injetado.
     * @return instância do bean.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getFacade(Class<T> clazz) {
        BeanManager bm = getBeanManager();
        Bean<T> bean = (Bean<T>) bm.getBeans(clazz).iterator().next();
        CreationalContext<T> ctx = bm.createCreationalContext(bean);
        T object = (T) bm.getReference(bean, clazz, ctx);
        return object;
    }

    private static BeanManager getBeanManager() {
        try {
            InitialContext initialContext = new InitialContext();
            return (BeanManager) initialContext.lookup("java:comp/BeanManager");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

}
