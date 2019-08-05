package br.com.smartcarweb.api.multitenant;

import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.jboss.logging.Logger;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public abstract class DataSourceMultiTenantConnectionProvider implements MultiTenantConnectionProvider  {

    private static final long serialVersionUID = 1L;
    private String baseJndiName;

    private static Logger log = Logger.getLogger(MultiTenantConnectionProvider.class);

    public DataSourceMultiTenantConnectionProvider(String baseJndiName) {
        this.baseJndiName = baseJndiName;
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        if ((connection != null) && (!connection.isClosed())) {
            connection.close();
        }
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        try {
            log.info("GETCONNECTION TENANT: " + tenantIdentifier);
            InitialContext context = new InitialContext();
            DataSource tenantDataSource = null;
            String tipoTenant = System.getProperty("hibernate.plataforma.multitenancy");
            if (tipoTenant.equals("DATABASE")) {
	            if (tenantIdentifier.equals("") || tenantIdentifier.equals("Default")) {
	                tenantDataSource = (DataSource) context.lookup(baseJndiName);
	            } else {
	                tenantDataSource = (DataSource) context.lookup(baseJndiName + "/" + tenantIdentifier);
	            }
	            return tenantDataSource.getConnection();
            } else {
            	tenantDataSource = (DataSource) context.lookup(baseJndiName);
            	Connection conexao = tenantDataSource.getConnection();
            	if ((tenantIdentifier.equals("")) || (tenantIdentifier.equalsIgnoreCase("DEFAULT"))) {
                	conexao.createStatement().execute("SET SEARCH_PATH TO public;");
                } else {
                	conexao.createStatement().execute("SET SEARCH_PATH TO "+tenantIdentifier+",public;");
                }
            	return conexao;
            }            
        } catch (NamingException e) {
            throw new SQLException("Erro ao acessar data source: " + baseJndiName + "/" 
                    + tenantIdentifier + ": " + e.getMessage(), e);
        }
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        if ((connection != null) && (!connection.isClosed())) {
            connection.close();
        }
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return getConnection("");
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean isUnwrappableAs(Class arg0) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> arg0) {
        return null;
    }

}
