import com.typesafe.scalalogging.slf4j.Logging
import linx.Root
import unfiltered.filter.Plan
import unfiltered.filter.Plan._
import unfiltered.request.{GET, Path}
import unfiltered.response.{JsonContent, NotFound, ResponseString, Ok}

object urls {
  val listIndustries = Root / "industry"
  val getIndustry = Root / "industry" / 'industryId
}

// TODO testing
trait IndustryPlan
  extends Plan
  with AbstractIndustryRepoComponent
  with Logging {

  // TODO Directives
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
