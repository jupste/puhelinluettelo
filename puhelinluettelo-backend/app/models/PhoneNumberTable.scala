package models

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

class PhoneNumberTable(tag: Tag) extends Table[PhoneNumber](tag, "phonenumber") {
  def userId = column[Long]("userid")
  def phonenumber = column[String]("phonenumber")
  def countryCode = column[String]("country_code")

  def user = foreignKey("user_fk", userId, TableQuery[ContactTable])(_.id)
  def country = foreignKey("country_fk", countryCode, TableQuery[CountryTable])(_.code)

  def * = (userId, phonenumber, countryCode) <> (PhoneNumber.tupled, PhoneNumber.unapply)
}
