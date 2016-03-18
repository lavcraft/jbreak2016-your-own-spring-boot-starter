package info.developerblog.examples.thirft.simpleclient;

import example.TInvestigatingService;
import example.TName;
import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSocket;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class TGreetingServiceHandlerTests {
  Integer port = 6565;
  TServer server;
  Thread thread;

  @Before
  public void setUp() throws Exception {
    thread = new Thread(() -> {
      try {
        TServerTransport serverTransport = new TServerSocket(
            new InetSocketAddress(InetAddress.getLocalHost(), port));
        TProcessor processor = new TInvestigatingService.Processor<>(
            (TName name) -> "Where is " + name.getFirstName() + "?"
        );

        server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));

        System.out.println("Starting the simple server...");
        server.serve();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    thread.start();

    while (server == null || !server.isServing()) {
      TimeUnit.MILLISECONDS.sleep(100);
    }
  }

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
    server.stop();
  }
}
