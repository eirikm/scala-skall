import com.typesafe.scalalogging.slf4j.Logging
import linx.Root
import unfiltered.filter.Plan
import unfiltered.filter.Plan._
import unfiltered.request._
import unfiltered.response._
import unfiltered.directives._, Directives._


object urls {
  val listIndustries = Root / "industry"
  val getIndustry = Root / "industry" / 'industryId
}

// TODO testing
trait IndustryPlan
  extends Plan
  with AbstractIndustryRepoComponent
  with Logging {

  override def intent: Intent =
    Directive.Intent.Path {
      case urls.getIndustry(industryId) =>
        for {
          _ <- GET
        } yield {
          logger.debug(s"plan: ${urls.getIndustry}")
          industryRepo.getById(industryId).map {
            industry =>
              Ok ~> JsonContent ~> ResponseString(toJson(industry))
          }.getOrElse(NotFound)
        }

      case urls.listIndustries() =>
        for {
          _ <- GET
        } yield {
          logger.debug(s"plan: ${urls.listIndustries}")
          Ok ~> JsonContent ~>
            ResponseString(
              industryRepo.getAll map (toJson(_)) mkString("[", ", ", "]"))
        }
    }

  private def toJson(i: Industry): String =
    s"""{ 'id' = "${i.id}",
       |  'name' = "${i.name}"
       |}""".stripMargin

}
