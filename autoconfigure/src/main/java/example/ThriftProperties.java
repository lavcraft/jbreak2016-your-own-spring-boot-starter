package example;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author tolkv
 * @since 18/03/16
 */
@Data
@ConfigurationProperties(prefix = "thrift.server")
public class ThriftProperties {
  boolean enable = true;
}
