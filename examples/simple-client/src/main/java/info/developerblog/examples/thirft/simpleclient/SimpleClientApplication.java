package info.developerblog.examples.thirft.simpleclient;

import example.ХватитЛоматьсяВключайсяПожалуйста;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ХватитЛоматьсяВключайсяПожалуйста
public class SimpleClientApplication {
  @Bean
  TProtocolFactory tProtocolFactory(){
    return new TCompactProtocol.Factory();
  }

  public static void main(String[] args) {
    SpringApplication.run(SimpleClientApplication.class, args);
  }
}
