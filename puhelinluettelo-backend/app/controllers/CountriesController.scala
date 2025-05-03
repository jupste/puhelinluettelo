package controllers

import javax.inject._
import play.api.mvc._
import repositories.CountriesRepository
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json._
import models._

@Singleton
class CountriesController @Inject()(cc: ControllerComponents, countriesRepo: CountriesRepository)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  implicit val countryFormat: OFormat[Country] = Json.format[Country]

  def getCountryDisplayList: Action[AnyContent] = Action.async {
    countriesRepo.list().map { countries =>
    val formatted = countries.map(c => (c.country_code, s"(${c.country_name})"))
    Ok(Json.toJson(formatted))
  }
}

}
