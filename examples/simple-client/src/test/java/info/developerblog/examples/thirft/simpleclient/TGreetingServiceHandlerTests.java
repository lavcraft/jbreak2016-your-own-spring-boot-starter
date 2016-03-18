package info.developerblog.examples.thirft.simpleclient;

import example.TInvestigatingService;
import example.TName;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {
    SimpleClientApplication.class
})
public class TGreetingServiceHandlerTests {
  Integer port = 6565;

  @Test
  public void testSimpleCall() throws Exception {
    TSocket transport = new TSocket("localhost", port);
    transport.open();

    TBinaryProtocol tBinaryProtocol = new TBinaryProtocol(transport);

    TInvestigatingService.Client client = new TInvestigatingService.Client(tBinaryProtocol);

    perform(client);

    transport.close();
  }

  private void perform(TInvestigatingService.Client client) throws TException {
    assert client.whereIs(new TName()
        .setFirstName("Josh")
        .setSecondName("Long"))
        .equals("Where is Josh?");
  }

  @After
  public void clean() {
  }
}
