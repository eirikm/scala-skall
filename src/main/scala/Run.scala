import org.apache.log4j.PropertyConfigurator
import org.slf4j.bridge.SLF4JBridgeHandler

object Run extends App {
  PropertyConfigurator.configure(getClass.getResource("/log4j.properties"))
  SLF4JBridgeHandler.install()

  // TODO liquibase
  unfiltered.jetty.Http(1337).filter(IndustryApp).run()
}
