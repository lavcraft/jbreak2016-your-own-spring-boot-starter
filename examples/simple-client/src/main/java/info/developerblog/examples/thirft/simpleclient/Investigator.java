package info.developerblog.examples.thirft.simpleclient;

import example.TInvestigatingService;
import example.TName;
import example.ThriftController;
import org.apache.thrift.TException;

/**
 * @author tolkv
 * @since 18/03/16
 */
@ThriftController
public class Investigator implements TInvestigatingService.Iface {
  @Override
  public String whereIs(TName name) throws TException {
    return "Where is " + name.getFirstName() + "?";
  }
}
