import com.typesafe.scalalogging.slf4j.Logging

trait AbstractIndustryRepoComponent {
  def industryRepo: AbstractIndustryRepo

  trait AbstractIndustryRepo {
    def getById(id: String): Option[Industry]
    def getAll: Seq[Industry]
  }
}

// TODO slick

trait IndustryRepoHashMapComponent
  extends AbstractIndustryRepoComponent {

  object industryRepo
    extends AbstractIndustryRepo
    with Logging {

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