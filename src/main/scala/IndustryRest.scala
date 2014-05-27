import com.typesafe.scalalogging.slf4j.Logging
import linx.Root
import org.apache.log4j.PropertyConfigurator
import org.constretto.Constretto
import org.constretto.Constretto._
import org.slf4j.bridge.SLF4JBridgeHandler
import unfiltered.filter.Plan
import unfiltered.filter.Plan.Intent
import unfiltered.request._
import unfiltered.response._
import unfiltered.response.ResponseString

// TODO
// - Run
// - Directives
// - liquibase
// - slick
// - ConcreteServices
// - Abstract


object IndustryRest {

  def main(args: Array[String]) {
    PropertyConfigurator.configure(getClass.getResource("/log4j.properties"))
    SLF4JBridgeHandler.install()

    unfiltered.jetty.Http(1337).filter(IndustryApp).run()
  }
}

object urls {
  val listIndustries = Root / "industry"
  val getIndustry = Root / "industry" / 'industryId
}

object IndustryApp
  extends IndustryPlan
  with ConfigurationComponent {

  override lazy val conf = Constretto(Seq(properties("classpath:datasource.properties")))
  println(conf[String]("datasource.url"))

}

trait IndustryPlan
  extends Plan
  with IndustryRepoHashMapComponent
  with Logging {

  override def intent: Intent = {
    case GET(Path(urls.getIndustry(industryId))) =>
      logger.debug(s"plan: ${urls.getIndustry}")
      industryRepo.getById(industryId).map {
        industry =>
          Ok ~> JsonContent ~> ResponseString(toJson(industry))
      }.getOrElse(NotFound)

    case GET(Path(urls.listIndustries())) =>
      logger.debug(s"plan: ${urls.listIndustries}")
      Ok ~> JsonContent ~>
        ResponseString(
          industryRepo.getAll map(toJson(_)) mkString("[", ", ", "]"))
  }

  private def toJson(i: Industry): String =
    s"""{ 'id' = "${i.id}",
       |  'name' = "${i.name}"
       |}""".stripMargin

}

case class Industry(id: String, name: String)

trait IndustryRepoHashMapComponent {
  object industryRepo extends Logging {
    private val industries = Map(
      "1" -> Industry("1", "Fjon"),
      "2" -> Industry("2", "Fjott")
    )

    def getAll: Seq[Industry] = {
      logger.debug("repo: getAll")
      industries.values.toSeq
    }

    def getById(id: String): Option[Industry] = {
      logger.debug("repo: getById")
      industries.get(id)
    }
  }
}