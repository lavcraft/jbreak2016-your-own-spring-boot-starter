package info.developerblog.examples.thirft.simpleclient;

import org.apache.thrift.server.TServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TGreetingServiceHandlerTests {

  Integer port=6565;
  TServer server;
  Thread thread;

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testSimpleCall() throws Exception {
  }

  @After
  public void clean(){
    server.stop();
  }
}
