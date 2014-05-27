import org.constretto.Constretto
import org.constretto.Constretto._


object IndustryApp
  extends IndustryPlan
  with ConcreteServices
  with ConfigurationComponent {

  override lazy val conf = Constretto(Seq(properties("classpath:datasource.properties")))
  println(conf[String]("datasource.url"))

}

trait ConcreteServices
  extends IndustryRepoHashMapComponent

