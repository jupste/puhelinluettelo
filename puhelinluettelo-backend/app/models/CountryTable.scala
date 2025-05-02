package models

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

class CountryTable(tag: Tag) extends Table[Country](tag, "country_code") {
  def code = column[String]("country_code", O.PrimaryKey)
  def name = column[String]("country_name")

  def * = (code, name) <> (Country.tupled, Country.unapply)
}
