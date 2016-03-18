package info.developerblog.examples.thirft.simpleclient;

import example.ThriftAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class SimpleClientApplication {
  public static void main(String[] args) {
    SpringApplication.run(SimpleClientApplication.class, args);
  }
}
