import unfiltered.request._
import unfiltered.response._

object IndustryRest {
  def main(args: Array[String]) {
    unfiltered.jetty.Http(1337).filter {
      unfiltered.filter.Planify {
        case GET(Path(Seg("industry" :: industryId :: Nil))) =>
          industryRepo.getById(industryId).map {
            industry =>
              Ok ~> JsonContent ~> ResponseString(toJson(industry))
          }.getOrElse(NotFound)

        case GET(Path(Seg("industry" :: Nil))) =>
          Ok ~> JsonContent ~>
            ResponseString(
              industryRepo.getAll map(toJson(_)) mkString("[", ", ", "]"))
      }
    }.run()
  }

  def toJson(i: Industry): String = {
    s"""{ 'id' = "${i.id}",
       |  'name' = "${i.name}"
       |}""".stripMargin  }

  case class Industry(id: String, name: String)

  object industryRepo {
    private val industries = Map(
      "1" -> Industry("1", "Fjon"),
      "2" -> Industry("2", "Fjott")
    )
    def getAll: Seq[Industry] = industries.values.toSeq
    def getById(id: String): Option[Industry] = industries.get(id)
  }
}
