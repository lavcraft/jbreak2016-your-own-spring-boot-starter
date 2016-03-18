package example;

import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServlet;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.embedded.RegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.lang.reflect.Constructor;
import java.util.Arrays;

/**
 * @author tolkv
 * @since 18/03/16
 */
@Slf4j
@Configuration
@ConditionalOnClass(ThriftController.class)
public class ThriftAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(TProtocolFactory.class)
  TProtocolFactory tProtocolFactory(){
    return new TBinaryProtocol.Factory();
  }

  @Configuration
  public static class Registrator extends RegistrationBean implements ApplicationContextAware {
    @Setter
    ApplicationContext applicationContext;
    @Autowired
    private TProtocolFactory protocolFactory;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
      applicationContext.getBeansWithAnnotation(ThriftController.class)
          .forEach((name, beanObject) -> reqisteringServlet(name,beanObject,servletContext));
    }

    private void reqisteringServlet(String name, Object beanObject, ServletContext servletContext) {
      Arrays.stream(beanObject.getClass().getInterfaces())
          .filter(aClass -> aClass.getName().endsWith("$Iface"))
          .findFirst()
          .ifPresent(aiFaceClass -> {
            Class<?> processor = Arrays.stream(aiFaceClass.getEnclosingClass().getDeclaredClasses())
                .filter(aClass -> aClass.getName().endsWith("$Processor"))
                .findFirst()
                .orElse(null);

            initMapping(name, beanObject, servletContext, aiFaceClass, processor);
          });
    }

    @SneakyThrows
    private void initMapping(String name, Object beanObject, ServletContext servletContext, Class<?> aiFaceClass, Class<?> processor) {
      Constructor<TProcessor> constructor = (Constructor<TProcessor>) processor.getConstructor(aiFaceClass);
      TProcessor tProcessor = BeanUtils.instantiateClass(constructor, beanObject);
      TServlet tServlet = new TServlet(tProcessor, protocolFactory);
      ServletRegistration.Dynamic dynamic = servletContext.addServlet(name, tServlet);
      dynamic.addMapping("/" + name);
    }
  }
}
