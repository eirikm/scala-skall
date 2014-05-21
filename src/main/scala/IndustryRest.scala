import linx.Root
import unfiltered.filter.Plan
import unfiltered.filter.Plan.Intent
import unfiltered.request._
import unfiltered.response._

object IndustryRest {

  def main(args: Array[String]) {
    unfiltered.jetty.Http(1337).filter(IndustryPlan).run()
  }
}

object urls {
  val listIndustries = Root / "industry"
  val getIndustry = Root / "industry" / 'industryId
}

object IndustryPlan
  extends Plan
  with IndustryRepoComponent {

  override def intent: Intent = {
    case GET(Path(urls.getIndustry(industryId))) =>
      industryRepo.getById(industryId).map {
        industry =>
          Ok ~> JsonContent ~> ResponseString(toJson(industry))
      }.getOrElse(NotFound)

    case GET(Path(urls.listIndustries())) =>
      Ok ~> JsonContent ~>
        ResponseString(
          industryRepo.getAll map(toJson(_)) mkString("[", ", ", "]"))
  }

  private def toJson(i: Industry): String = {
    s"""{ 'id' = "${i.id}",
       |  'name' = "${i.name}"
       |}""".stripMargin  }
}

case class Industry(id: String, name: String)

trait IndustryRepoComponent {
  object industryRepo {
    private val industries = Map(
      "1" -> Industry("1", "Fjon"),
      "2" -> Industry("2", "Fjott")
    )

    def getAll: Seq[Industry] = industries.values.toSeq
    def getById(id: String): Option[Industry] = industries.get(id)
  }
}