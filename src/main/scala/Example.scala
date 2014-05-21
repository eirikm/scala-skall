package com.example

import unfiltered.request._
import unfiltered.response._

import unfiltered.directives._, Directives._
import linx.Root
import unfiltered.filter.Plan

object urls {
  val listIndustries = Root / "industry"
  val getIndustry = Root / "industry" / 'industryId
}



class App
  extends Plans
  with ConcreteServices {

}

trait ConcreteServices

trait Plans
  extends Plan
  with IndustryPlan {

  override def intent =
    industryPlan.intent
}

trait IndustryPlan {
  object industryPlan extends Plan {
    def intent = Directive.Intent.Path[Any] {
      case urls.listIndustries() =>
        for {
          _ <- GET
        } yield {
          Ok ~> ResponseString("liste over alle bedrifter")
        }

      case urls.getIndustry(industryId) =>
        for {
          _ <- GET
        } yield {
          Ok ~> ResponseString(s"viser bedrift med id $industryId")
        }
    }
  }
}

/** embedded server */
object Server {
  def main(args: Array[String]) {
    unfiltered.jetty.Http(1337).filter(new App).run
  }
}
