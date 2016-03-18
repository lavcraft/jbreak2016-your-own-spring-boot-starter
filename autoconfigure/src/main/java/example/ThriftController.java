package example;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author tolkv
 * @since 18/03/16
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Component
public @interface ThriftController {
}
