package info.developerblog.examples.thirft.simpleclient;

import example.TInvestigatingService;
import example.TName;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.transport.THttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {
    SimpleClientApplication.class
})
@WebIntegrationTest(randomPort = true)
public class TGreetingServiceHandlerTests {

  @Value("${local.server.port}")
  int port;

  @Test
  public void testSimpleCall() throws Exception {
    THttpClient tHttpClient = new THttpClient("http://localhost:" + port + "/investigator");
    TCompactProtocol tBinaryProtocol = new TCompactProtocol(tHttpClient);

    TInvestigatingService.Client client = new TInvestigatingService.Client(tBinaryProtocol);

    perform(client);
  }

  private void perform(TInvestigatingService.Client client) throws TException {
    assert client.whereIs(new TName()
        .setFirstName("Josh")
        .setSecondName("Long"))
        .equals("Where is Josh?");
  }
}
