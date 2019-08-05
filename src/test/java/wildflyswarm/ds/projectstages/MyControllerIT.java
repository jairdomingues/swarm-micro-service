package wildflyswarm.ds.projectstages;
/*
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.arquillian.DefaultDeployment;

import br.com.smartcarweb.main.SchedulerMain;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Arquillian.class)
@DefaultDeployment(testable = false, main = SchedulerMain.class)
public class MyControllerIT {

  @ArquillianResource
  private URI deploymentUri;

  @Test
  public void test() {
    Client client = ClientBuilder.newClient();
    WebTarget target = client.target(deploymentUri);

    Response response = target.request(MediaType.APPLICATION_JSON).get();

    assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    assertThat(response.readEntity(String.class).contains("org.jboss.jca.adapters.jdbc.jdk7.WrappedConnectionJDK7"));
  }

}
*/