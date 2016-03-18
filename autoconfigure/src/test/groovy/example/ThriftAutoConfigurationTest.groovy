package example

import org.springframework.boot.test.EnvironmentTestUtils
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import spock.lang.Specification
import spock.lang.Unroll
/**
 * @author tolkv
 * @since 18/03/16
 */
class ThriftAutoConfigurationTest extends Specification {
  AnnotationConfigApplicationContext context

  def setup() {
    context = new AnnotationConfigApplicationContext()
  }

  def clean() {
    context.close()
  }

  @Unroll
  def 'should enable auto configuration by thrift.enabled=#enableProperty property'() {
    given:
    EnvironmentTestUtils.addEnvironment(context, enableProperty)
    context.register(ThriftAutoConfiguration)
    context.refresh()

    expect:
    context.containsBean('tProtocolFactory') == beanIsContained

    where:
    enableProperty       | beanIsContained
    'thrift.server.enable=true'  | true
    'thrift.server.enable=false' | false
  }
}
