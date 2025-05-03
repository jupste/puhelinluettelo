package repositories

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import models._
import scala.concurrent.{ExecutionContext, Future}

class CountriesRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import profile.api._

  private val countries = TableQuery[CountryTable]

  def list(): Future[Seq[Country]] = db.run(countries.result)
}