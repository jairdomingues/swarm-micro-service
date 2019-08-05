package br.com.smartcarweb;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

public class SchedulerMain {

	public static void main(String[] args) throws Exception {

		Swarm swarm = new Swarm(args);
	     
	    swarm.fraction(
	        new DatasourcesFraction()
	        .jdbcDriver("org.postgresql", (d) -> {
	            d.driverClassName("org.postgresql.Driver");
	            d.xaDatasourceClass("org.postgresql.xa.PGXADataSource");
	            d.driverModuleName("org.postgresql");
	        })
	        .dataSource("myDS", (ds) -> {
	           
	          ds.driverName("PostgreSQL");	
	          ds.connectionUrl("jdbc:postgresql://localhost:5432/swarm");
	          ds.userName("postgres");
	          ds.password("861009");
	          ds.minPoolSize(5);
	          ds.maxPoolSize(50);
	          ds.validateOnMatch(false);
	          ds.backgroundValidation(false);
	          ds.backgroundValidationMillis(1l);
	          ds.preparedStatementsCacheSize(0l);
	          ds.sharePreparedStatements(false);
	          ds.useCcm(false);
	          ds.useJavaContext(true);
	          ds.transactionIsolation("TRANSACTION_READ_UNCOMMITTED");
	        }));
	         
	    swarm.start();
	     
	    JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
	     
	    ClassLoader classLoader = SchedulerMain.class.getClassLoader();
	           
	    deployment.addAsWebInfResource(classLoader.getResource("beans.xml"),"beans.xml");
	    deployment.addAsWebInfResource(classLoader.getResource("META-INF/persistence.xml"),"classes/META-INF/persistence.xml");
	    deployment.addAsWebInfResource(classLoader.getResource("modules/org/postgresql/main/module.xml"),"module.xml");
	    deployment.addAsResource("dicionario/excecoes/ErroAcc_pt_BR.properties","dicionario/excecoes/ErroAcc_pt_BR.properties");
	    deployment.addAsResource("dicionario/excecoes/ErroAcc_pt_BR.properties","dicionario/excecoes/ErroBase_pt_BR.properties");

	    deployment.addPackages(true, Package.getPackage("br.com.smartcarweb"));	
	    deployment.addAllDependencies();
	     
	    swarm.deploy(deployment);
	     
	  }	

}