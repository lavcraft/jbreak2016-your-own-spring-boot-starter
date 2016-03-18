package info.developerblog.examples.thirft.simpleclient;

import example.TInvestigatingService;
import example.TName;
import org.apache.thrift.TProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class SimpleClientApplication {
  Integer port = 6565;
  TServer server;
  Thread thread;

  @PostConstruct
  public void init() throws InterruptedException {
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
  public static void main(String[] args) {
    SpringApplication.run(SimpleClientApplication.class, args);
  }

  @PreDestroy
  public void close(){
    server.stop();
  }
}
