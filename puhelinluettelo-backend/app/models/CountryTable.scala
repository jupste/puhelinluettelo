package models

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

class CountryTable(tag: Tag) extends Table[Country](tag, "country") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def code = column[String]("code")
  def name = column[String]("name")

  def * = (id, code, name) <> (Country.tupled, Country.unapply)
}
