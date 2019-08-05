package br.com.smartcarweb.api.multitenant;

import java.io.IOException;

import javax.annotation.Priority;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;

/**
 * Filter to intercept requests and get the user from the header.
 *
 * @author André Hildinger
 */

@Provider
//Ocorre antes da autorização
@Priority(900)
public class AuthRequestFilter implements ContainerRequestFilter {

	@PersistenceUnit(unitName = "myh2")
	private EntityManagerFactory entityManagerFactory;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
/*
    	SessionFactoryImplementor f = (SessionFactoryImplementor) entityManagerFactory.unwrap(SessionFactory.class);
        final SchemaResolver schemaResolver = (SchemaResolver) f.getCurrentTenantIdentifierResolver();
        String username = containerRequestContext.getHeaderString("X-TENANT");
        if (username == null) {
        	//pegar o tenant atraves do get da url (usado para download)
            MultivaluedMap<String, String> pathOrQueryParam = containerRequestContext.getUriInfo().getPathParameters();
             if (pathOrQueryParam==null) {
            	pathOrQueryParam = containerRequestContext.getUriInfo().getQueryParameters();            
            }
            username = pathOrQueryParam.getFirst("tenant");
            //username = pathParam.getFirst("tenant");
        }
        schemaResolver.setTenantIdentifier(username);
        */
    }

}
